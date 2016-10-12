package com.urlshortner.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
//import java.util.B;
import java.util.StringTokenizer;

import com.google.common.hash.Hashing;
import com.sun.jersey.core.util.Base64;

public class AuthenticationUtil {
	
	static LinkedHashMap<String, String> userList = new LinkedHashMap<String, String>();
	
	public String addUser(String accountId) {
		
		if(userList.get(accountId) != null) {
			return null;
		}
		else {
			final String pwd = Hashing.murmur3_32().hashString(accountId, StandardCharsets.UTF_8).toString();
			userList.put(accountId, pwd);
			
			return pwd;
		}
	}
	
	public boolean authenticate(String authCredentials) {

		if (null == authCredentials)
			return false;
		final String encodedUserPassword = authCredentials.replaceFirst("Basic"
				+ " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.decode(
					encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		boolean authenticationStatus = userList.get(username) != null && userList.get(username).equals(password);
		return authenticationStatus;
	}
}