package com.mincom.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mincom.inter.Preis;
import com.mincom.inter.Season;
import com.mincom.inter.Tarif;

@XmlRootElement
public class TarifImp implements Tarif{
	
	private List<Season> seasonList;
	private Season season;
	private String name;
	private int begin;
	private int end;
	
	
	public TarifImp(){
		this.seasonList = new ArrayList<Season>();
		this.season = new SeasonImp();
		this.begin = 3;
		this.end = 8;
	}
	
	@XmlElement(name="s")
	public void setSeason(Season season) {
		this.seasonList.add(season);
		
	}

	public Season getSeason(int type) {
		
		
		for ( int i = 0;i < seasonList.size(); i++){
			this.season= seasonList.get(i);
			if ( this.season.getType() == type){
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


	public Preis getPreis(Time uhr, Date date) {
		
		if ( (date.getMonth() >= this.getLowDemandSeasonBegin()) &&  date.getMonth() <= this.getLowDemandSeasonEnd()){
			return getSeason(Season.LOW).getPreis(uhr, date);
		}else {
			return getSeason(Season.HIGH).getPreis(uhr, date);
		}
		
		
	}


	@Override
	public void setLowDemandSeason(int begin, int end) {
		this.begin = begin;
		this.end = end;
		
	}


	@Override
	public int getLowDemandSeasonBegin() {
		return this.begin;
	}


	@Override
	public int getLowDemandSeasonEnd() {
		return this.end;
	}


	@XmlElement(name="seasons")
	public List<Season> getSeasons() {
		return this.seasonList;
	}
	
	
	public JsonObjectBuilder getTarifJson(){ 
	
		JsonObjectBuilder thisObject = Json.createObjectBuilder();
		thisObject.add("name",this.name);
		thisObject.add("monateAnfang",this.begin);
		thisObject.add("monateEnde",this.end);
		
		for ( int i=0; i < this.seasonList.size(); i++){
			thisObject.add(this.seasonList.get(i).getName(), this.seasonList.get(i).getSeasonJson());
		}
		
		
		return thisObject;
		
	}
	
	
}
