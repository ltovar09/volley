package com.android.volley.models;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.base.VolleyApplication;
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
import static com.android.volley.base.VolleyConstants.METHOD_POST;
import static com.android.volley.base.VolleyConstants.METHOD_PUT;
import static com.android.volley.base.VolleyConstants.REST_METHOD;
import static com.android.volley.base.VolleyConstants.REST_PATH;
import static com.android.volley.base.VolleyConstants.REST_URL;
import static com.android.volley.base.VolleyConstants.REQUEST_TIME_OUT;


/**
 * Created by oscarrodriguez on 6/11/14.
 */
public class VolleyQuery {


    private  static final String JEXO_QUERY="JexoQuery";
    private  int mRequestMethod;
    private  String mRequestHost;
   // private VolleyModel model;
    private  HashMap<String,String> ids;
    private  HashMap<String,String> keys;
    private Map<String,String> restHeaderKeys;

    private  int requestTimeOut;



    public int  getRequestTimeOut(){

        return  requestTimeOut;
    }

    public  VolleyQuery(){

        ids=new HashMap<String,String>();
        keys=new HashMap<String,String>();
        restHeaderKeys=new HashMap<String,String>();
        requestTimeOut=REQUEST_TIME_OUT;

    }


    public void setBasicAuth(String username,String password){

        HashMap<String,String> serverConfig = new  HashMap<String,String>();
        serverConfig.put(API_BASIC_AUTH_PASSWORD, username);
        serverConfig.put(API_BASIC_AUTH_USERNAME, password);
        serverConfig.put(API_BASIC_AUTH_ENABLED,API_BASIC_AUTH_ENABLE_TRUE);
        VolleyApplication.getInstance().addRestConfigValue(API_BASIC_AUTH, serverConfig);

    }

    /*public void setModel(VolleyModel model){

        this.model = model;
    }*/

    public void setHeaders(String key,String value) {
        VolleyApplication.getInstance().addHeaders(key, value);
    }


    public void seTimeOut(int time){

        this.requestTimeOut=time;
    }

    public  HashMap<String,String> getRestIds(){

        return ids;
    }

    public  HashMap<String,String> getRestKeys(){

        return keys;
    }

    public void setPathId(String key,String value){
        ids.put(key,value);
    }

    public void addUrlParameter(String key,String value){
        keys.put(key,value);
    }



    public  String getKeyParameters()
    {
        String query = "";
        String format = "";

        boolean first = true;

        if (keys!=null && !keys.isEmpty()) {

            for (String key: keys.keySet()) {
                format = (first ? "?" : "&");
                String param = format + key + "=" + URLEncoder.encode(keys.get(key));
                query=query+param;
                first = false;
            }
        }

        return query;
    }


    public String getRestUrl(String method,VolleyModel model) {
        String url = "";
        try {
            HashMap<String, Object> paramsMethod = (HashMap<String, Object>) VolleyApplication.getInstance().getMethods().get(method);

            String  serverUrl = VolleyApplication.getInstance().getRestConfig().get(REST_URL).toString();

            String path = paramsMethod.get(REST_PATH).toString();


            String  urlKeys=getKeyParameters();

           if(!urlKeys.isEmpty()){
               path=path+urlKeys;
           }

            if(model!=null) {
                this.ids = model.toParamId(this.ids);
            }
               if (this.ids != null) {
                    for (String key : ids.keySet()) {
                        String keyp = key;
                        path = path.replace(":" + key, ids.get(key));
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

           HashMap<String, Object> paramsMethod = (HashMap<String, Object>) VolleyApplication.getInstance().getMethods().get(method);

            if (paramsMethod.get(REST_METHOD).toString().equals(METHOD_POST)) {

                return Request.Method.POST;

            } else if (paramsMethod.get(REST_METHOD).toString().equals(METHOD_PUT)) {

                return Request.Method.PUT;

            } else if (paramsMethod.get(REST_METHOD).toString().equals(METHOD_DELETE)) {

                return Request.Method.DELETE;
            } else {

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

                HashMap<String, Object> basicAuthValues = (HashMap<String, Object>) VolleyApplication.getInstance().getBasicAuthKeys();

                if(basicAuthValues.get(API_BASIC_AUTH_ENABLED)==API_BASIC_AUTH_ENABLE_TRUE){
                    String basicAuth = basicAuthValues.get(API_BASIC_AUTH_USERNAME).toString() + ":" + basicAuthValues.get(API_BASIC_AUTH_PASSWORD).toString();
                    basicAuth = "Basic " + Base64.encodeToString(basicAuth.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", basicAuth);
                }

                Map<String, Object> headerConfig = (Map<String, Object>)VolleyApplication.getInstance().getRestHeaderKeys();
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

            JsonObjectRequest request = doRequest(Request.Method.GET,model,mRequestHost,null,requestTimeOut,callback);

            VolleyApplication.getInstance().addToRequestQueue(request);

        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }



    public void find(String method,final VolleyCallback callback)
    {

        try {
            mRequestHost = getRestUrl(method,null);
            JsonObjectRequest request = doRequest(Request.Method.GET,null,mRequestHost,null,requestTimeOut,callback);
            VolleyApplication.getInstance().addToRequestQueue(request);

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

                JsonObjectRequest request = doRequest(mRequestMethod,null,mRequestHost,params,requestTimeOut,callback);

                VolleyApplication.getInstance().addToRequestQueue(request);

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

            JsonObjectRequest request = doRequest(mRequestMethod,model,mRequestHost,params,requestTimeOut,callback);

            VolleyApplication.getInstance().addToRequestQueue(request);

        }else {
            Log.d("Response", "The method to this request is not defined");
            callback.onFail("The method for this request is not defined");
        }


        } catch (Exception e){
           Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }
}
