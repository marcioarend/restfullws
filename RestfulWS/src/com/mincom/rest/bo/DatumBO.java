package com.mincom.rest.bo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatumBO {

	private String datum;

	private List<WertBO> values;

	public DatumBO(){
		this.values = new ArrayList<WertBO>();
	}
	
	public DatumBO(String datum){
		super();
		this.values = new ArrayList<WertBO>();
		this.datum= datum;
	}
	
	public void setValues(WertBO wert) {
		this.values.add(wert);
	}

	@XmlElement(name = "v")
	public List<WertBO> getValues() {
		return this.values;
	}

	/**
	 * @return the datum
	 */
	@XmlElement(name="d")
	public String getDatum() {
		return datum;
	}

	/**
	 * @param datum the datum to set
	 */
	public void setDatum(String datum) {
		this.datum = datum;
	}

}
