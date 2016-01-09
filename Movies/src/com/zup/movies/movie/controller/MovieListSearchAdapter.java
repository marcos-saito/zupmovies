package com.zup.movies.movie.controller;

import java.util.List;

import com.squareup.picasso.Picasso;
import com.zup.movies.R;
import com.zup.movies.movie.model.MovieEntity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieListSearchAdapter extends ArrayAdapter<MovieEntity> {

	private Activity context;
	private List<MovieEntity> objects;
	
	public class MovieItemViewHolder {
		final ImageView posterImage;
		final TextView textView,movieYear, movieTypeText;
		
		public MovieItemViewHolder(View view) {
			posterImage = (ImageView) view.findViewById(R.id.posterImage);
			textView = (TextView) view.findViewById(R.id.movieTitle);
			movieYear = (TextView) view.findViewById(R.id.movieYear);
			movieTypeText = (TextView) view.findViewById(R.id.movieTypeText);
		}
		
		public void fillData(MovieEntity objectToShow) {
			textView.setText(objectToShow.getTitle());
			movieYear.setText(objectToShow.getYear());
			movieTypeText.setText(objectToShow.getType());
		}
	}

	public MovieListSearchAdapter(Activity context, List<MovieEntity> objects) {
		super(context, 0, objects);
		this.context = context;
		this.objects = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		MovieItemViewHolder holder = null;
		final MovieEntity objectToShow;
		
		if(convertView == null) {
			objectToShow = objects.get(position);
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.movie_item, null);
			holder = new MovieItemViewHolder(view);
			
			holder.textView.setText(objectToShow.getTitle());
			holder.movieYear.setText(objectToShow.getYear());
			holder.movieTypeText.setText(objectToShow.getType());
			
			String url = objectToShow.getPoster();
			
			if(!url.equals("N/A"))
				Picasso.with(context).load(url).into(holder.posterImage);
			else 
				holder.posterImage.setImageResource(R.drawable.default_poster);
			view.setTag(holder);
		} else {
			objectToShow = objects.get(position);
			view = convertView;
			holder = (MovieItemViewHolder) view.getTag();
			
			holder.textView.setText(objectToShow.getTitle());
			holder.movieYear.setText(objectToShow.getYear());
			holder.movieTypeText.setText(objectToShow.getType());
			
			String url = objectToShow.getPoster();
			
			if(!url.equals("N/A"))
				Picasso.with(context).load(objectToShow.getPoster()).into(holder.posterImage);
			else 
				holder.posterImage.setImageResource(R.drawable.default_poster);
		}
		
		return view;
	}

}
