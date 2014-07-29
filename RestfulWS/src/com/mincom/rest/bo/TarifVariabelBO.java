package com.mincom.rest.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;

public class TarifVariabelBO {

	private int id;
	private String name;
	private boolean dayNight;
	private boolean dreiPeriod;
	private boolean jahrzeit;
	private HashMap<Time,PreisBO> preis;
	private Time time00 = new Time(00, 00, 00);
	private Time time06 = new Time(6, 00, 00);
	private Time time07 = new Time(7, 00, 00);
	private Time time08 = new Time(8, 00, 00);
	private Time time10 = new Time(10, 00, 00);
	private Time time18 = new Time(18, 00, 00);
	private Time time20 = new Time(20, 00, 00);
	private Time time22 = new Time(22, 00, 00);

	public int getId() {
		return id;
	}



	public String getKuendigungsfrist() {
		return kuendigungsfrist;
	}

	public void setKuendigungsfrist(String kuendigungsfrist) {
		this.kuendigungsfrist = kuendigungsfrist;
	}

	private String kuendigungsfrist;

	public HashMap<Time, PreisBO> getPreis() {
		return preis;
	}

	public void setPreis(PreisBO preis) {
		if ( this.preis == null) {
			this.preis = new HashMap<Time, PreisBO>();
		}
		this.preis.put(preis.getAnfangszeit(), preis);
	}
	
	public double getFakePreis(Time time){
		
		if ((time.compareTo(time07) >= 0 && time.compareTo(time08) < 0) || 
			(time.compareTo(time10) >= 0 && time.compareTo(time18) < 0) || 
			(time.compareTo(time20) >= 0 && time.compareTo(time22) < 0)){
			PreisBO p = this.preis.get(time07);
			
			return p.getPreis();
		}else if ((time.compareTo(time08) >= 0 && time.compareTo(time10) < 0) || 
				(time.compareTo(time18) >= 0 && time.compareTo(time20) < 0)){
			PreisBO p = this.preis.get(time08);
			return p.getPreis();
		} else {	
			PreisBO p = this.preis.get(time22);
			return p.getPreis();	
		}
			
			
		
		
	}

	
	public double getFakePreisDayNight(Time time){
		
		if ((time.compareTo(time06) >= 0 && time.compareTo(time22) < 0)){
			PreisBO p = this.preis.get(time06);
			return p.getPreis();
		} else {	
			PreisBO p = this.preis.get(time22);
			return p.getPreis();	
		}
			
			
		
		
	}
	
	
	public void populate(ResultSet result){
		try {
			this.setId(result.getInt("id"));
			this.setName(result.getString("name"));
			this.setDayNight(result.getBoolean("dayNight"));
			this.setDreiPeriod(result.getBoolean("dreiPeriod"));
			this.setJahrzeit(result.getBoolean("jahrzeit"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuffer values = new StringBuffer();
		values.append( this.getName() );
		
		return values.toString();
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public boolean isDayNight() {
		return dayNight;
	}



	public void setDayNight(boolean dayNight) {
		this.dayNight = dayNight;
	}



	public boolean isDreiPeriod() {
		return dreiPeriod;
	}



	public void setDreiPeriod(boolean dreiPeriod) {
		this.dreiPeriod = dreiPeriod;
	}



	public boolean isJahrzeit() {
		return jahrzeit;
	}



	public void setJahrzeit(boolean jahrzeit) {
		this.jahrzeit = jahrzeit;
	}



	public void setId(int id) {
		this.id = id;
	}



	public void setPreis(HashMap<Time, PreisBO> preis) {
		this.preis = preis;
	}
	
	

}
