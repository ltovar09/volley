package com.android.volley.models.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VolleyRestMethod {
	
	String host() default "";
    String path() default "";
	String method() default "POST";
	String contentType() default "application/x-www-form-urlencoded";
}
