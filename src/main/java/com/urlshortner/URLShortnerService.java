
package com.urlshortner;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.common.hash.Hashing;
import com.urlshortner.domain.GenericResponse;
import com.urlshortner.domain.RealURL;

@Path("/tinyURL")
public class URLShortnerService {

	static LinkedHashMap<String, RealURL> shortUrl = new LinkedHashMap<String, RealURL>();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTinyURL(RealURL urlObject) {

		final String id = Hashing.murmur3_32().hashString(urlObject.getUrl(), StandardCharsets.UTF_8).toString();
		shortUrl.put(id, urlObject);
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
		try {
			return Response.status(Integer.parseInt(realurl.getRedirectType())).location(new URI(realurl.getUrl()))
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Integer.parseInt(realurl.getRedirectType())).build();

	}

}
