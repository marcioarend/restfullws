package com.mincom.rest.dao;

import java.util.List;

import com.mincom.rest.bo.KundeBO;

public class SimulationDAO {
	private  KundeBO kunde;
	private List simulations;

	public KundeBO getKunde() {
		return kunde;
	}

	public void setKunde(KundeBO kunde) {
		this.kunde = kunde;
	}

	public List getSimulations() {
		return simulations;
	}

	public void setSimulations(List simulations) {
		this.simulations = simulations;
	}

	@Override
	public String toString() {
		if ( kunde != null){
			return  kunde.toString();
		}
		
		return "Kein Kunde";
	}

}
