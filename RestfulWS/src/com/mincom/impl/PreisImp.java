package com.mincom.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mincom.inter.Preis;


@XmlRootElement
public class PreisImp implements Preis{

	private String name;
	private String uhrAnfang;
	private String uhtEnd;
	private double preis;
	private double gebuehr;
	private List<Time> gueltigkeitAnfang;
	private List<Time> gueltigkeitEnd;
	private JsonArrayBuilder anfang;
	private JsonArrayBuilder end;
	private JsonArrayBuilder allUhr;
	
	
		public PreisImp(){
			gueltigkeitAnfang = new ArrayList<Time>();
			gueltigkeitEnd = new ArrayList<Time>();
			end =  Json.createArrayBuilder();
			anfang = Json.createArrayBuilder();
			
		}
		
		
		public void setGueltigkeit(Time uhrAnfang, Time uhrEnd) {
			gueltigkeitAnfang.add(uhrAnfang);
			gueltigkeitEnd.add(uhrEnd);
			anfang.add(uhrAnfang.toString());
			end.add(uhrEnd.toString());
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

	@XmlElement(name="p")
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
//		JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
//		jsonObjBuilder.add("anfang",anfang);
//		jsonObjBuilder.add("end",end);
//		JsonObject jsonObj = jsonObjBuilder.build();
//		
//		return jsonObj.toString();
		
		return "PreisImp [name=" + name + ", preis=" + preis
				+ ", gueltigkeitAnfang=" + gueltigkeitAnfang
				+ ", gueltigkeitEnd=" + gueltigkeitEnd + "]";
	}


	public JsonArrayBuilder getUhrJson(){
		this.allUhr = Json.createArrayBuilder();
		this.allUhr.add(this.anfang);
		this.allUhr.add(this.end);
		return this.allUhr;
	}


	


	
	

	

}
