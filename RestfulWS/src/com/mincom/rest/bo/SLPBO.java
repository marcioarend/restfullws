package com.mincom.rest.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class SLPBO {
	
	private int id;
	private double wert;
	private int bundeslandId;
	private int geschaeftId;
	private String zeit;
	private List<DatumBO> datuns;
	
	
	/**
	 * @return the datuns
	 */
	@XmlElement(name="dates")
	public List<DatumBO> getDatuns() {
		return datuns;
	}

	/**
	 * @param datuns the datuns to set
	 */
	
	public void setDatum(DatumBO e) {
		this.datuns.add(e);
	}

	public SLPBO(){
		this.datuns = new ArrayList<DatumBO>();
	}
	
	

	/**
	 * @return the wert
	 */
	@XmlElement(name="w")
	public double getWert() {
		return wert;
	}

	/**
	 * @param wert
	 *            the wert to set
	 */
	public void setWert(double wert) {
		this.wert = wert;
	}

	/**
	 * @return the bundeslandId
	 */
	@XmlElement(name="b")
	public int getBundeslandId() {
		return bundeslandId;
	}

	/**
	 * @param bundeslandId
	 *            the bundeslandId to set
	 */
	public void setBundeslandId(int bundeslandId) {
		this.bundeslandId = bundeslandId;
	}

	/**
	 * @return the geschaeftId
	 */
	@XmlElement(name="g")
	public int getGeschaeftId() {
		return geschaeftId;
	}

	/**
	 * @param geschaeftId
	 *            the geschaeftId to set
	 */
	public void setGeschaeftId(int geschaeftId) {
		this.geschaeftId = geschaeftId;
	}

	/**
	 * @return the zeit
	 */
	@XmlElement(name="z")
	public String getZeit() {
		return zeit;
	}

	/**
	 * @param zeit
	 *            the zeit to set
	 */
	public void setZeit(String zeit) {
		this.zeit = zeit;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
