package com.zup.movies.main.view;

import com.zup.movies.R;
import com.zup.movies.main.controller.MainActivityController;
import com.zup.movies.movie.controller.MovieRegisterActivityController;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private MainActivityController controller;
	private Holder holder;
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		 
		@Override
		public void onReceive(Context context, Intent intent) {
			controller.updateList();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.registerReceiver(mReceiver, new IntentFilter(MovieRegisterActivityController.UPDATE_LIST));
		
		initView();
		setController();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.unregisterReceiver(this.mReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void initView() {
		this.setContentView(R.layout.activity_main);
		
		this.holder =  new Holder(this);
	}
	
	private void setController() {
		this.controller = new MainActivityController(this);		
		this.holder.addMovieLayout.setOnClickListener(this.controller);
	}
	
	public Holder getHolder() {
		return holder;
	}

	public void setHolder(Holder holder) {
		this.holder = holder;
	}
	
	public class Holder {
		public final TextView emptyViewText;
		public final ListView moviewListView;
		public final LinearLayout addMovieLayout;
		
		public Holder(Activity activity) {
			emptyViewText = (TextView) activity.findViewById(R.id.emptyList);
			moviewListView = (ListView) activity.findViewById(R.id.myMoviesList);
			addMovieLayout = (LinearLayout) activity.findViewById(R.id.addMovieLayout);
			
			moviewListView.setEmptyView(emptyViewText);
		}
	}
}
