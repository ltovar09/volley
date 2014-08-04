package com.android.volley.models;

import android.content.Context;

import com.android.volley.models.annotations.VolleyModelField;
import com.android.volley.models.annotations.VolleyModelHeader;
import com.android.volley.models.annotations.VolleyParamId;
import com.android.volley.util.VolleyLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by oscarrodriguez on 6/11/14.
 */
public class VolleyModel {


    protected Context mContext;
    protected VolleyQuery query;

    public void init(Context context)
    {
        this.mContext = context;
    }

    public VolleyModel()
    {
        init(null);

    }

    public VolleyModel(Context context)
    {
        init(context);
 }


    public Map<String, Object> getParams()
    {
        Map<String, Object> data = new HashMap<String, Object>();

        for(Field field : getClass().getDeclaredFields()){
            String key = null;
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations.length>0) {
                for (int i = 0; i < annotations.length; i++) {
                    Annotation annotation = annotations[i];

                    if (annotation instanceof VolleyModelField) {

                        try {
                            VolleyModelField modelField = (VolleyModelField)annotation;
                            key = modelField.fieldName();
                            data.put(key, field.get(this).toString());
                        } catch (Exception e) {
                            if (e!=null) VolleyLog.error(e.toString());
                        }
                    }
                }
            }
        }

        return data;
    }


    public HashMap<String, String> toHeaderMap()
    {
        HashMap<String, String> data = new HashMap<String, String>();

        for(Field field : getClass().getDeclaredFields()){
            String key = null;
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations.length>0) {
                for (int i = 0; i < annotations.length; i++) {
                    Annotation annotation = annotations[i];

                    if (annotation instanceof VolleyModelHeader) {

                        try {
                            VolleyModelHeader modelField = (VolleyModelHeader)annotation;
                            key = modelField.headerName();
                            data.put(key, field.get(this).toString());
                        } catch (Exception e) {
                            if (e!=null) VolleyLog.error(e.toString());
                        }
                    }
                }
            }
        }
      return data;
    }



    public HashMap<String, String> toParamId(HashMap<String, String> ids)
    {
        HashMap<String, String> data = ids;

        for(Field field : getClass().getDeclaredFields()){
            String key = null;
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations.length>0) {
                for (int i = 0; i < annotations.length; i++) {
                    Annotation annotation = annotations[i];

                    if (annotation instanceof VolleyParamId) {

                        try {
                            VolleyParamId modelField = (VolleyParamId)annotation;
                            key = modelField.paramName();
                            data.put(key, field.get(this).toString());
                        } catch (Exception e) {
                            if (e!=null) VolleyLog.error(e.toString());
                        }
                    }
                }
            }
        }
        return data;
    }
}
