package com.zup.movies.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

public class MoviesApplication extends Application {
	
	public static RequestQueue REQUEST_QUEUE;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		startVolleyQueue();
		dbInit();
	}

	private void startVolleyQueue() {
		REQUEST_QUEUE = Volley.newRequestQueue(this);
	}
	
	private void dbInit() {
		
	}
}
