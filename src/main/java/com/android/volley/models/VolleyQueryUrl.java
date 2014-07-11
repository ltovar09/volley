package com.android.volley.models;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.base.VolleyApplication;
import com.android.volley.networking.VolleyCallback;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.base.VolleyConstants.METHOD_DELETE;
import static com.android.volley.base.VolleyConstants.METHOD_POST;
import static com.android.volley.base.VolleyConstants.METHOD_PUT;



/**
 * Created by oscarrodriguez on 6/21/14.
 */
public class VolleyQueryUrl extends VolleyQuery {

    private  static final String JEXO_QUERY="JexoQuery";
    private VolleyModel model;
    private  String url;
    private  String method;


    public VolleyQueryUrl(String url){
        super();
        this.url = url;
    }




    public void setMethod(String method)
    {
        this.method = method;
    }


    public void find(final VolleyCallback callback)
    {
         try {

             Map<String,String> ids= new HashMap<String,String>();

             ids =  model.toParamId(super.getRestIds());

             String  urlKeys=getKeyParameters();

             if(!urlKeys.isEmpty()){
                 this.url=this.url+urlKeys;
             }

             if (ids!=null) {
                 for (String key: ids.keySet()) {
                     String keyp=key;
                     this.url = this.url.replace(":"+key, ids.get(key));
                 }
             }

            JsonObjectRequest request = doRequest(Request.Method.GET,null,this.url,null,super.getRequestTimeOut(),callback);

            VolleyApplication.getInstance().addToRequestQueue(request);

        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }


  public void save(int method,HashMap<String,Object> params,final VolleyCallback callback)
    {
        try {

           if(method>=0&&method<=3) {


               JsonObjectRequest request = doRequest(method,null,this.url,params, super.getRequestTimeOut(), callback);

               VolleyApplication.getInstance().addToRequestQueue(request);

           }else{

               Log.d(JEXO_QUERY, "Problem loading the rest methods values.");
           }

        } catch (Exception e){
            Log.d(JEXO_QUERY, "Problem loading the rest config.");
        }

    }


}
