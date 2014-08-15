package com.mincom.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.mincom.inter.Preis;
import com.mincom.inter.Season;
import com.mincom.inter.Tarif;

public class TarifImp implements Tarif{
	
	private List<Season> seasonList;
	private Season season;
	private String name;
	
	public TarifImp(){
		this.seasonList = new ArrayList<Season>();
		this.season = new SeasonImp();
	}
	
	
	public void setSeason(Season season) {
		this.seasonList.add(season);
		
	}

	public Season getSeason(String name) {
		
		
		for ( int i = 0;i < seasonList.size(); i++){
			this.season= seasonList.get(i);
			if ( this.season.getName().equals(name)){
				return this.season;
			}
		}
		return null;
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		
		return "Tarif " + this.name + this.seasonList;
	}


	@Override
	public Preis getPreis(Time uhr, Date date) {
		
		if ( (date.getMonth() >= 3) &&  date.getMonth() <= 8){
			return getSeason(Season.LOW_DEMAND).getPreis(uhr, date);
		}else {
			return getSeason(Season.HIGH_DEMAND).getPreis(uhr, date);
		}
		
		
	}
	
}
