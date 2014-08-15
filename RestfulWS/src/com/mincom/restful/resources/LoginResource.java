package com.mincom.restful.resources;


import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.mincom.rest.auth.DemoAuthenticator;
import com.mincom.rest.auth.DemoHTTPHeaderNames;
import com.mincom.rest.models.Banda;
import com.mincom.rest.models.Login;

import java.util.UUID;

@Path("restLogin")
public class LoginResource {
	
	@POST
    @Path( "login" )
	@Produces("application/json")
	@Consumes("application/json")
	public Response login(@Context HttpHeaders httpHeaders,Login l ){
		List<String> serviceKey = httpHeaders.getRequestHeader( DemoHTTPHeaderNames.SERVICE_KEY );
		List<String> token = httpHeaders.getRequestHeader( DemoHTTPHeaderNames.AUTH_TOKEN );
		
		System.out.println("token "+ token + " service "  + serviceKey + " " + l.getPassword() + " " + l.getUsername()); //+  " user " + username + " pwd " + password );
		
		DemoAuthenticator demoAuthenticator = DemoAuthenticator.getInstance();
		
		String authToken;
		try {
			authToken = demoAuthenticator.login( serviceKey.get(0), l.getUsername(), l.getPassword() );
			JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
	        jsonObjBuilder.add( "auth_token", authToken );
	        JsonObject jsonObj = jsonObjBuilder.build();

	        return getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();
		} catch (final LoginException ex ) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", "Problem matching service key, username and password" );
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
		}
		
		
		
		
	}
	
	private Response.ResponseBuilder getNoCacheResponseBuilder( Response.Status status ) {
        CacheControl cc = new CacheControl();
        cc.setNoCache( true );
        cc.setMaxAge( -1 );
        cc.setMustRevalidate( true );

        return Response.status( status ).cacheControl( cc );
    }

}
