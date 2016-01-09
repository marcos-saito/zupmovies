package com.zup.movies.util;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zup.movies.movie.model.MovieEntity;

public class DatabaseOpenHelper extends OrmLiteSqliteOpenHelper{

	private static final String DATABASE_NAME = "moviesapp.db";
	private static final int DATABASE_VERSION = 1;
	
	private Dao<MovieEntity, Integer> movieDao = null;
	private RuntimeExceptionDao<MovieEntity, Integer> movieRuntimeDao = null;
	
	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			TableUtils.createTable(connectionSource, MovieEntity.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {}
	
	public RuntimeExceptionDao<MovieEntity, Integer> getSimpleDataDao() {
		if (movieRuntimeDao == null) {
			movieRuntimeDao = getRuntimeExceptionDao(MovieEntity.class);
		}
		return movieRuntimeDao;
	}
	
	public Dao<MovieEntity, Integer> getDao() throws SQLException {
		if (movieDao == null) {
			movieDao = getDao(MovieEntity.class);
		}
		return movieDao;
	}
}
