package com.mincom.rest.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;

public class TarifBO {

	private int id;
	private double verbrauchspreis;
	private double grundpreis;
	private double sofortbonus;
	private double neukundenbonus;
	private boolean kaution;
	private int eingeschraenktePreisgarantie;
	private int abschlaege;
	private boolean vorauskasse;
	private int vertragslaufzeit;
	private int verlaengerung;
	private HashMap<Time,PreisBO> preis;
	private String beschreibung;

	public int getId() {
		return id;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getVerbrauchspreis() {
		return verbrauchspreis;
	}

	public void setVerbrauchspreis(double verbrauchspreis) {
		this.verbrauchspreis = verbrauchspreis;
	}

	public double getGrundpreis() {
		return grundpreis;
	}

	public void setGrundpreis(double grundpreis) {
		this.grundpreis = grundpreis;
	}

	public double getSofortbonus() {
		return sofortbonus;
	}

	public void setSofortbonus(double sofortbonus) {
		this.sofortbonus = sofortbonus;
	}

	public double getNeukundenbonus() {
		return neukundenbonus;
	}

	public void setNeukundenbonus(double neukundenbonus) {
		this.neukundenbonus = neukundenbonus;
	}

	public boolean isKaution() {
		return kaution;
	}

	public void setKaution(boolean kaution) {
		this.kaution = kaution;
	}

	public int getEingeschraenktePreisgarantie() {
		return eingeschraenktePreisgarantie;
	}

	public void setEingeschraenktePreisgarantie(int eingeschraenktePreisgarantie) {
		this.eingeschraenktePreisgarantie = eingeschraenktePreisgarantie;
	}

	public int getAbschlaege() {
		return abschlaege;
	}

	public void setAbschlaege(int abschlaege) {
		this.abschlaege = abschlaege;
	}

	public boolean isVorauskasse() {
		return vorauskasse;
	}

	public void setVorauskasse(boolean vorauskasse) {
		this.vorauskasse = vorauskasse;
	}

	public int getVertragslaufzeit() {
		return vertragslaufzeit;
	}

	public void setVertragslaufzeit(int vertragslaufzeit) {
		this.vertragslaufzeit = vertragslaufzeit;
	}

	public int getVerlaengerung() {
		return verlaengerung;
	}

	public void setVerlaengerung(int verlaengerung) {
		this.verlaengerung = verlaengerung;
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
	

	public void populate(ResultSet result){
		try {
			this.setId(result.getInt("idTarif"));
			this.setVerbrauchspreis(result.getDouble("t.Verbrauchspreis"));
			this.setGrundpreis(result.getDouble("t.Grundpreis"));
			this.setSofortbonus(result.getDouble("t.Sofortbonus"));
			this.setNeukundenbonus(result.getDouble("t.Neukundenbonus"));
			this.setKaution(result.getBoolean("t.Kaution"));
			this.setEingeschraenktePreisgarantie(result.getInt("t.Eingeschraenkte_Preisgarantie"));
			this.setAbschlaege(result.getInt("t.Abschlaege"));
			this.setVertragslaufzeit(result.getInt("t.Vertragslaufzeit"));
			this.setVerlaengerung(result.getInt("t.Verlaengerung"));
			this.setBeschreibung(result.getString("t.Beschreibung"));
			this.setKuendigungsfrist(result.getString("t.Kuendigungsfrist"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuffer values = new StringBuffer();
		values.append(" id " + this.getId());
		values.append(" Vertrags " + this.getVertragslaufzeit());
		
		return values.toString();
	}
	
	

}
