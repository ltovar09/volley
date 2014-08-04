package com.android.volley.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyParams {

	public Map<String, Object> data;
	public Map<String, String> headers;
	public Map<String, String> ids;
	public String method;
	
	public VolleyParams()
	{
		ids = new HashMap<String, String>();
		headers = new HashMap<String, String>();
	}
}
