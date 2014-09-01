package com.mincom.inter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public interface Tarif {

	public void setSeason(Season season);

	public Season getSeason(int type);

	public List<Season> getSeasons();

	public Preis getPreis(Time uhr, Date date);

	public void setName(String voltage);

	public String getName();

	public void setLowDemandSeason(int begin, int end);

	public int getLowDemandSeasonBegin();

	public int getLowDemandSeasonEnd();
	
	public JsonObjectBuilder getTarifJson();

}
