package com.urlshortner.domain;

//{success: 'true', description: 'Your account is opened', password: 'xC345Fc0'}
public class GenericResponse {

	Boolean success;
	String description;
	String password;
	String shortURL;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getShortURL() {
		return shortURL;
	}

	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}

}
