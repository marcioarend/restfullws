package com.mincom.inter;

import java.sql.Date;
import java.sql.Time;

import com.mincom.inter.Preis;

public interface Season {
	public static String HIGH_DEMAND = "High Demand";
	
	public static String LOW_DEMAND = "Low Demand";

	public void setPreis(Preis preis);

	public void setPreisSamstag(Preis preis);

	public void setPreisSonntag(Preis preis);

	public void setName(String voltage);

	public String getName();
	
	public Preis getPreis(Time uhr,Date date);
	
	

}
