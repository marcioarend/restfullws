package com.mincom.inter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import com.mincom.inter.Preis;

public interface Season {
	public static String HIGH_DEMAND = "HighDemand";
	
	public static String LOW_DEMAND = "LowDemand";
	
	public static int HIGH = 1;
	
	public static int LOW = 0;
	

	public void setPreis(Preis preis);
	
	public List<Preis> getPreis();

	public void setPreisSamstag(Preis preis);
	
	public List<Preis> getPreisSamstag();

	public void setPreisSonntag(Preis preis);
	
	public List<Preis> getPreisSonntag();

	public void setName(String voltage);

	public String getName();
	
	public Preis getPreis(Time uhr,Date date);
	
	public void setType(int demand);
	
	public int getType();
	
	public JsonObjectBuilder getSeasonJson();
	
	

}
