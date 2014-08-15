package com.mincom.inter;

import java.sql.Date;
import java.sql.Time;

public interface Tarif {

	public void setSeason(Season season);
	
	public Season getSeason(String name);
	
	public Preis getPreis(Time uhr, Date date);
	
	public void setName(String voltage);

	public String getName();
	
}
