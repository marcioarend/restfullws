package com.mincom.impl;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import jdk.nashorn.internal.runtime.ListAdapter;

import com.mincom.inter.Season;
import com.mincom.inter.Preis;

public class SeasonImp implements Season {
	private List<Preis> listPreis;
	private List<Preis> listSamstag;
	private List<Preis> listSonntag;
	private JsonObjectBuilder preisObjct;
	private JsonArrayBuilder preisArray;
	private JsonObjectBuilder samstagObjct;
	private JsonArrayBuilder samstagArray;
	private JsonObjectBuilder sonntagObjct;
	private JsonArrayBuilder sonntagArray;
	
	
	
	private String name;
	private int type;

	public SeasonImp() {
		this.listPreis = new ArrayList<Preis>();
		this.listSamstag = new ArrayList<Preis>();
		this.listSonntag = new ArrayList<Preis>();
		this.preisObjct = Json.createObjectBuilder();
		this.preisArray = Json.createArrayBuilder();
		this.samstagObjct = Json.createObjectBuilder();
		this.samstagArray = Json.createArrayBuilder();
		this.sonntagObjct = Json.createObjectBuilder();
		this.sonntagArray = Json.createArrayBuilder();
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getName() {
		return this.name;
	}

	

	public Preis getPreis(Time uhr, Date date) {

		return this.getPreis(uhr, date.getDay());
	}

	private Preis getPreis(Time uhr, int day) {
		Preis preis = null;
		if (day != 0 && day != 6) {
			for (int i = 0; i < listPreis.size(); i++) {
				preis = listPreis.get(i);
				if (preis.isGueltig(uhr)) {
					return preis;
				}

			}
		} else if (day == 6) {
			for (int i = 0; i < listSamstag.size(); i++) {
				preis = listSamstag.get(i);
				if (preis.isGueltig(uhr)) {
					return preis;
				}

			}
		} else {
			for (int i = 0; i < listSonntag.size(); i++) {
				preis = listSonntag.get(i);
				if (preis.isGueltig(uhr)) {
					return preis;
				}

			}
		}

		return null;
	}

	
	public void setPreis(Preis preis) {
		this.listPreis.add(preis);
		
	}
	
	
	public void setPreisSamstag(Preis preis) {
		this.listSamstag.add(preis);
		
	}

	public void setPreisSonntag(Preis preis) {
		this.listSonntag.add(preis);
		

	}

	@Override
	public void setType(int demand) {
		this.type = demand;

	}

	@Override
	public int getType() {

		return this.type;
	}

	public List<Preis> getPreis() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Preis> getPreisSamstag() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Preis> getPreisSonntag() {
		// TODO Auto-generated method stub
		return null;
	}

	public JsonObjectBuilder getSeasonJson(){
		
		for (int i = 0; i < listPreis.size(); i++) {
			preisObjct = Json.createObjectBuilder();
			Preis preis = listPreis.get(i);
			if (preis.getPreis() != 0){
				preisObjct.add("preis",preis.getPreis());
				preisObjct.add("uhr" , preis.getUhrJson());
				this.preisArray.add(preisObjct);
			}
			
		}
		
		for (int i = 0; i < listSamstag.size(); i ++){
			samstagObjct = Json.createObjectBuilder();
			Preis preis = listSamstag.get(i);
			if (preis.getPreis() != 0){
				samstagObjct.add("preis", preis.getPreis());
				samstagObjct.add("uhr" , preis.getUhrJson());
				this.samstagArray.add(samstagObjct);
			}	
		}
		
		for (int i = 0; i < listSonntag.size(); i ++){
			sonntagObjct = Json.createObjectBuilder();
			Preis preis = listSonntag.get(i);
			if (preis.getPreis() != 0){
				sonntagObjct.add("preis", preis.getPreis());
				sonntagObjct.add("uhr" , preis.getUhrJson());
				this.sonntagArray.add(sonntagObjct);
			}	
		}
		
		
		
		
		JsonObjectBuilder thisObject = Json.createObjectBuilder();
		
		thisObject.add("name",this.name);
		thisObject.add("type",this.type);
		thisObject.add("wochentag", this.preisArray);
		thisObject.add("samstag",this.samstagArray);
		thisObject.add("sonntag",this.sonntagArray);
		
		
		return thisObject;
	}
 	
	
	@Override
	public String toString() {

		
		return "Season " + this.name + "listPreis=" + listPreis
				+ ", listSamstag=" + listSamstag + ", listSonntag="
				+ listSonntag + ", name=" + name;

	}

}
