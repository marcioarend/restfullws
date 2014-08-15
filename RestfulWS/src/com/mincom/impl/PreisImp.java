package com.mincom.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.mincom.inter.Preis;

public class PreisImp implements Preis{

	private String name;
	private String uhrAnfang;
	private String uhtEnd;
	private double preis;
	private double gebuehr;
	private List<Time> gueltigkeitAnfang;
	private List<Time> gueltigkeitEnd;
	
	
	public PreisImp(){
		gueltigkeitAnfang = new ArrayList<Time>();
		gueltigkeitEnd = new ArrayList<Time>();
	}
	
	
	public void setGueltigkeit(Time uhrAnfang, Time uhrEnd) {
		gueltigkeitAnfang.add(uhrAnfang);
		gueltigkeitEnd.add(uhrEnd);
		
	}

	public boolean isGueltig(Time uhr) {
		Time anfang, end;
		for(int i = 0; i < gueltigkeitAnfang.size(); i++){
			anfang = gueltigkeitAnfang.get(i);
			end = gueltigkeitEnd.get(i);
			if ( anfang.compareTo(uhr) <= 0 && uhr.compareTo(end) < 0 ){
				return true;
			}
		}
		return false;
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public void setPreis(double preis) {
		this.preis = preis;
		
	}

	public double getPreis() {
		
		return this.preis;
	}

	public void setGebuehr(double gebuehr) {
		this.gebuehr = gebuehr;
		
	}

	public double getGebuehr() {
		// TODO Auto-generated method stub
		return this.gebuehr;
	}


	@Override
	public String toString() {
		return "PreisImp [name=" + name + ", preis=" + preis
				+ ", gueltigkeitAnfang=" + gueltigkeitAnfang
				+ ", gueltigkeitEnd=" + gueltigkeitEnd + "]";
	}


	


	
	

	

}
