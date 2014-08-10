package com.mincom.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mincom.rest.bo.*;

public class DAO {
	private Connection connection = null;

	public Connection getConnection() {
		return this.connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public DAO() {
		this.setConnection(Dbconnnection.getInstance().getConnection());
	}

	/**
	 * @param Bundesland
	 * @param Geschaeft
	 * @param AnzahlKunstlicher
	 * @param datumAnfang
	 * @param datumEnd
	 * @param faktor
	 * @param ansteigenFaktor
	 * @throws SQLException
	 * 
	 * 
	 */
	public void CreateKunstlicherNutzer(int Bundesland, int Geschaeft,
			int AnzahlKunstlicher, double faktor, double ansteigenFaktor)
			throws SQLException {

		Map<Integer, String> bundsLandMap = this.getbundesLandMapIntString();
		Map<Integer, String> geschaeftMap = this.getGeschaeftMapIntString();

		String value = "INSERT INTO Kunde  (Name, Beschreibung, Faktor, Bundesland_id, Geschaeft_id) VALUES "
				+ "( ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = this.getConnection()
				.prepareStatement(value);
		for (int i = 0; i < AnzahlKunstlicher; i++) {

			preparedStatement.setString(1, "Kunde " + i);
			preparedStatement.setString(2, bundsLandMap.get(Bundesland) + " "
					+ geschaeftMap.get(Geschaeft) + " faktor "
					+ (faktor + ansteigenFaktor * i));
			preparedStatement.setDouble(3, faktor + ansteigenFaktor * i);
			preparedStatement.setInt(4, Bundesland);
			preparedStatement.setInt(5, Geschaeft);
			preparedStatement.executeUpdate();

		}

	}

	/**
	 * 
	 * @param datumAnfang
	 * @param datumEnd
	 * @throws SQLException
	 */
	public void createKunderProfilwert(String datumAnfang, String datumEnd)
			throws SQLException {
		Statement statement = this.getConnection().createStatement();

		String befehl = "insert into Simulation "
				+ "(Wert,Datum,Zeit,Kunde_id,Bundesland_id,Geschaeft_id) "
				+ " select "
				+ " p.Wert*k.Faktor, p.Datum, p.Zeit, k.id,k.Bundesland_id,k.Geschaeft_id "
				+ " from SLP as p, Kunde as k  where "
				+ " Datum between Date('"
				+ datumAnfang
				+ "') and Date('"
				+ datumEnd
				+ "') "
				+ " and k.Bundesland_id = p.Bundesland_id and k.Geschaeft_id = p.Geschaeft_id;";

		statement.execute(befehl);

	}

	public Collection<TarifBO> getListTarife() throws SQLException {
		Map<Integer, TarifBO> tarifBOs = new HashMap<Integer, TarifBO>();
		TarifBO tarifBO = new TarifBO();
		PreisBO preisBO = new PreisBO();
		Statement statement;
		ResultSet resultSet = null;

		statement = this.getConnection().createStatement();
		resultSet = statement
				.executeQuery(""
						+ "SELECT "
						+ "t.id as idTarif,t.Grundpreis,t.Sofortbonus,t.Abschlaege,t.Beschreibung,t.Eingeschraenkte_Preisgarantie,t.Kaution, "
						+ "t.Kuendigungsfrist,t.Neukundenbonus,t.Verbrauchspreis,t.Verlaengerung,t.Vertragslaufzeit, "
						+ " pre.id as idPreis ,pre.Anfangszeit,pre.Endzeit,pre.Anfangsdatum,pre.Enddatum,pre.Preis "
						+ "FROM Tarif_has_Preis as tp, Preis as pre, Tarif as t "
						+ "where t.id=tp.Tarif_id and tp.Preis_id=pre.id ");
		while (resultSet.next()) {
			tarifBO = new TarifBO();
			preisBO = new PreisBO();
			preisBO.populate(resultSet);
			if (tarifBOs.containsKey(resultSet.getInt("idTarif")) == true) {
				tarifBO = tarifBOs.get(resultSet.getInt("idTarif"));
			} else {
				tarifBO.populate(resultSet);
				tarifBOs.put(resultSet.getInt("idTarif"), tarifBO);
			}
			tarifBO.setPreis(preisBO);

		}
		return tarifBOs.values();

	}

	
	
	public Collection<TarifVariabelBO> getListTarifeVariabel() throws SQLException {
		Map<Integer, TarifVariabelBO> tarifBOs = new HashMap<Integer, TarifVariabelBO>();
		TarifVariabelBO tarifBO = new TarifVariabelBO();
		PreisBO preisBO = new PreisBO();
		Statement statement;
		ResultSet resultSet = null;

		statement = this.getConnection().createStatement();
		resultSet = statement
				.executeQuery(	" select vt.id as id, vt.name as name ,  vt.DayNight as dayNight, vt.DreiPeriod as dreiPeriod, " + 
								" vt.Jahrzeit as jahrzeit, p.Anfangszeit as anfangzeit, p.Endzeit as endzeit, " +
								" p.id as idPreis, p.Preis as Preis, p.Anfangsdatum as Anfangsdatum , p.Enddatum as Enddatum," + 
								" p.Anfangszeit as  Anfangszeit, p.Endzeit as Endzeit, p.Trafiart as tarifart " +
								" from Preis as p, Variabeltarif as vt, Variabeltarif_has_Preis as vhp  " +
								" where vhp.Preis_id=p.id and vhp.Variabeltarif_id=vt.id ");
		while (resultSet.next()) {
			tarifBO = new TarifVariabelBO();
			preisBO = new PreisBO();
			preisBO.populate(resultSet);
			if (tarifBOs.containsKey(resultSet.getInt("id")) == true) {
				tarifBO = tarifBOs.get(resultSet.getInt("id"));
			} else {
				tarifBO.populate(resultSet);
				tarifBOs.put(resultSet.getInt("id"), tarifBO);
			}
			tarifBO.setPreis(preisBO);

		}
		return tarifBOs.values();

	}
	
	
	
	
	
	
	
	public List<KundeBO> getAllKunden() throws SQLException {
		List<KundeBO> kundeBOs = new ArrayList<KundeBO>();
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();

		resultSet = statement.executeQuery("Select * from Kunde");
		while (resultSet.next()) {
			KundeBO kundeBO = new KundeBO();
			kundeBO.populate(resultSet);
			kundeBOs.add(kundeBO);

		}

		return kundeBOs;
	}

	public SLPBO getAllSLP(String datumAnfang,String datumEnd) throws SQLException{
		SLPBO slpbo = new SLPBO();
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		String newDate = "";
		String oldDate = "0000";
		resultSet = statement.executeQuery("Select * from SLP where "
				+ " Datum between Date('"
				+ datumAnfang
				+ "') and Date('"
				+ datumEnd
				+ "') "
				);
		DatumBO datumBO=null; 
		int i=0;
		while (resultSet.next()){
			newDate = resultSet.getString("datum");
			
			if (!newDate.equals(oldDate)){
					datumBO = new DatumBO(newDate);
					datumBO.setValues(new WertBO(resultSet.getString("Zeit"),resultSet.getString("Wert")));
					oldDate=newDate;
					slpbo.setDatum(datumBO);
					i++;
			} else {
					datumBO.setValues(new WertBO(resultSet.getString("Zeit"),resultSet.getString("Wert")));
					i++;
			}
		}
		
		System.out.println(i);
		return slpbo;
	}
	
	
	
	
	public SLPBO getSLPFromBundsGeschaeft(String datumAnfang,String datumEnd,int idBundesland, int idGeschaeft) throws SQLException{
		SLPBO slpbo = new SLPBO();
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		String newDate = "";
		String oldDate = "0000";
		resultSet = statement.executeQuery("Select * from SLP where "
				+ "Bundesland_id = " + idBundesland 
				+ " and Geschaeft_id = " + idGeschaeft
				+ " and Datum between Date('"
				+ datumAnfang
				+ "') and Date('"
				+ datumEnd
				+ "') "
				);
		DatumBO datumBO=null; 
		int i=0;
		while (resultSet.next()){
			newDate = resultSet.getString("datum");
			
			if (!newDate.equals(oldDate)){
					datumBO = new DatumBO(newDate);
					datumBO.setValues(new WertBO(resultSet.getString("Zeit"),resultSet.getString("Wert")));
					oldDate=newDate;
					slpbo.setDatum(datumBO);
					i++;
			} else {
					datumBO.setValues(new WertBO(resultSet.getString("Zeit"),resultSet.getString("Wert")));
					i++;
			}
		}
		
		return slpbo;
	}
	
	public List<GeschaeftBO> getAllGeschaeft()throws SQLException {
		List<GeschaeftBO> geschaefts = new ArrayList<GeschaeftBO>();
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();

		resultSet = statement.executeQuery("Select * from Geschaeft");
		while (resultSet.next()) {
			GeschaeftBO bo = new GeschaeftBO();
			bo.populate(resultSet);
			geschaefts.add(bo);

		}

		return geschaefts;
	}
	
	
	public List<Double> SPL(int idBundesland, int idGeschaft, String datumAnfang, String datumEnd)
			throws SQLException {
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		List<Double> list = new ArrayList<Double>();
		String befehl = "Select * from SLP where"
				+ " Bundesland_id = "+ idBundesland 
				+ " and  Geschaeft_id = " + idGeschaft
				+ " and  Datum = Date('"
				+ datumAnfang
				+ "');";
		System.out.println(befehl);
		resultSet = statement
				.executeQuery(befehl);
		while(resultSet.next()){
			list.add(resultSet.getDouble("Wert"));
		}

		return list;
	}

	public String applyTarifToUser(Collection<TarifBO> tarifBOs, int kundId)
			throws SQLException {
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		Iterator<TarifBO> tempTarifs = tarifBOs.iterator();
		TarifBO fixTarifBO = tempTarifs.next();
		Map<Time, PreisBO> preisFixMap = fixTarifBO.getPreis();
		TarifBO variiertTarifBO = tempTarifs.next();
		Map<Time, PreisBO> preisVariiertMap = variiertTarifBO.getPreis();

		Time time00 = new Time(00, 00, 00);
		Time time08 = new Time(8, 00, 00);
		Time time18 = new Time(18, 00, 00);

		double variiertPreisSumme = 0.0;
		double fixPreisSumme = 0.0;

		resultSet = statement
				.executeQuery("Select * from Simulation where Kunde_id = "
						+ kundId);
		while (resultSet.next()) {
			fixPreisSumme += resultSet.getDouble("Wert")
					* preisFixMap.get(time00).getPreis();
			if (resultSet.getTime("Zeit").compareTo(time00) > 0
					&& resultSet.getTime("Zeit").compareTo(time08) < 0) {
				// System.out.println( resultSet.getTime("Zeit") + " " +
				// preisMap.get(time00));
				variiertPreisSumme += resultSet.getDouble("Wert")
						* preisVariiertMap.get(time00).getPreis();
			} else if (resultSet.getTime("Zeit").compareTo(time08) > 0
					&& resultSet.getTime("Zeit").compareTo(time18) < 0) {
				// System.out.println( resultSet.getTime("Zeit") + " " +
				// preisMap.get(time08));
				variiertPreisSumme += resultSet.getDouble("Wert")
						* preisVariiertMap.get(time08).getPreis();
			} else {
				// System.out.println( resultSet.getTime("Zeit") + " " +
				// preisMap.get(time18));
				variiertPreisSumme += resultSet.getDouble("Wert")
						* preisVariiertMap.get(time18).getPreis();
			}

		}
		return ";" + fixPreisSumme + ";" + variiertPreisSumme;

	}

	
	public String applyVaribelTarif(Collection<TarifVariabelBO> tarifVariabelBOs, int kundeId) throws SQLException{
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		Iterator<TarifVariabelBO> tarifVariables = tarifVariabelBOs.iterator();
		TarifVariabelBO tarifVariabelBO = tarifVariables.next();
		TarifVariabelBO tarfiDayNight = tarifVariables.next();
		

		double variiertPreisSumme = 0.0;
		double dayNightSumme = 0;
		double fixPreisSume = 0.0;
		double wert = 0.0;

		resultSet = statement.executeQuery("Select * from Simulation where Kunde_id = "	+ kundeId);
		while (resultSet.next()) {
			wert = resultSet.getDouble("Wert");
			variiertPreisSumme += wert *	tarifVariabelBO.getFakePreis(resultSet.getTime("Zeit"));
			dayNightSumme += wert * 	tarfiDayNight.getFakePreisDayNight(resultSet.getTime("Zeit"));
			fixPreisSume += wert * 0.395;
		}
		
		
		return ";" + fixPreisSume + ";" + variiertPreisSumme + ";" + dayNightSumme;
	}
	
	
	public List<SimulationBO> getAllSimulationFurKunde(int kundeID) throws SQLException{
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		List<SimulationBO> simulationBOs = new ArrayList<SimulationBO>();
		SimulationBO simBo = new SimulationBO();

		resultSet = statement.executeQuery("Select * from Simulation where Kunde_id = "	+ kundeID);
		while (resultSet.next()) {
			simBo = new SimulationBO();
			simBo.setDate(resultSet.getDate("Datum"));
			simBo.setZeit(resultSet.getTime("Zeit"));
			simBo.setWert(resultSet.getDouble("Wert"));
			simulationBOs.add(simBo);
		}
		
		return simulationBOs;
		
	}
	
	/**
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Map<String, Integer> populateMap(ResultSet resultSet)
			throws SQLException {
		Map<String, Integer> map = new HashMap<String, Integer>();

		while (resultSet.next()) {
			String name = resultSet.getString("Name");

			// System.out.println("Name: " + name.toUpperCase());
			map.put(name.toUpperCase(), resultSet.getInt("id"));
		}

		return map;
	}

	/**
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private Map<Integer, String> populateMapIntString(ResultSet resultSet)
			throws SQLException {
		Map<Integer, String> map = new HashMap<Integer, String>();

		while (resultSet.next()) {
			String name = resultSet.getString("Name");
			map.put(resultSet.getInt("id"), name.toUpperCase());
		}

		return map;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Integer> getbundesLandMap() {
		Statement statement;
		ResultSet resultSet = null;
		Map<String, Integer> map = null;
		try {
			statement = this.getConnection().createStatement();
			resultSet = statement.executeQuery("Select * from Bundesland");
			map = populateMap(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;

	}

	/**
	 * 
	 * @return
	 */
	public Map<Integer, String> getbundesLandMapIntString() {
		Statement statement;
		ResultSet resultSet = null;
		Map<Integer, String> map = null;
		try {
			statement = this.getConnection().createStatement();
			resultSet = statement.executeQuery("Select * from Bundesland");
			map = populateMapIntString(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;

	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Integer> getGeschaeftMap() {
		Statement statement;
		ResultSet resultSet = null;
		Map<String, Integer> map = null;
		try {
			statement = this.getConnection().createStatement();
			resultSet = statement.executeQuery("Select * from Geschaeft");
			map = populateMap(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;

	}

	/**
	 * 
	 * @return
	 */
	public Map<Integer, String> getGeschaeftMapIntString() {
		Statement statement;
		ResultSet resultSet = null;
		Map<Integer, String> map = null;
		try {
			statement = this.getConnection().createStatement();
			resultSet = statement.executeQuery("Select * from Geschaeft");
			map = populateMapIntString(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return map;

	}

}
