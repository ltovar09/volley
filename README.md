### Volley

Volley is a networking library was introduced to make networking calls much easier, faster without writing tons of code. By default all the volley network calls works asynchronously, so we don’t have to worry about using asynctask anymore.



### Getting Volley


## VolleyQueryUrl

VolleyQueryUrl implement the common patterns of communicating with a web application over HTTP,including request operation management.



### GET Request

```java
 VolleyQueryUrl query = new VolleyQueryUrl("http://example.com/resources.json");
 query.find(new VolleyCallback() {
        @Override
        public void onInit() {

        }

        @Override
        public void onFail(String message) {

        }

        @Override
        public void onSuccess(JSONObject response) {

        }
   });

```


### POST URL-Form-Encoded Request

```java
VolleyQueryUrl query = new VolleyQueryUrl("http://example.com/resources.json");
HashMap<String,Object> params = new  HashMap<String,Object>();
params.put("foo","bar");
query.save(Request.Method.POST,params,new VolleyCallback() {
	@Override
	public void onInit() {

	}

	@Override
	public void onFail(String message) {

	}

	@Override
	public void onSuccess(JSONObject response) {

	}
});
```

### UPDATE URL-Form-Encoded Request

```java
VolleyQueryUrl query = new VolleyQueryUrl("http://example.com/v1/api/resource/:id");
HashMap<String,Object> params = new  HashMap<String,Object>();
params.put("foo","bar");
query.setPathIds("id", "123");
query.save(Request.Method.PUT,params,new VolleyCallback() {
	@Override
	public void onInit() {

	}

	@Override
	public void onFail(String message) {

	}

	@Override
	public void onSuccess(JSONObject response) {

	}
});
```

### DESTROY URL-Form-Encoded Request

```java
VolleyQueryUrl query = new VolleyQueryUrl("http://example.com/v1/api/resource/:id");
query.setPathIds("id", "123");
query.save(Request.Method.DELETE,params,new VolleyCallback() {
	@Override
	public void onInit() {

	}

	@Override
	public void onFail(String message) {

	}

	@Override
	public void onSuccess(JSONObject response) {

	}
});
```

##  VolleyQuery

VolleyQuery object is other way to implement communicating with a web application over HTTP from configuration options.

The main configuration is add all the server parameter like url, headers and other require options to the request in you Application class.



```
public class YourApplication extends VolleyApplication {


    @Override
    public void onCreate() {
        super.onCreate();
	 }
}

```

Add YourApplication class in the AndroidManifest in the android:name label

```java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.yourpackage.app" >
  <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:name=".YourApplication"
        android:theme="@style/AppTheme" >
</manifest>

```


### Server Url
You need to add in youy application class the setServerUrl to provide the root ulr

```java
public class YourApplication extends VolleyApplication {


    @Override
    public void onCreate() {
        super.onCreate();
		setServerUrl("http://www.example.com");
	 }
}

```


### BasicAuth
If you want to add basic authentication to your server, you need to add setBasicAuth method with the 
username and password as parameters.

```java
public class YourApplication extends VolleyApplication {


    @Override
    public void onCreate() {
        super.onCreate();
		setServerUrl("http://www.example.com");
		setBasicAuth("XXXXX","XXXXXX");
	 }
}

```
### Headers

If you need to add a parameter set in the request headers you can to use a HashMap with key and value like is shown in the above code.


```java
public class YourApplication extends VolleyApplication {


    @Override
    public void onCreate() {
        super.onCreate();
		setServerUrl("http://www.example.com");
		setBasicAuth("XXXXX","XXXXXX");
		Map<String,Object> headersValues = new HashMap<String, Object>();
	    headersValues.put("Api-ID","XXXXXXXXX");
	    headersValues.put("Secret-ID","XXXXXX");
	    setHeaders(headersValues);
	 }
}

```



Other way to add parameters to the headers is use addHeaders funciton.



```java
public class YourApplication extends VolleyApplication {


    @Override
    public void onCreate() {
        super.onCreate();
		setServerUrl("http://www.example.com");
		setBasicAuth("XXXXX","XXXXXX");
	    addRestHeaders("user-token","xxxxxxxx");
	 }
}

```

### EndPoint file 

The end-point is a set the path or url to control the request action, avoiding the need to hardcode strings in your views.

To create a rout you need specify a variable string type as reference  and add the path,method and contentType in a java annotation like the following example.


