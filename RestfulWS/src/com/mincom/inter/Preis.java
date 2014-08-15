package com.mincom.inter;

import java.sql.Time;

public interface Preis {

	public void setGueltigkeit(Time uhrAnfang, Time uhrEnd);

	public boolean isGueltig(Time uhr);

	public void setName(String name);

	public String getName();

	public void setPreis(double preis);

	public double getPreis();

	public void setGebuehr(double gebuehr);

	public double getGebuehr();
}
