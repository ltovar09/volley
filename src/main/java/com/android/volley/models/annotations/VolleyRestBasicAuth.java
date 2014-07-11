package com.android.volley.models.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VolleyRestBasicAuth {
    String username() default "";
    String password() default "";
    String enabled() default "false";

}