```java
public class EndPoint {

    //REST API ROUTES
	
    @VolleyRestMethod(path = "v1/api/users", method = "GET", contentType = "application/json")
    public static final String GET_USERS="GET_USERS";
	
    @VolleyRestMethod(path = "v1/api/user/:id", method = "GET", contentType = "application/json")
    public static final String GET_USER="GET_USER";
	
    @VolleyRestMethod(path = "v1/api/user", method = "POST", contentType = "application/json")
    public static final String CREATE_USER="CREATE_USER";
	
    @VolleyRestMethod(path = "v1/api/user/:id", method = "DELETE", contentType = "application/json")
    public static final String DELETE_USER="DELETE_USER";
	
    @VolleyRestMethod(path = "v1/api/user/:id", method = "PUT", contentType = "application/json")
    public static final String UPDATE_USER="UPDATE_USER";

}

```

Finally you need to add the end-point class in your appliaction class 


```java
public class YourApplication extends VolleyApplication {


    @Override
    public void onCreate() {
        super.onCreate();
		setServerUrl("http://www.example.com");
		setBasicAuth("XXXXX","XXXXXX");
	    addRestHeaders("user-token","xxxxxxxx");
		setEndPoint(EndPoint.class);
	 }
}

```


### Find 
Implementing the find method to get all the users, we use GET_USERS root in the end-point file to do the request.

```java
//REST API ROUTES
@VolleyRestMethod(path = "v1/api/users", method = "GET", contentType = "application/json")
public static final String GET_USERS="GET_USERS";
```

```java
	
VolleyQuery query = new VolleyQuery();
query.find(GET_USERS,new VolleyCallback() {
	@Override
	public void onInit() {

	}

	@Override
	public void onFail(String message) {

	}

	@Override
	public void onSuccess(JSONObject response) {

	}
	});
}
```


If you want to add parameter in the query url like by example "http:www.example.com/v1/api/users?q=owner", you can use addUrlParameter

```java
	
VolleyQuery query = new VolleyQuery();
query.addUrlParameter("q", "owner");
query.find(GET_USERS,new VolleyCallback() {
	@Override
	public void onInit() {

	}

	@Override
	public void onFail(String message) {

	}

	@Override
	public void onSuccess(JSONObject response) {

	}
	});
}
```



### Find By Id 

Implementing the find by id method to get a specific user, we use GET_USER root in the end-point file to do the request.

```java
//REST API ROUTES
@VolleyRestMethod(path = "v1/api/user/:id", method = "GET", contentType = "application/json")
public static final String GET_USER="GET_USER";
```

```java
VolleyQuery query = new VolleyQuery();
query.setPathId("id", "12");
query.find(GET_USERS,new VolleyCallback() {
	@Override
	public void onInit() {

	}

	@Override
	public void onFail(String message) {

	}

	@Override
	public void onSuccess(JSONObject response) {

	}
	});
}
```

### Save 
Implementing the save method to create a user, we use CREATE_USER root in the end-point file to do the request.

```java
//REST API ROUTES
@VolleyRestMethod(path = "v1/api/user", method = "POST", contentType = "application/json")
public static final String CREATE_USER="CREATE_USER";
```

```java
	
VolleyQuery query = new VolleyQuery();
HashMap<String,Object> params = new HashMap<String, Object>();
params.put("name","XXXXX");
params.put("address","XXXXX");
params.put("phone","XXXXX");
query.save(CREATE_USER,params,new VolleyCallback() {
	@Override
	public void onInit() {

	}

	@Override
	public void onFail(String message) {

	}

	@Override
	public void onSuccess(JSONObject response) {

	}
	});
}
```

### Save with model

You can use the VolleyModel to encapsulate data and send this one via http request by example :

```java
public class User extends VolleyModel
{
    @VolleyParamId( paramName="id")
    public String userId;
    @VolleyModelField(fieldName="email")
	public String email;
	@VolleyModelField(fieldName="password")
	public String password;
    @VolleyModelHeader( headerName="user_token")
    public String usertoken;
}
```
In this case you use the VolleyModelField for indicate to the VolleyModel that the attribute will add as parameter in the query.

```java

User user=new User();
VolleyQuery query = new VolleyQuery();
user.userId="45";
user.email="api@test.com";
user.password="XXXXXX";
user.usertoken="XXXXXX";
query.save(CREATE_USER,user, new VolleyCallback() {
	@Override
	public void onInit() {

	}

	@Override
	public void onFail(String message) {

	}

	@Override
	public void onSuccess(JSONObject response) {

	}
	});

```

