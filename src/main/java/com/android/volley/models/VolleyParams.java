package com.android.volley.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyParams {

	public Map<String, Object> data;
	public Map<String, String> headers;
	//public Map<String, String> meta;
	public Map<String, String> ids;
	public List<Object> conditions;
	public List<Object> order;
	public String method;

	public String path;
	
	public VolleyParams()
	{
		data = new HashMap<String, Object>();
		ids = new HashMap<String, String>();
		conditions = new ArrayList<Object>();
		order = new ArrayList<Object>();
		headers = new HashMap<String, String>();
	}
}
