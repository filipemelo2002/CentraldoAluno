package com.feathercompany.www.InternalDB;

public abstract class DBHandler
{
	public abstract void createDB();
	public abstract void disconnectDB();
	public abstract void saveData(int position, String data);
}
