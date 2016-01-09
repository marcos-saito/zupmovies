package com.zup.movies.main.controller;

import java.util.ArrayList;
import java.util.List;

import com.zup.movies.main.DAO.MovieDBHelper;
import com.zup.movies.main.view.MainActivity;
import com.zup.movies.movie.controller.MovieListSearchAdapter;
import com.zup.movies.movie.controller.MovieRegisterActivityController;
import com.zup.movies.movie.model.MovieEntity;
import com.zup.movies.movie.view.MovieRegisterActivity;
import com.zup.movies.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivityController implements OnClickListener, OnItemClickListener {

	private MainActivity context;
	private MovieListSearchAdapter adapter;
	
	public MainActivityController(MainActivity context) {
		this.context = context;
		initMainList();
	}
	
	private void initMainList() {
		List<MovieEntity> myMovies = MovieDBHelper.queryForAllMovies();
		this.adapter = new MovieListSearchAdapter(context, myMovies == null ? new ArrayList<MovieEntity>() : myMovies);
		this.context.getHolder().moviewListView.setAdapter(this.adapter);
		this.context.getHolder().moviewListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		switch(id) {
		case R.id.addMovieLayout:
			addMovieAction();
			break;
		}
	}

	private void addMovieAction() {
		Intent intent = MovieRegisterActivity.getIntent(context);
		
		context.startActivity(intent);
	}
	
	public void updateList() {
		this.adapter.clear();
		List<MovieEntity> myMovies = MovieDBHelper.queryForAllMovies();
		this.adapter.addAll(myMovies);
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
		final MovieEntity item = this.adapter.getItem(position);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(Html.fromHtml(String.format(context.getString(R.string.dialog_delete_movie_message), item.getTitle())));
		
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   ProgressDialog progress = new ProgressDialog(MainActivityController.this.context);
	        	   progress.setMessage(MainActivityController.this.context.getString(R.string.loading));
	        	   progress.setCancelable(false);
	        	   MovieDBHelper.deleteMovie(item);
	        	   MainActivityController.this.adapter.remove(item);
	        	   MainActivityController.this.adapter.notifyDataSetChanged();
	        	   progress.dismiss();
	        	   
	        	   Toast.makeText(MainActivityController.this.context, R.string.movie_delete_successfully, Toast.LENGTH_SHORT).show();
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
