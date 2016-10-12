
package com.urlshortner;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.hash.Hashing;
import com.sun.jersey.core.util.Base64;
import com.urlshortner.domain.GenericResponse;
import com.urlshortner.domain.RealURL;

@Path("/")
public class URLShortnerService {

	@Context
	private HttpServletRequest request;

	static LinkedHashMap<String, RealURL> shortUrl = new LinkedHashMap<String, RealURL>();
	static LinkedHashMap<String, ArrayList<String>> urlToUserMapping = new LinkedHashMap<String, ArrayList<String>>();

	@POST
	@Path("/tinyURL")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTinyURL(RealURL urlObject) {

		final String id = Hashing.murmur3_32().hashString(urlObject.getUrl(), StandardCharsets.UTF_8).toString();
		shortUrl.put(id, urlObject);
		String username = getUsername(request.getHeader("Authorization"));
		
		//Add user to short url mapping for stat purpose
		ArrayList<String> shortUrls = urlToUserMapping.get(username);
		if (shortUrls == null) {
			shortUrls = new ArrayList<String>();
			shortUrls.add(id);
			urlToUserMapping.put(username, shortUrls);
		} else {
			shortUrls.add(id);
		}
		String tinyUrl = "http://localhost:8080/urlshortner/rest/tinyURL/" + id;
		GenericResponse response = new GenericResponse();
		response.setShortURL(tinyUrl);
		return Response.status(200).entity(response).build();

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response accessTinyURL(@PathParam("id") String id) {

		RealURL realurl = shortUrl.get(id);
		// Increase the access count
		try {
			Integer count = realurl.getAccessCount();
			if (count != null) {
				count++;
			} else {
				count = 1;
			}
			return Response.status(Integer.parseInt(realurl.getRedirectType())).location(new URI(realurl.getUrl()))
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Integer.parseInt(realurl.getRedirectType())).build();

	}

	@GET
	@Path("/stats")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response currentStatus(@PathParam("id") String id) {

		String username = getUsername(request.getHeader("Authorization"));

		LinkedHashMap<String, Integer> stat = new LinkedHashMap<String, Integer>();
		for (String url : urlToUserMapping.get(username)) {
			RealURL realurl = shortUrl.get(url);
			stat.put(realurl.getUrl(), realurl.getAccessCount());
		}
		return Response.status(200).entity(stat).build();

	}

	public String getUsername(String authString) {
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.decode(authString);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		return tokenizer.nextToken();
	}

}
