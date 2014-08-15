package com.mincom.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.mincom.inter.Season;
import com.mincom.inter.Preis;

public class SeasonImp implements Season{
	private List<Preis> listPreis;
	private List<Preis> listSamstag;
	private List<Preis> listSonntag;
	private String name;
	
	public SeasonImp(){
		this.listPreis = new ArrayList<Preis>();
		this.listSamstag = new ArrayList<Preis>();
		this.listSonntag = new ArrayList<Preis>();
	}
	

	

	public void setName(String name) {
		this.name = name;
		
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		
		return "Season " + this.name + "listPreis=" + listPreis + ", listSamstag="
				+ listSamstag + ", listSonntag=" + listSonntag + ", name="
				+ name ; 
	}




	public void setPreis(Preis preis) {
			this.listPreis.add(preis);
	}
	
	public Preis getPreis(Time uhr,Date date){
		
		
		return this.getPreis(uhr,date.getDay());
	}

	private Preis getPreis(Time uhr, int day) {
		Preis preis = null;
		if ( day !=0 && day != 6 ){
			for (int i = 0; i < listPreis.size(); i++) {
				preis = listPreis.get(i);
				if ( preis.isGueltig(uhr)){
					return preis;
				}
				
			}
		}else if ( day == 6){
			for (int i = 0; i < listSamstag.size(); i++) {
				preis = listSamstag.get(i);
				if ( preis.isGueltig(uhr)){
					return preis;
				}
				
			}
		}else {
			for (int i = 0; i < listSonntag.size(); i++) {
				preis = listSonntag.get(i);
				if ( preis.isGueltig(uhr)){
					return preis;
				}
				
			}
		}
		
		return null;
	}	
	
	public void setPreisSamstag(Preis preis) {
			this.listSamstag.add(preis);
		
		
		
	}


	public void setPreisSonntag(Preis preis) {
			this.listSonntag.add(preis);
		
	}
	
}
