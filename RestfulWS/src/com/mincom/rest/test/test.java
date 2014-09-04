package com.mincom.rest.test;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.mincom.inter.Season;
import com.mincom.inter.Tarif;
import com.mincom.rest.bo.SimulationBO;
import com.mincom.rest.dao.DAO;

public class test {

	public static void main(String[] args) throws SQLException {
		DAO dao = new DAO();
		
//		JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
//		jsonObjBuilder.add("values",dao.geteinSparrung());
//		JsonObject jsonObj = jsonObjBuilder.build();
//		
//		System.out.println(jsonObj.toString());
		
		
		dao.getBetrag();
		
//		List<Tarif> tarifs =dao.getAllTarifsFromDB(0);
//		for (Iterator iterator = tarifs.iterator(); iterator.hasNext();) {
//			Tarif tarif = (Tarif) iterator.next();
//			System.out.println(tarif.getTarifJson().build().toString());
//			
//		}
		
		
		
		Collection<Integer[]> l = dao.getAllKundeTarif();
		List<Tarif> tarifs =dao.getAllTarifsFromDB(0);
		Tarif tar1 = tarifs.get(0);
		Tarif tar2 = tarifs.get(1);
		Tarif tar3 = tarifs.get(2);
		
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			Integer[] integers = (Integer[]) iterator.next();
			int kundeId = integers[0];
			int tarifId = integers[1];
			System.out.println("Kunde " + kundeId + " Tarif " + tarifId);
			
			List<SimulationBO> simulationBOs = dao.getAllSimulationFurKunde(kundeId,"2013-01-01","2013-12-31");
			// @TODO tarifas fixas para test
			double totalTarif1 = 0;
			double totalTarif2 = 0;
			double totalTarif3 = 0;
			double watt = 0;

			for (SimulationBO simulationBO : simulationBOs) {
				watt += simulationBO.getWert();
				totalTarif1 += tar1.getPreis(simulationBO.getZeit(), simulationBO.getDate()).getPreis() * simulationBO.getWert();
				totalTarif2 += tar2.getPreis(simulationBO.getZeit(), simulationBO.getDate()).getPreis() * simulationBO.getWert();
				totalTarif3 += tar3.getPreis(simulationBO.getZeit(), simulationBO.getDate()).getPreis() * simulationBO.getWert();
			}
			CreateBetrage(dao, kundeId, totalTarif1, totalTarif2, totalTarif3,	watt);
			System.out.println("T1 " + totalTarif1 + " T2 " + totalTarif2 + " T3 " + totalTarif3);	
			
		}

	}

	private static void CreateBetrage(DAO dao, int kundeId, double totalTarif1,
			double totalTarif2, double totalTarif3, double watt)
			throws SQLException {
		dao.createBetragKunde(kundeId,1, totalTarif1, watt);
		dao.createBetragKunde(kundeId,2, totalTarif2, watt);
		dao.createBetragKunde(kundeId,3, totalTarif3, watt);
	}

}
