package com.urlshortner.domain;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class RealURL {

	String url;
	String redirectType;
	Integer accessCount;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRedirectType() {
		return redirectType;
	}

	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}

	public Integer getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(Integer accessCount) {
		this.accessCount = accessCount;
	}

	@Override
	public boolean equals(Object obj) {
		RealURL urlobj = (RealURL) obj;
		return url.equals(urlobj.getUrl()) && redirectType.equals(urlobj.getRedirectType());
	}

}
