package com.mincom.restful.resources;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mincom.rest.models.Banda;
import com.mincom.rest.models.Login;

import java.util.UUID;

@Path("/login")
public class LoginResource {
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Login adicionaBanda(Login login) {
		UUID uuid = UUID.randomUUID();
		Login returnLogin = new Login(1,String.valueOf(uuid),"admin","admin","123456");
		
		if (login.getPassword().equals("123456") && login.getUsername().equals("admin")){
			return returnLogin;
		}
		return  null;
	}
	
	@GET
	@Produces("application/json")
	public String getBandas() {
		return "ola";
	}

}
