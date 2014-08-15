package com.mincom.rest.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;

import com.mincom.inter.TarifVariabelInter;

public class TarifVariabelBO implements TarifVariabelInter {

	private int id;
	private String name;
	private boolean dayNight;
	private boolean dreiPeriod;
	private boolean jahrzeit;
	private HashMap<Time,PreisBO> preis;
	private Time time00 = new Time(00, 00, 00);
	private Time time06 = new Time(6, 00, 00);
	private Time time07 = new Time(7, 00, 00);
	private Time time08 = new Time(8, 00, 00);
	private Time time10 = new Time(10, 00, 00);
	private Time time18 = new Time(18, 00, 00);
	private Time time20 = new Time(20, 00, 00);
	private Time time22 = new Time(22, 00, 00);

	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#getId()
	 */
	@Override
	public int getId() {
		return id;
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#getKuendigungsfrist()
	 */
	@Override
	public String getKuendigungsfrist() {
		return kuendigungsfrist;
	}

	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#setKuendigungsfrist(java.lang.String)
	 */
	@Override
	public void setKuendigungsfrist(String kuendigungsfrist) {
		this.kuendigungsfrist = kuendigungsfrist;
	}

	private String kuendigungsfrist;

		/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#setPreis(com.mincom.rest.bo.PreisBO)
	 */
	@Override
	public void setPreis(PreisBO preis,Time time) {
		if ( this.preis == null) {
			this.preis = new HashMap<Time, PreisBO>();
		}
		this.preis.put(time, preis);
	}
	
	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#getFakePreis(java.sql.Time)
	 */
	@Override
	public double getPreis(Time time){
		
		if ((time.compareTo(time07) >= 0 && time.compareTo(time08) < 0) || 
			(time.compareTo(time10) >= 0 && time.compareTo(time18) < 0) || 
			(time.compareTo(time20) >= 0 && time.compareTo(time22) < 0)){
			PreisBO p = this.preis.get(time07);
			
			return p.getPreis();
		}else if ((time.compareTo(time08) >= 0 && time.compareTo(time10) < 0) || 
				(time.compareTo(time18) >= 0 && time.compareTo(time20) < 0)){
			PreisBO p = this.preis.get(time08);
			return p.getPreis();
		} else {	
			PreisBO p = this.preis.get(time22);
			return p.getPreis();	
		}
			
			
		
		
	}

	
	
	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#populate(java.sql.ResultSet)
	 */
	@Override
	public void populate(ResultSet result){
		try {
			this.setId(result.getInt("id"));
			this.setName(result.getString("name"));
			this.setDayNight(result.getBoolean("dayNight"));
			this.setDreiPeriod(result.getBoolean("dreiPeriod"));
			this.setJahrzeit(result.getBoolean("jahrzeit"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#toString()
	 */
	@Override
	public String toString() {
		StringBuffer values = new StringBuffer();
		values.append( this.getName() );
		
		return values.toString();
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#getName()
	 */
	@Override
	public String getName() {
		return name;
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#isDayNight()
	 */
	@Override
	public boolean isDayNight() {
		return dayNight;
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#setDayNight(boolean)
	 */
	@Override
	public void setDayNight(boolean dayNight) {
		this.dayNight = dayNight;
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#isDreiPeriod()
	 */
	@Override
	public boolean isDreiPeriod() {
		return dreiPeriod;
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#setDreiPeriod(boolean)
	 */
	@Override
	public void setDreiPeriod(boolean dreiPeriod) {
		this.dreiPeriod = dreiPeriod;
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#isJahrzeit()
	 */
	@Override
	public boolean isJahrzeit() {
		return jahrzeit;
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#setJahrzeit(boolean)
	 */
	@Override
	public void setJahrzeit(boolean jahrzeit) {
		this.jahrzeit = jahrzeit;
	}



	/* (non-Javadoc)
	 * @see com.mincom.rest.bo.TarifVariabelInter#setId(int)
	 */
	@Override
	public void setId(int id) {
		this.id = id;
	}

	

}
