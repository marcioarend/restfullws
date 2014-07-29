package com.mincom.rest.bo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class WertBO {
	
	private String zeit;
	private String wert;
	
	
	public WertBO(){};
	
	
	public WertBO(String zeit, String wert) {
		super();
		this.zeit = zeit;
		this.wert = wert;
	}


	/**
	 * @return the zeit
	 */
	@XmlElement(name="z")
	public String getZeit() {
		return zeit;
	}
	/**
	 * @param zeit the zeit to set
	 */
	public void setZeit(String zeit) {
		this.zeit = zeit;
	}
	/**
	 * @return the wert
	 */
	@XmlElement(name="w")
	public String getWert() {
		return wert;
	}
	/**
	 * @param wert the wert to set
	 */
	public void setWert(String wert) {
		this.wert = wert;
	}

}
