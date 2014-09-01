package com.mincom.restful.resources;

import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.mincom.rest.dao.DAO;


@Path("bericht")
public class BerichtResource {

	static private DAO dao;
	static {
		dao = new DAO();
	}	
	
	
	
	@GET
	@Path("gesamtumsatz")
	@Produces("application/json" )
	public String getGesamtumsatz(){
		try {
			JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
			jsonObjBuilder.add("values",dao.getGesamtumsatz());
			JsonObject jsonObj = jsonObjBuilder.build();
			return jsonObj.toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
 }
