package com.zup.movies.movie.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.zup.movies.app.MoviesApplication;
import com.zup.movies.movie.model.MovieEntity;
import com.zup.movies.movie.view.MovieRegisterActivity.Holder;
import com.zup.movies.movie.web.OMDbWebHelper;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

public class MovieRegisterActivityController implements TextWatcher {
	
	private static String MOVIE_SEARCH_CANCEL_TAG = "searchMovieCancelTag";
	
	private String accumulator = "";
	private Holder holder;
	private OMDbWebHelper webHelper;
	private List<MovieEntity> movies;

	public MovieRegisterActivityController(Holder holder) {
		this.holder = holder;
		this.webHelper = new OMDbWebHelper();
		this.movies = new ArrayList<MovieEntity>();
	}
	
	Listener<String> successListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			Log.d("Success Response", "Get Movie: " + response);
			jsonStringToObjetct(response);
		}
	};

	private ErrorListener errorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.d("Error Response", "Get Movie: " + error.getMessage());
		}
	};
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		accumulator = s.toString();
		
		if(accumulator.length() > 1) {
			MoviesApplication.REQUEST_QUEUE.cancelAll(MOVIE_SEARCH_CANCEL_TAG);
			webHelper.getMovie(accumulator, 1, successListener, errorListener, MOVIE_SEARCH_CANCEL_TAG);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	
	public void jsonStringToObjetct(String jsonString) {
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("Search");
				
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject explrObject = jsonArray.getJSONObject(i);
				MovieEntity searchMovie = new MovieEntity();
			        
				searchMovie.setTitle(explrObject.getString("Title"));
				searchMovie.setYear(explrObject.getString("Year"));
				searchMovie.setImdbID(explrObject.getString("imdbID"));
				searchMovie.setType(explrObject.getString("Type"));
				searchMovie.setPoster(explrObject.getString("Poster"));
				
				this.movies.add(searchMovie);
			}
				
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}