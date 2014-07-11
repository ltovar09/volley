package com.android.volley.util;

import android.util.Log;

public class VolleyLog {

   public static void debug(String msg)
   {
	   String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();            
       String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
       String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
       int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
       String tag = className + "." + methodName + ":" + lineNumber;
       
       Log.d(tag, msg);
   }
   
   public static void error(String msg)
   {
	   String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();            
       String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
       String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
       int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
       String tag = className + "." + methodName + ":" + lineNumber;
       
       Log.e(tag, msg);
   }
   
   public static void info(String msg)
   {
	   String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();            
       String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
       String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
       int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
       String tag = className + "." + methodName + ":" + lineNumber;
       
       Log.i(tag, msg);
   }

}