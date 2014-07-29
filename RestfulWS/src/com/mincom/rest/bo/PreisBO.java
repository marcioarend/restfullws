package com.mincom.rest.bo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class PreisBO {

	private int id;
	private Time Anfangszeit;
	private Time Endzeit;
	private Double preis;
	private Date Anfangsdatum;
	private int idArtTarif;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getAnfangszeit() {
		return Anfangszeit;
	}

	public void setAnfangszeit(Time anfangszeit) {
		Anfangszeit = anfangszeit;
	}

	public Time getEndzeit() {
		return Endzeit;
	}

	public void setEndzeit(Time endzeit) {
		Endzeit = endzeit;
	}

	public Double getPreis() {
		return preis;
	}

	public void setPreis(Double preis) {
		this.preis = preis;
	}

	public Date getAnfangsdatum() {
		return Anfangsdatum;
	}

	public void setAnfangsdatum(Date anfangsdatum) {
		Anfangsdatum = anfangsdatum;
	}

	public Date getEnddatum() {
		return Enddatum;
	}

	public void setEnddatum(Date enddatum) {
		Enddatum = enddatum;
	}

	private Date Enddatum;

	public void populate(ResultSet resultSet) throws SQLException{
		this.setId(resultSet.getInt("idPreis"));
		this.setAnfangsdatum(resultSet.getDate("Anfangsdatum"));
		this.setEnddatum(resultSet.getDate("Enddatum"));
		this.setAnfangszeit(resultSet.getTime("Anfangszeit"));
		this.setEndzeit(resultSet.getTime("Endzeit"));
		this.setPreis(resultSet.getDouble("Preis"));
		//this.setIdArtTarif(resultSet.getInt("tarifart"));
		
	}

	@Override
	public String toString() {
		StringBuffer values = new StringBuffer();
		values.append("Id Preis " + this.getId());
//		values.append(" anf Datum " + this.getAnfangsdatum());
//		values.append(" end Datum " + this.getEnddatum());
		values.append(" anf Zeit " + this.getAnfangszeit());
		values.append(" end Zeit " + this.getEndzeit());
		values.append(" Preis " + this.getPreis());
		values.append(" Tarif art " + this.getIdArtTarif());
		return values.toString();
	}

	public int getIdArtTarif() {
		return idArtTarif;
	}

	public void setIdArtTarif(int idArtTarif) {
		this.idArtTarif = idArtTarif;
	}
	
	
}
