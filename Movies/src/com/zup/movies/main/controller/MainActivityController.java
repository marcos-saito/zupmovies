package com.zup.movies.main.controller;

import com.zup.movies.movie.view.MovieRegisterActivity;

import com.zup.movies.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivityController implements OnClickListener{

	private Context context;
	
	public MainActivityController(Context context) {
		this.context = context;
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

}
