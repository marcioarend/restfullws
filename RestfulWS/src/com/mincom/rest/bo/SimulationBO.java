package com.mincom.rest.bo;

import java.sql.Date;
import java.sql.Time;

public class SimulationBO {

	private Date date;
	private double wert;
	private Time zeit;
	private boolean sonntag;
	private boolean samstag;

	public Date getDate() {
		return date;
	}

	
	public void setDate(Date date) {
		this.date = date;
		sonntag = false;
		samstag = false;
		if ( date.getDay() == 0 ){
			sonntag= true;
		}
		if ( date.getDay() == 6 ){
			samstag = true;
		}
		
	}

	public double getWert() {
		return wert;
	}

	public void setWert(double wert) {
		this.wert = wert;
	}

	public Time getZeit() {
		return zeit;
	}

	public void setZeit(Time zeit) {
		this.zeit = zeit;
	}
	
	public boolean isSonntag(){
		return this.sonntag;
	}
	
	public boolean isSamstag(){
		return this.samstag;
	}

	@Override
	public String toString() {
		return "SBO [d=" + date + ", w=" + wert + ", z="
				+ zeit + ", s=" + sonntag + ", s=" + samstag + "]";
	}
	
	

}
