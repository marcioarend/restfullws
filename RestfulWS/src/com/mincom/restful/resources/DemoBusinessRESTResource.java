package com.mincom.restful.resources;


import java.security.GeneralSecurityException;
import java.util.List;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mincom.rest.auth.DemoAuthenticator;
import com.mincom.rest.auth.DemoBusinessRESTResourceProxy;
import com.mincom.rest.auth.DemoHTTPHeaderNames;

@Path( "demo-business-resource" )
@Stateless( name = "DemoBusinessRESTResource", mappedName = "ejb/DemoBusinessRESTResource" )
public class DemoBusinessRESTResource implements DemoBusinessRESTResourceProxy {

    private static final long serialVersionUID = -6663599014192066936L;

    
    @Override
    @POST
    @Path( "login" )
    @Produces("application/json")
    public Response login( @Context HttpHeaders httpHeaders, @FormParam( "username" ) String username, @FormParam( "password" ) String password ) {
    	System.out.println("Resource  " + httpHeaders);	
        DemoAuthenticator demoAuthenticator = DemoAuthenticator.getInstance();
        List<String> serviceKey = httpHeaders.getRequestHeader( DemoHTTPHeaderNames.SERVICE_KEY );

        try {
            String authToken = demoAuthenticator.login( serviceKey.get(0), username, password );

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "auth_token", authToken );
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();

        } catch ( final LoginException ex ) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add( "message", "Problem matching service key, username and password" );
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        }
    }

    @Override
    public Response demoGetMethod() {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add( "message", "Executed demoGetMethod" );
        JsonObject jsonObj = jsonObjBuilder.build();

        return getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();
    }

    @Override
    public Response demoPostMethod() {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add( "message", "Executed demoPostMethod" );
        JsonObject jsonObj = jsonObjBuilder.build();

        return getNoCacheResponseBuilder( Response.Status.ACCEPTED ).entity( jsonObj.toString() ).build();
    }

    @Override
    public Response logout(
        @Context HttpHeaders httpHeaders ) {
        try {
            DemoAuthenticator demoAuthenticator = DemoAuthenticator.getInstance();
            List<String> serviceKey = httpHeaders.getRequestHeader( DemoHTTPHeaderNames.SERVICE_KEY );
            List<String> authToken = httpHeaders.getRequestHeader( DemoHTTPHeaderNames.AUTH_TOKEN );

            demoAuthenticator.logout( serviceKey.get(0), authToken.get(0) );

            return getNoCacheResponseBuilder( Response.Status.NO_CONTENT ).build();
        } catch ( final GeneralSecurityException ex ) {
            return getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).build();
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


