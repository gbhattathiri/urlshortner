package com.urlshortner;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.urlshortner.domain.GenericResponse;
import com.urlshortner.util.AuthenticationUtil;

@Path("/register")
public class RegisterService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String accountId) {

		AuthenticationUtil register = new AuthenticationUtil();

		String pwd = register.addUser(accountId);

		GenericResponse resp = new GenericResponse();
		if (pwd == null) {
			resp.setSuccess(false);
			resp.setDescription("Account already exist");
		} else {
			resp.setSuccess(false);
			resp.setDescription("Account successfully created");
			resp.setPassword(pwd);
		}

		return Response.status(200).entity(resp).build();

	}

}
