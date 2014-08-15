package com.mincom.rest.test;

import java.sql.SQLException;
import java.util.List;

import com.mincom.inter.Tarif;
import com.mincom.rest.bo.SimulationBO;
import com.mincom.rest.dao.DAO;

public class test {

	public static void main(String[] args) throws SQLException {
		DAO dao = new DAO();
		List<Tarif> tarifs =dao.getAllTarifsFromDB();
		List<SimulationBO> simulationBOs = dao.getAllSimulationFurKunde(310,"2013-01-01","2013-12-31");

		Tarif tar1 = tarifs.get(2);
		double total = 0;
		double totalFix = 0;
		int quatro = 0;
		int oito = 0;
		int vinte = 0;
		
		
		for (SimulationBO simulationBO : simulationBOs) {
//			System.out.println( " Uhr " + simulationBO.getZeit() + " dia da semana " + simulationBO.getDate().getDay() + " Preis " + tar1.getPreis(simulationBO.getZeit(), simulationBO.getDate()).getPreis() + " wert " +simulationBO.getWert() );
//			System.out.println(tar1.getPreis(simulationBO.getZeit(), simulationBO.getDate()).getPreis());
			total += tar1.getPreis(simulationBO.getZeit(), simulationBO.getDate()).getPreis() * simulationBO.getWert();
			totalFix += 0.25 * simulationBO.getWert();
		}
		System.out.println("Total " + total + " total fix " + totalFix);
	}

}
