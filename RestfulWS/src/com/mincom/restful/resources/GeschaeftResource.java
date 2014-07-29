package com.mincom.restful.resources;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.mincom.rest.bo.GeschaeftBO;
import com.mincom.rest.dao.DAO;

@Path("/geschaeft")
public class GeschaeftResource {

	
	static private Map<Integer, GeschaeftBO> geschaeftsMap;

	private DAO dao;
	static {
		geschaeftsMap = new HashMap<Integer, GeschaeftBO>();

		GeschaeftBO g1 = new GeschaeftBO();
		g1.setId(1);
		g1.setName("Gewerbe allgemein");

		geschaeftsMap.put(g1.getId(), g1);
		
		GeschaeftBO g2 = new GeschaeftBO();
		g2.setId(2);
		g2.setName("Gewerbe werktags 8-18");

		geschaeftsMap.put(g2.getId(), g2);

		
	}
	
	public GeschaeftResource(){
		
	}
	
	@GET
	@Produces("application/json")
	public List<GeschaeftBO> getGeschaefts() {
		try {
			DAO dao=new DAO();
			return dao.getAllGeschaeft();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
