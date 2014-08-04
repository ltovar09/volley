package com.android.volley.models;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.base.VolleyApp;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.networking.VolleyCallback;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import static com.android.volley.base.VolleyConstants.API_BASIC_AUTH;
import static com.android.volley.base.VolleyConstants.API_BASIC_AUTH_ENABLED;
import static com.android.volley.base.VolleyConstants.API_BASIC_AUTH_ENABLE_TRUE;
import static com.android.volley.base.VolleyConstants.API_BASIC_AUTH_PASSWORD;
import static com.android.volley.base.VolleyConstants.API_BASIC_AUTH_USERNAME;
import static com.android.volley.base.VolleyConstants.METHOD_DELETE;
import static com.android.volley.base.VolleyConstants.METHOD_PATCH;
import static com.android.volley.base.VolleyConstants.METHOD_POST;
import static com.android.volley.base.VolleyConstants.METHOD_PUT;
import static com.android.volley.base.VolleyConstants.REST_PATH;
import static com.android.volley.base.VolleyConstants.REST_METHOD;
import static com.android.volley.base.VolleyConstants.REST_URL;
import static com.android.volley.base.VolleyConstants.REQUEST_TIME_OUT;
import static com.android.volley.base.VolleyConstants.REST_HOST;

/**
 * Created by oscarrodriguez on 6/11/14.
 */
public class VolleyQuery {


    private  static final String JEXO_QUERY="JexoQuery";
    private  int mRequestMethod;
    private  String mRequestHost;
    private  HashMap<String,String> mIds;
    private  HashMap<String,String> mKeys;
    private Map<String,String> mRestHeaderKeys;

    private  int mRequestTimeOut;



    public int getRequestTimeOut(){

        return mRequestTimeOut;
    }

    public  VolleyQuery(){

        mIds =new HashMap<String,String>();
        mKeys =new HashMap<String,String>();
        mRestHeaderKeys =new HashMap<String,String>();
        mRequestTimeOut =REQUEST_TIME_OUT;

    }


    public void setBasicAuth(String username,String password){

        HashMap<String,String> serverConfig = new  HashMap<String,String>();
        serverConfig.put(API_BASIC_AUTH_PASSWORD, username);
        serverConfig.put(API_BASIC_AUTH_USERNAME, password);
        serverConfig.put(API_BASIC_AUTH_ENABLED,API_BASIC_AUTH_ENABLE_TRUE);
        VolleyApp.getInstance().addRestConfigValue(API_BASIC_AUTH, serverConfig);

    }

    /*public void setModel(VolleyModel model){

        this.model = model;
    }*/

    public void setHeaders(String key,String value) {
        VolleyApp.getInstance().addHeaders(key, value);
    }


    public void seTimeOut(int time){

        this.mRequestTimeOut =time;
    }

    public  HashMap<String,String> getRestIds(){

        return mIds;
    }

    public  HashMap<String,String> getRestKeys(){

        return mKeys;
    }

    public void setPathId(String key,String value){
        mIds.put(key, value);
    }

    public void addUrlParameter(String key,String value){
        mKeys.put(key, value);
    }



    public  String getKeyParameters()
    {
        String query = "";
        String format = "";

        boolean first = true;

        if (mKeys !=null && !mKeys.isEmpty()) {

            for (String key: mKeys.keySet()) {
                format = (first ? "?" : "&");
                String param = format + key + "=" + URLEncoder.encode(mKeys.get(key));
                query=query+param;
                first = false;
            }
        }

        return query;
    }


    public String getRestUrl(String method,VolleyModel model) {
        String url = "";
        try {
            HashMap<String, Object> paramsMethod = (HashMap<String, Object>) VolleyApp.getInstance().getmMethods().get(method);

            String serverUrl = (paramsMethod.get(REST_HOST)!=null) ? paramsMethod.get(REST_HOST).toString() : VolleyApp.getInstance().getmRestConfig().get(REST_URL).toString();

            String path = paramsMethod.get(REST_PATH).toString();


            String  urlKeys=getKeyParameters();

           if(!urlKeys.isEmpty()){
               path=path+urlKeys;
           }

            if(model!=null) {
                this.mIds = model.toParamId(this.mIds);
            }
               if (this.mIds != null) {
                    for (String key : mIds.keySet()) {
                        String keyp = key;
                        path = path.replace(":" + key, mIds.get(key));
                    }
                }


            url = serverUrl + "/" + path;


        } catch (Exception e) {
            Log.d(JEXO_QUERY, "Problem loading the rest url values.");
        }

        return url;
    }



