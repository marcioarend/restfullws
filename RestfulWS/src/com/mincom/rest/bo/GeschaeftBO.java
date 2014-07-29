package com.mincom.rest.bo;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GeschaeftBO {
	private int id;
	private String name;
	
	
	/**
	 * @return the id
	 */
	@XmlElement(name="id")
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	public void populate(ResultSet resultSet) throws SQLException{
		this.setId(resultSet.getInt("id"));
		this.setName(resultSet.getString("Name"));
	}

	
}
