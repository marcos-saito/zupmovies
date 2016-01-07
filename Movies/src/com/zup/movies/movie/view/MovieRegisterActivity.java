package com.zup.movies.movie.view;

import com.zup.movies.R;
import com.zup.movies.movie.controller.MovieRegisterActivityController;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MovieRegisterActivity extends Activity {

	private Holder holder;
	private MovieRegisterActivityController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		initView();
		setListeners();
	}

	private void setListeners() {
		this.controller = new MovieRegisterActivityController(this.holder);
		
		this.holder.searchEditText.addTextChangedListener(this.controller);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.movie_register, menu);
		return true;
	}
	
	public static Intent getIntent(Context context) {
		Intent intent = new Intent(context, MovieRegisterActivity.class);
		
		return intent;
	}
	
	private void initView() {
		setContentView(R.layout.activity_movie_register);
		
		this.holder = new Holder(this);
	}
	
	public class Holder {
		
		final EditText searchEditText;
		final ListView listViewSearch;
		final TextView emptyListText;
		
		public Holder(Activity activity) {
			searchEditText = (EditText) activity.findViewById(R.id.editTextSearch);
			listViewSearch = (ListView) activity.findViewById(R.id.listMovieSearch);
			emptyListText = (TextView) activity.findViewById(R.id.emptyList);
			
			listViewSearch.setEmptyView(emptyListText);
		}
	}
}
