package com.zup.movies.util;

public abstract class WebHelper {
	
	private boolean isDebuging = true;

	public final String SERVER_DEV_URL = "http://www.omdbapi.com/";
	public final String SERVER_PROD_URL = "http://www.omdbapi.com/";
	
	public boolean isDebuging() {
		return isDebuging;
	}

	public void setDebuging(boolean isDebuging) {
		this.isDebuging = isDebuging;
	}
	
	public String getServerUrl() {
		return isDebuging ? SERVER_DEV_URL : SERVER_PROD_URL;
	}
}
