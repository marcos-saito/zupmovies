package com.zup.movies.movie.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "movies")
public class MovieEntity {
	@DatabaseField
	private String Title;
	
	@DatabaseField
	private String Year;
	
	@DatabaseField(id = true)
	private String imdbID;
	
	@DatabaseField
	private String type;
	
	@DatabaseField
	private String Poster;
	
	@DatabaseField
	private boolean Inactive = false;
	
	public boolean isInactive() {
		return Inactive;
	}

	public void setInactive(boolean inactive) {
		Inactive = inactive;
	}

	public MovieEntity() {
		
	}
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getYear() {
		return Year;
	}
	public void setYear(String year) {
		Year = year;
	}
	public String getImdbID() {
		return imdbID;
	}
	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPoster() {
		return Poster;
	}
	public void setPoster(String poster) {
		Poster = poster;
	}
}
