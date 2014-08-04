package com.android.volley.models;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.base.VolleyApp;
import com.android.volley.networking.VolleyCallback;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by oscarrodriguez on 6/21/14.
 */
public class VolleyQueryUrl extends VolleyQuery {

    private  static final String JEXO_QUERY="JexoQuery";
    private VolleyModel model;
    private  String mUrl;
    private  String mMethod;


    public VolleyQueryUrl(String url){
        super();
        this.mUrl = url;
    }




    public void setMethod(String mMethod)
    {
        this.mMethod = mMethod;
    }


    public void find(final VolleyCallback callback)
    {
         try {

             Map<String,String> ids= new HashMap<String,String>();

             ids =  model.toParamId(super.getRestIds());

             String  urlKeys=getKeyParameters();

             if(!urlKeys.isEmpty()){
                 this.mUrl=this.mUrl+urlKeys;
             }

             if (ids!=null) {
                 for (String key: ids.keySet()) {
                     String keyp=key;
                     this.mUrl = this.mUrl.replace(":"+key, ids.get(key));
                 }
             }

            JsonObjectRequest request = doRequest(Request.Method.GET,null,this.mUrl,null,super.getRequestTimeOut(),callback);

             VolleyApp.getInstance().addToRequestQueue(request);

        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }


  public void post(HashMap<String,Object> params,final VolleyCallback callback)
    {
        try {
            JsonObjectRequest request = doRequest(Request.Method.POST,null,this.mUrl,params, super.getRequestTimeOut(), callback);
            VolleyApp.getInstance().addToRequestQueue(request);
        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }


    public void update(HashMap<String,Object> params,final VolleyCallback callback)
    {
        try {
            JsonObjectRequest request = doRequest(Request.Method.PUT,null,this.mUrl,params, super.getRequestTimeOut(), callback);
            VolleyApp.getInstance().addToRequestQueue(request);
        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }

    public void delete(HashMap<String,Object> params,final VolleyCallback callback)
    {
        try {
            JsonObjectRequest request = doRequest(Request.Method.DELETE,null,this.mUrl,params, super.getRequestTimeOut(), callback);
            VolleyApp.getInstance().addToRequestQueue(request);
        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }

    public void patch(HashMap<String,Object> params,final VolleyCallback callback)
    {
        try {
            JsonObjectRequest request = doRequest(Request.Method.PATCH,null,this.mUrl,params, super.getRequestTimeOut(), callback);
            VolleyApp.getInstance().addToRequestQueue(request);
        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }


}
