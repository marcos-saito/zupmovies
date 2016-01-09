package com.zup.movies.movie.controller;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.zup.movies.R;
import com.zup.movies.app.MoviesApplication;
import com.zup.movies.main.DAO.MovieDBHelper;
import com.zup.movies.movie.model.MovieEntity;
import com.zup.movies.movie.view.MovieRegisterActivity;
import com.zup.movies.movie.web.OMDbWebHelper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MovieRegisterActivityController implements TextWatcher, OnItemClickListener {
	
	private static String MOVIE_SEARCH_CANCEL_TAG = "searchMovieCancelTag";
	public static String UPDATE_LIST = "updateList";
	
	private MovieRegisterActivity activity;
	private OMDbWebHelper webHelper;
	private MovieListSearchAdapter adapter;

	public MovieRegisterActivityController(MovieRegisterActivity activity) {
		this.activity = activity;
		this.webHelper = new OMDbWebHelper();
		this.adapter = new MovieListSearchAdapter(activity, new ArrayList<MovieEntity>());
		this.activity.getHolder().listViewSearch.setAdapter(this.adapter);
		this.activity.getHolder().listViewSearch.setOnItemClickListener(this);
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
		this.adapter.clear();
		
		if(s.length() > 1) {
			MoviesApplication.REQUEST_QUEUE.cancelAll(MOVIE_SEARCH_CANCEL_TAG);
			webHelper.getMovie(s.toString(), 1, successListener, errorListener, MOVIE_SEARCH_CANCEL_TAG);
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
			ArrayList<MovieEntity> movies = new ArrayList<MovieEntity>();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject explrObject = jsonArray.getJSONObject(i);
				MovieEntity searchMovie = new MovieEntity();
			        
				searchMovie.setTitle(explrObject.getString("Title"));
				searchMovie.setYear(explrObject.getString("Year"));
				searchMovie.setImdbID(explrObject.getString("imdbID"));
				searchMovie.setType(explrObject.getString("Type"));
				searchMovie.setPoster(explrObject.getString("Poster"));
				searchMovie.setInactive(false);
				
				movies.add(searchMovie);
			}
			
			notifyNewData(movies);
				
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void notifyNewData(ArrayList<MovieEntity> movies) {
		this.adapter.addAll(movies);
		this.adapter.notifyDataSetChanged();
	}
	
	public void sendBrodcast() {
		Intent i = new Intent(UPDATE_LIST);
		activity.sendBroadcast(i);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
		final MovieEntity item = this.adapter.getItem(position);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(Html.fromHtml(String.format(activity.getString(R.string.dialog_add_movie_message), item.getTitle())));
		
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   ProgressDialog progress = new ProgressDialog(MovieRegisterActivityController.this.activity);
	        	   progress.setMessage(MovieRegisterActivityController.this.activity.getString(R.string.loading));
	        	   progress.setCancelable(false);
	        	   MovieDBHelper.createMovie(item);
	        	   progress.dismiss();
	        	   sendBrodcast();
	        	   
	        	   Toast.makeText(MovieRegisterActivityController.this.activity, R.string.movie_add_successfully, Toast.LENGTH_SHORT).show();
	           }
	    });
		
		builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	           }
	    });
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
