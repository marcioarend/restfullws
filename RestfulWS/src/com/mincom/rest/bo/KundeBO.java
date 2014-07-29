package com.mincom.rest.bo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KundeBO {
	
	private int id;
	private String name;
	private String beschreibung;
	private double faktor;
	private int bundeslandId;
	private int geschaeftId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public double getFaktor() {
		return faktor;
	}

	public void setFaktor(double faktor) {
		this.faktor = faktor;
	}

	public int getBundeslandId() {
		return bundeslandId;
	}

	public void setBundeslandId(int bundeslandId) {
		this.bundeslandId = bundeslandId;
	}

	public int getGeschaeftId() {
		return geschaeftId;
	}

	public void setGeschaeftId(int geschaeftId) {
		this.geschaeftId = geschaeftId;
	}
	
	public void populate(ResultSet resultSet) throws SQLException{
		this.setId(resultSet.getInt("id"));
		this.setName(resultSet.getString("Name"));
		this.setBeschreibung(resultSet.getString("Beschreibung"));
		this.setFaktor(resultSet.getDouble("Faktor"));
	}

	@Override
	public String toString() {
		return  name + ", \t" + beschreibung ;
	}

}
