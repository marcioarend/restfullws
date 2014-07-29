package com.mincom.rest.test;

import java.sql.SQLException;

import com.mincom.rest.dao.DAO;

public class test {

	public static void main(String[] args) throws SQLException {
		DAO dao = new DAO();
		System.out.println(dao.getSLPFromBundsGeschaeft("2013-01-01","2013-12-31",4,7));

	}

}
