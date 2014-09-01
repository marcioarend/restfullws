package com.mincom.restful.resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;

import org.json.simple.JSONArray;

import com.mincom.inter.Tarif;
import com.mincom.rest.dao.DAO;

@Path("tarif")
public class TarifResource {

	static private DAO dao;
	static {
		dao = new DAO();
	}
	
	
	@GET
	@Path("allTarife")
	@Produces("application/json")
	public String  getSLPs(){
		try {
			List<Tarif> tarifs =dao.getAllTarifsFromDB(1);
			
			
			return tarifs.get(0).getTarifJson().build().toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
