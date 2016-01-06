package com.zup.movies.movie.web;


import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.zup.movies.app.MoviesApplication;
import com.zup.movies.util.WebHelper;

public class OMDbWebHelper extends WebHelper{
	
	public void getMovie(String movieName, int page, Listener<String> successListener, ErrorListener errorListener, Object tag) {
		String request = String.format("%s/?s=%s&page=%d", this.getServerUrl(), movieName, page);
		
		StringRequest stringRequest = new StringRequest(Request.Method.GET, request, successListener, errorListener);
		MoviesApplication.REQUEST_QUEUE.add(stringRequest);
	}
}
