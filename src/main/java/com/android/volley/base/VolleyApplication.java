package com.android.volley.base;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import static com.android.volley.base.VolleyConstants.*;
import com.android.volley.models.annotations.VolleyRestMethod;
import com.android.volley.util.VolleyLog;

public class VolleyApplication extends Application {



    private Map<String, Object> methods;
    private Map<String, Object> restConfig;
    private Map<String, Object> restHeaderKeys;
    private Map<String, Object> restConfigValue;
    private String serverUrl;
    private  int requestTimeOut;

    /**
     * Log or request TAG
     */
    public static final String TAG = "VolleyPatterns";

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static VolleyApplication instance;

	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
        this.methods = new HashMap<String, Object>();
        this.restConfig = new HashMap<String, Object>();
        this.restHeaderKeys = new HashMap<String, Object>();
    }



    public void setServerUrl(String url){
        this.serverUrl=url;
        restConfig.put(REST_URL,  this.serverUrl);
    }


    public void setBasicAuth(String username,String password){

        Map<String, Object> restConfigValue = new HashMap<String, Object>();
        restConfigValue.put(API_BASIC_AUTH_PASSWORD,username);
        restConfigValue.put(API_BASIC_AUTH_USERNAME,password);
        restConfigValue.put(API_BASIC_AUTH_ENABLED,API_BASIC_AUTH_ENABLE_TRUE);
        restConfig.put(API_BASIC_AUTH, restConfigValue);

    }

    public void setHeaders(Map<String,Object> headers){

        for (Object key : headers.keySet()) {
            restHeaderKeys.put(key.toString(), headers.get(key));
        }
     }

    public void setEndPoint(Class endPoint){

        Field[] fields = endPoint.getDeclaredFields();
        for(int ind = 0; ind < fields.length; ind++) {
            Annotation methodAnnotation = fields[ind].getAnnotation(VolleyRestMethod.class);

            if (methodAnnotation != null) {
               Map <String, Object> endPointMap=new HashMap<String, Object>();
                VolleyRestMethod endPointField = (VolleyRestMethod) methodAnnotation;
                String path=endPointField.path();
                endPointMap.put("path",path);
                endPointMap.put("contentType",endPointField.contentType());
                endPointMap.put("method",endPointField.method().toString().toUpperCase());
                if(!endPointMap.isEmpty()){
                    this.methods.put(fields[ind].getName(),endPointMap);
                }
            }

        }
    }


    public Map<String, Object> getMethods(){
      return methods;
    }


    public Map<String, Object> getRestConfig(){
        return restConfig;
    }

    public Map<String, Object> getRestHeaderKeys(){

        return (Map<String, Object>) this.restHeaderKeys;
    }

    public Map<String, Object> getBasicAuthKeys(){
      return (Map<String, Object> ) restConfig.get(API_BASIC_AUTH);
    }


	public void addRestConfigValue(String key,Map<String, String> value){
        restConfig.put(key,value);
    }

    public void addHeaders(String key,String value){
       this.restHeaderKeys.put(key,value);
    }



	public static VolleyApplication getInstance() {
		return instance;
	}


    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req

     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty

        com.android.volley.VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


	public String getMetadata(String name) {
		String value = null;
		
		try {
		    ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
		    Bundle bundle = ai.metaData;
		    value = bundle.getString(name);
		} catch (Exception e) {
		  if (e!=null) VolleyLog.error("Failed to load meta-data: " + e.getMessage());
		}
		
		return value;
	}
	
	public boolean isConnected()
	{
		boolean status=false;
	    
		try{
	        
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo netInfo = cm.getNetworkInfo(0);
	        
	        if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
	            status= true;
	        } else {
	            netInfo = cm.getNetworkInfo(1);
	            if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
	                status= true;
	            }
	        }
	    }catch(Exception e){
	          
	        status = false;
	    }
	    
		return status;
	}

	
}