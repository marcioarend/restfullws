package com.mincom.restful.resources;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.mincom.rest.bo.SLPBO;
import com.mincom.rest.dao.DAO;

@Path("SLP")
public class SLPResource {

	static private Map<Integer, SLPBO> maps;

	static private DAO dao;
	static {
		maps = new HashMap<Integer, SLPBO>();
		dao = new DAO();
	}
	
	
	
	
	@GET
	@Path("allSLP")
	@Produces("application/json")
	public SLPBO getSLPs(){
		try {
			return dao.getSLPFromBundsGeschaeft("2013-01-01", "2013-01-02", 4, 7);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Path("getSLPByID/{begin}/{end}/{bl}/{ge}")
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	public SLPBO getSPL(@PathParam("begin") String begin, @PathParam("end") String end,@PathParam("bl") int bl,@PathParam("ge") int ge) {
		System.out.println(begin + end + bl + ge);
		try {
			return dao.getSLPFromBundsGeschaeft(begin, end, bl, ge);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
