package com.mincom.inter;

import java.sql.ResultSet;
import java.sql.Time;
import java.util.HashMap;

import com.mincom.rest.bo.PreisBO;

public interface TarifVariabelInter {

	public abstract int getId();

	public abstract String getKuendigungsfrist();

	public abstract void setKuendigungsfrist(String kuendigungsfrist);

	public abstract void setPreis(PreisBO preis,Time time);

	public abstract double getPreis(Time time);

	public abstract void populate(ResultSet result);

	public abstract String toString();

	public abstract String getName();

	public abstract void setName(String name);

	public abstract boolean isDayNight();

	public abstract void setDayNight(boolean dayNight);

	public abstract boolean isDreiPeriod();

	public abstract void setDreiPeriod(boolean dreiPeriod);

	public abstract boolean isJahrzeit();

	public abstract void setJahrzeit(boolean jahrzeit);

	public abstract void setId(int id);
	

}