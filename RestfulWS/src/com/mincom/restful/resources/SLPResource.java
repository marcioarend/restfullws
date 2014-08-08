package com.mincom.restful.resources;

import javax.ws.rs.Path;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mincom.rest.bo.DatumBO;
import com.mincom.rest.bo.GeschaeftBO;
import com.mincom.rest.bo.SLPBO;
import com.mincom.rest.bo.WertBO;
import com.mincom.rest.dao.DAO;
import com.mincom.rest.models.Banda;

@Path("/SLP")
public class SLPResource {

	static private Map<Integer, SLPBO> maps;

	static private DAO dao;
	static {
		maps = new HashMap<Integer, SLPBO>();
		dao = new DAO();
		SLPBO g1 = new SLPBO();
		
		g1.setId(1);
		g1.setBundeslandId(1);
		g1.setGeschaeftId(1);
		DatumBO  datumBO = new DatumBO("2014-01-01");
		g1.setDatum(datumBO);
		datumBO.setValues(new WertBO("00:00:00","0.00000"));
		datumBO.setValues(new WertBO("00:15:00","0.00500"));
		datumBO.setValues(new WertBO("00:30:00","0.00700"));
		datumBO.setValues(new WertBO("00:45:00","0.00900"));
		datumBO.setValues(new WertBO("01:00:00","0.01000"));
		
		datumBO = new DatumBO("2014-01-02");
		g1.setDatum(datumBO);
		datumBO.setValues(new WertBO("00:00:00","0.00000"));
		datumBO.setValues(new WertBO("00:15:00","0.00500"));
		datumBO.setValues(new WertBO("00:30:00","0.00700"));
		datumBO.setValues(new WertBO("00:45:00","0.00900"));
		datumBO.setValues(new WertBO("01:00:00","0.01000"));

		

		maps.put(g1.getId(), g1);


		
	}
	
	
	
	
	@GET
	@Produces("application/json")
	public List<SLPBO> getSLPs(){
		return new ArrayList<SLPBO>(maps.values());
	}
	
	@Path("{begin}/{end}/{bl}/{ge}")
	@GET
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
