package com.zup.movies.main.DAO;

import java.util.List;

import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.zup.movies.app.MoviesApplication;
import com.zup.movies.movie.model.MovieEntity;

/*
 *  CLASS NOT ASSYNC PLEASE DON'T STARVE, DEADLOCK, CRASH THE APP
 */
public class MovieDBHelper {

	public synchronized static void createMovie(MovieEntity entity) {
		RuntimeExceptionDao<MovieEntity, Integer> dao = MoviesApplication.OPEN_DB.getSimpleDataDao();
		dao.createOrUpdate(entity);
		Log.v("MovieDBHelper", "1 Row add!");
	}
	
	public synchronized static void deleteMovie(MovieEntity entity) {
		RuntimeExceptionDao<MovieEntity, Integer> dao = MoviesApplication.OPEN_DB.getSimpleDataDao();
		dao.delete(entity);
		Log.v("MovieDBHelper", "1 Row add!");
	}
	
	public synchronized static List<MovieEntity> queryForAllMovies() {
		RuntimeExceptionDao<MovieEntity, Integer> dao = MoviesApplication.OPEN_DB.getSimpleDataDao();
		return dao.queryForAll();
	}
}
