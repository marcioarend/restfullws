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

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mincom.impl.PreisImp;
import com.mincom.impl.SeasonImp;
import com.mincom.impl.TarifImp;
import com.mincom.inter.Preis;
import com.mincom.inter.Season;
import com.mincom.inter.Tarif;
import com.mincom.inter.TarifVariabelInter;
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
//			tarifBO.setPreis(preisBO);

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
	
	
	/**
	 * 
	 * @param datumAnfang
	 * @param datumEnd
	 * @param idBundesland
	 * @param idGeschaeft
	 * @return
	 * @throws SQLException
	 */
	
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
	
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public JsonArrayBuilder getGesamtumsatz() throws SQLException{
		JsonArrayBuilder list = Json.createArrayBuilder();
		JsonObjectBuilder wert = Json.createObjectBuilder();
		Statement statement;
		ResultSet rs = null;
		statement = this.getConnection().createStatement();

		rs = statement.executeQuery("SELECT b.Tarif_id as id,sum(gesamt) as gesamt, sum(kw) as kw, t.Name as Name "+ 
									" FROM betrag as b, Tarif as t where b.Tarif_id = t.id group by b.Tarif_id");
		while (rs.next()) {
			wert = Json.createObjectBuilder();
			
			wert.add("id", rs.getInt("id"));
			wert.add("kw", rs.getDouble("kw"));
			wert.add("gesamt", rs.getDouble("gesamt"));
			wert.add("name", rs.getString("Name"));
			
			list.add(wert);

		}

		return list;
		
	}
	public JsonArrayBuilder geteinSparrung() throws SQLException{
		JsonArrayBuilder list = Json.createArrayBuilder();
		JsonArrayBuilder listWerten = Json.createArrayBuilder();
		JsonObjectBuilder kunde = Json.createObjectBuilder();
		JsonObjectBuilder wert = Json.createObjectBuilder();
		Statement statement;
		ResultSet rs = null;
		statement = this.getConnection().createStatement();
		int idOld = 0;
		int idNew = 0;
		
		rs = statement.executeQuery("select * FROM whatif.betrag order by Kunde_id, Tarif_id");
		
		while (rs.next()) {
			idNew = rs.getInt("Kunde_id");
			if (idNew != idOld ){
				if ( idOld == 0){
					kunde = Json.createObjectBuilder();
					kunde.add("id",idNew);
				} else {
					kunde.add("Tarif",listWerten);
					list.add(kunde);
					kunde = Json.createObjectBuilder();
					kunde.add("id",idNew);
					
				}
				listWerten = Json.createArrayBuilder();
				
				
				
				idOld = idNew;
				
			}
			wert = Json.createObjectBuilder();
			wert.add("id",rs.getInt("Tarif_id"));
			wert.add("kw",rs.getDouble("kw"));
			wert.add("gesamt",rs.getDouble("gesamt"));
			listWerten.add(wert);
			
		}
		kunde.add("Tarif",listWerten);
		list.add(kunde);
		
		return list;
	}
	
	
	
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * 
	 * @param idBundesland
	 * @param idGeschaft
	 * @param datumAnfang
	 * @param datumEnd
	 * @return
	 * @throws SQLException
	 */
	
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

	/**
	 * 
	 * @param tarifBOs
	 * @param kundId
	 * @return
	 * @throws SQLException
	 */
	
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

	/**
	 * 
	 * @param tarifVariabelBOs
	 * @param kundeId
	 * @return
	 * @throws SQLException
	 */
	
	public String applyVaribelTarif(Collection<TarifVariabelBO> tarifVariabelBOs, int kundeId) throws SQLException{
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		Iterator<TarifVariabelBO> tarifVariables = tarifVariabelBOs.iterator();
		TarifVariabelInter tarifVariabelBO = tarifVariables.next();
		TarifVariabelInter tarfiDayNight = tarifVariables.next();
		

		double variiertPreisSumme = 0.0;
		double dayNightSumme = 0;
		double fixPreisSume = 0.0;
		double wert = 0.0;

		resultSet = statement.executeQuery("Select * from Simulation where Kunde_id = "	+ kundeId);
		while (resultSet.next()) {
			wert = resultSet.getDouble("Wert");
//			variiertPreisSumme += wert *	tarifVariabelBO.getFakePreis(resultSet.getTime("Zeit"));
//			dayNightSumme += wert * 	tarfiDayNight.getFakePreisDayNight(resultSet.getTime("Zeit"));
			fixPreisSume += wert * 0.395;
		}
		
		
		return ";" + fixPreisSume + ";" + variiertPreisSumme + ";" + dayNightSumme;
	}
	
	/**
	 * 
	 * @param kundeID
	 * @param datumAnfang
	 * @param datumEnd
	 * @return
	 * @throws SQLException
	 */
	public List<SimulationBO> getAllSimulationFurKunde(int kundeID,String datumAnfang, String datumEnd) throws SQLException{
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		List<SimulationBO> simulationBOs = new ArrayList<SimulationBO>();
		SimulationBO simBo = new SimulationBO();

		resultSet = statement.executeQuery("Select * from Simulation where Kunde_id = "	+ kundeID 
				
				+ " and Datum between Date('"
				+ datumAnfang
				+ "') and Date('"
				+ datumEnd
				+ "') "
				);
				
				
				
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
	 * @param kundeId
	 * @param tarifId
	 * @param gesamt
	 * @param kw
	 * @throws SQLException
	 */
	public void createBetragKunde(int kundeId,int tarifId, double gesamt, double kw) throws SQLException{
		
		String value = "INSERT INTO betrag  (Kunde_id,Tarif_id,kw,gesamt) VALUES "
				+ "( ?, ?, ?, ?)";
		PreparedStatement preparedStatement = this.getConnection().prepareStatement(value);
	
		preparedStatement.setInt(1, kundeId);
		preparedStatement.setInt(2, tarifId);
		preparedStatement.setDouble(3, kw);
		preparedStatement.setDouble(4, gesamt);
		preparedStatement.executeUpdate();

		
		
	}
	
	public Collection<Integer[]> getAllKundeTarif() throws SQLException{
		Statement statement;
		ResultSet rs = null;
		statement = this.getConnection().createStatement();
		Collection<Integer[]> elements = new ArrayList<Integer[]>();
		
		
		rs = statement.executeQuery("select * from Kunde_has_Tarif group by Kunde_id" );
		
		Integer[] values = new Integer[2];
		while (rs.next()){
				values = new Integer[2];
				values[0]=rs.getInt("Kunde_id");
				values[1]=rs.getInt("Tarif_id");
				elements.add( values);
		}
		
		
		
		
		
		
		return elements;
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Tarif> getAllTarifsFromDB(int id) throws SQLException{
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		int tIdOld = 0,tIdNeue = 0,pIdOld = 0,pIdNeue = 0;
		Preis pWochentagHigh = null;
		Preis pSamstagHigh = null;
		Preis pSonntagHigh = null;
		
		Preis pWochentagLow = null;
		Preis pSamstagLow = null;
		Preis pSonntagLow = null;
		
		Season sHigh = null;
		Season sLow = null;
		Tarif t = null;
		List<Tarif> tList = new ArrayList<Tarif>();
		
		
		
		if ( id == 0) {
			resultSet = statement.executeQuery("select t.id as tId, name, DayNight,DreiPeriod,Jahrzeit, " +
					   " p.id as pId, Preis,HighDemand,Anfang, End,Wochentag,Sanstag,Sonntag "+
					   " from Preis as p, Preis_activen as pa, Tarif as t, Tarif_has_Preis as tp "+ 
					   "	where " +
					   " tp.Preis_id=p.id and pa.Preis_id=p.id and t.id = tp.Tarif_id and pa.Tarif_id "+
					   " order by t.id ASC, p.id ASC" );
		} else {
			resultSet = statement.executeQuery("select t.id as tId, name, DayNight,DreiPeriod,Jahrzeit, " +
					   " p.id as pId, Preis,HighDemand,Anfang, End,Wochentag,Sanstag,Sonntag "+
					   " from Preis as p, Preis_activen as pa, Tarif as t, Tarif_has_Preis as tp "+ 
					   "	where " +
					   " t.id = " + id + 
					   " and tp.Preis_id=p.id and pa.Preis_id=p.id and t.id = tp.Tarif_id and pa.Tarif_id "+
					   " order by t.id ASC, p.id ASC" );
		}
		
		
		while (resultSet.next()){
			tIdNeue = resultSet.getInt("tId");
			if ( tIdNeue != tIdOld){
				t= new TarifImp();
				t.setName(resultSet.getString("name"));
				sHigh = new SeasonImp();
				sHigh.setName(Season.HIGH_DEMAND);
				sHigh.setType(Season.HIGH);
				sLow = new SeasonImp();
				sLow.setName(Season.LOW_DEMAND);
				sLow.setType(Season.LOW);
				t.setSeason(sLow);
				t.setSeason(sHigh);
				tList.add(t);
				tIdOld = tIdNeue;
			}
			
			pIdNeue = resultSet.getInt("pId");
			if (pIdNeue == pIdOld){
				if ( resultSet.getInt("HighDemand") == 1){
					populatePreis(resultSet,  pWochentagHigh, pSamstagHigh, pSonntagHigh);
				}else {
					populatePreis(resultSet,  pWochentagLow, pSamstagLow, pSonntagLow);
				}
				
		
			} else {
				pWochentagHigh = new PreisImp();
				pSamstagHigh = new PreisImp();
				pSonntagHigh = new PreisImp();
				pWochentagLow = new PreisImp();
				pSamstagLow = new PreisImp();
				pSonntagLow = new PreisImp();
				
				pIdOld = pIdNeue;
				
				if ( resultSet.getInt("HighDemand") == 1){
					populatePreis(resultSet,  pWochentagHigh, pSamstagHigh, pSonntagHigh);
					sHigh.setPreis(pWochentagHigh);
					sHigh.setPreisSamstag(pSamstagHigh);
					sHigh.setPreisSonntag(pSonntagHigh);
				}else {
					populatePreis(resultSet,  pWochentagLow, pSamstagLow, pSonntagLow);
					sLow.setPreis(pWochentagLow);
					sLow.setPreisSamstag(pSamstagLow);
					sLow.setPreisSonntag(pSonntagLow);
				}
				
				
			}
			
		}
		
		
		return tList;
	}

	public List<Tarif> getAllTarifsFromJson(int id) throws SQLException{
		Statement statement;
		ResultSet resultSet = null;
		statement = this.getConnection().createStatement();
		int tIdOld = 0,tIdNeue = 0,pIdOld = 0,pIdNeue = 0;
		
		JsonArrayBuilder TarifList = Json.createArrayBuilder();
		JsonObjectBuilder tarifJson = Json.createObjectBuilder();
		JsonObjectBuilder seasonHighJson = Json.createObjectBuilder();
		JsonObjectBuilder seasonLowJson = Json.createObjectBuilder();
		JsonObjectBuilder preisWocheHigh = Json.createObjectBuilder();
		
		
		Preis pWochentagHigh = null;
		Preis pSamstagHigh = null;
		Preis pSonntagHigh = null;
		
		Preis pWochentagLow = null;
		Preis pSamstagLow = null;
		Preis pSonntagLow = null;
		
		Season sHigh = null;
		Season sLow = null;
		Tarif t = null;
		List<Tarif> tList = new ArrayList<Tarif>();
		
		
		if ( id == 0) {
			resultSet = statement.executeQuery("select t.id as tId, name, DayNight,DreiPeriod,Jahrzeit, " +
					   " p.id as pId, Preis,HighDemand,Anfang, End,Wochentag,Sanstag,Sonntag "+
					   " from Preis as p, Preis_activen as pa, Tarif as t, Tarif_has_Preis as tp "+ 
					   "	where " +
					   " tp.Preis_id=p.id and pa.Preis_id=p.id and t.id = tp.Tarif_id and pa.Tarif_id "+
					   " order by t.id ASC, p.id ASC" );
		} else {
			resultSet = statement.executeQuery("select t.id as tId, name, DayNight,DreiPeriod,Jahrzeit, " +
					   " p.id as pId, Preis,HighDemand,Anfang, End,Wochentag,Sanstag,Sonntag "+
					   " from Preis as p, Preis_activen as pa, Tarif as t, Tarif_has_Preis as tp "+ 
					   "	where " +
					   " t.id = " + id + 
					   " and tp.Preis_id=p.id and pa.Preis_id=p.id and t.id = tp.Tarif_id and pa.Tarif_id "+
					   " order by t.id ASC, p.id ASC" );
		}
		
		
		
		while (resultSet.next()){
			tIdNeue = resultSet.getInt("tId");
			if ( tIdNeue != tIdOld){
					
				
				t= new TarifImp();
				tarifJson = Json.createObjectBuilder();
				tarifJson.add("name",resultSet.getString("name"));
				t.setName(resultSet.getString("name"));
				
				sHigh = new SeasonImp();
				seasonHighJson = Json.createObjectBuilder();
				seasonHighJson.add("name",Season.HIGH_DEMAND);
				seasonHighJson.add("type",Season.HIGH);
				sHigh.setName(Season.HIGH_DEMAND);
				sHigh.setType(Season.HIGH);
				seasonLowJson = Json.createObjectBuilder();
				seasonLowJson.add("name",Season.LOW_DEMAND);
				seasonLowJson.add("type",Season.LOW);
				sLow = new SeasonImp();
				sLow.setName(Season.LOW_DEMAND);
				sLow.setType(Season.LOW);
				tarifJson.add("high",seasonHighJson);
				tarifJson.add("low",seasonLowJson);
				t.setSeason(sLow);
				t.setSeason(sHigh);
				TarifList.add(tarifJson);
				tList.add(t);
				tIdOld = tIdNeue;
			}
			
			pIdNeue = resultSet.getInt("pId");
			if (pIdNeue == pIdOld){
				if ( resultSet.getInt("HighDemand") == 1){
					populatePreis(resultSet,  pWochentagHigh, pSamstagHigh, pSonntagHigh);
				}else {
					populatePreis(resultSet,  pWochentagLow, pSamstagLow, pSonntagLow);
				}
				
		
			} else {
				pWochentagHigh = new PreisImp();
				
				pSamstagHigh = new PreisImp();
				pSonntagHigh = new PreisImp();
				pWochentagLow = new PreisImp();
				pSamstagLow = new PreisImp();
				pSonntagLow = new PreisImp();
				
				pIdOld = pIdNeue;
				
				if ( resultSet.getInt("HighDemand") == 1){
					populatePreis(resultSet,  pWochentagHigh, pSamstagHigh, pSonntagHigh);
					seasonHighJson.add("wochentag",pWochentagHigh.getUhrJson());
					sHigh.setPreis(pWochentagHigh);
					sHigh.setPreisSamstag(pSamstagHigh);
					sHigh.setPreisSonntag(pSonntagHigh);
				}else {
					populatePreis(resultSet,  pWochentagLow, pSamstagLow, pSonntagLow);
					sLow.setPreis(pWochentagLow);
					sLow.setPreisSamstag(pSamstagLow);
					sLow.setPreisSonntag(pSonntagLow);
				}
				
				
			}
			
		}
		
		System.out.println(TarifList.build().toString());
		return tList;
	}
	
	
	
	
	
	
	

	
	
	
	
	
	private void populatePreis(ResultSet resultSet, Preis pWochentag,Preis pSanstag,Preis pSonntag)
			throws SQLException {
		if ( resultSet.getInt("Wochentag") == 1){
			pWochentag.setName("Id " + resultSet.getInt("pId"));
			pWochentag.setPreis(resultSet.getDouble("Preis"));
			pWochentag.setGueltigkeit(resultSet.getTime("Anfang"), resultSet.getTime("End"));
		} else if (resultSet.getInt("Sanstag") == 1){
			pSanstag.setName("Id " + resultSet.getInt("pId"));
			pSanstag.setPreis(resultSet.getDouble("Preis"));
			pSanstag.setGueltigkeit(resultSet.getTime("Anfang"), resultSet.getTime("End"));
		} else {
			pSonntag.setName("Id " + resultSet.getInt("pId"));
			pSonntag.setPreis(resultSet.getDouble("Preis"));
			pSonntag.setGueltigkeit(resultSet.getTime("Anfang"), resultSet.getTime("End"));
		}
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