    public  int  getServerMethod(String method){

          try{

           HashMap<String, Object> paramsMethod = (HashMap<String, Object>) VolleyApp.getInstance().getmMethods().get(method);

            if (paramsMethod.get(REST_METHOD).toString().equals(METHOD_POST)) {

                return Request.Method.POST;

            } else if (paramsMethod.get(REST_METHOD).toString().equals(METHOD_PUT)) {

                return Request.Method.PUT;

            } else if (paramsMethod.get(REST_METHOD).toString().equals(METHOD_DELETE)) {

                return Request.Method.DELETE;

            } else if (paramsMethod.get(REST_METHOD).toString().equals(METHOD_PATCH)) {

                 return Request.Method.PATCH;

               }else{
                return Request.Method.DEPRECATED_GET_OR_POST;

             }

         } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest methods values.");
            return Request.Method.DEPRECATED_GET_OR_POST;
         }

    }


    public JsonObjectRequest  doRequest(int method,final VolleyModel model,String Host,Map<String, Object> params,int timeOut,final VolleyCallback callback){

        JSONObject requestParams = null;

        if(params!=null){
            requestParams=  new JSONObject(params);
        }
        JsonObjectRequest request = new JsonObjectRequest(method,
                Host, requestParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response",response.toString());
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error Reponse", "Error: " + error.getMessage());
                callback.onFail(error.getMessage());
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers =( model!=null ?  model.toHeaderMap() : new HashMap<String, String>());

                HashMap<String, Object> basicAuthValues = (HashMap<String, Object>) VolleyApp.getInstance().getBasicAuthKeys();

                if(basicAuthValues.get(API_BASIC_AUTH_ENABLED)==API_BASIC_AUTH_ENABLE_TRUE){
                    String basicAuth = basicAuthValues.get(API_BASIC_AUTH_USERNAME).toString() + ":" + basicAuthValues.get(API_BASIC_AUTH_PASSWORD).toString();
                    basicAuth = "Basic " + Base64.encodeToString(basicAuth.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", basicAuth);
                }

                Map<String, Object> headerConfig = (Map<String, Object>)VolleyApp.getInstance().getmRestHeaderKeys();
                for (Object key : headerConfig.keySet()) {
                    headers.put(key.toString(),headerConfig.get(key).toString());
                }

                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(timeOut * 1000, 1, 1.0f));

        return request;
    }


    public void find(String method,VolleyModel model,final VolleyCallback callback)
    {

        try {

          mRequestHost = getRestUrl(method,model);

            JsonObjectRequest request = doRequest(Request.Method.GET,model,mRequestHost,null, mRequestTimeOut,callback);

            VolleyApp.getInstance().addToRequestQueue(request);

        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }



    public void find(String method,final VolleyCallback callback)
    {

        try {
            mRequestHost = getRestUrl(method,null);
            JsonObjectRequest request = doRequest(Request.Method.GET,null,mRequestHost,null, mRequestTimeOut,callback);
            VolleyApp.getInstance().addToRequestQueue(request);

        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }



    public void save(String method,HashMap<String,Object> params,final VolleyCallback callback)
    {

        try {

            mRequestMethod =getServerMethod(method);
            mRequestHost = getRestUrl(method,null);

            if(mRequestMethod!=Request.Method.DEPRECATED_GET_OR_POST){

                JsonObjectRequest request = doRequest(mRequestMethod,null,mRequestHost,params, mRequestTimeOut,callback);

                VolleyApp.getInstance().addToRequestQueue(request);

            }else {
                Log.d("Response", "The method to this request is not defined");
                callback.onFail("The method for this request is not defined");
            }


        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }


    public void save(String method,VolleyModel model,final VolleyCallback callback)
    {

      try {

        mRequestMethod =getServerMethod(method);
        mRequestHost = getRestUrl(method,model);

        if(mRequestMethod!=Request.Method.DEPRECATED_GET_OR_POST){

            Map<String, Object> params = model.getParams();

            JsonObjectRequest request = doRequest(mRequestMethod,model,mRequestHost,params, mRequestTimeOut,callback);

            VolleyApp.getInstance().addToRequestQueue(request);

        }else {
            Log.d("Response", "The method to this request is not defined");
            callback.onFail("The method for this request is not defined");
        }


        } catch (Exception e){
           Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }
}
