package com.feathercompany.www.InternalDB;
import android.database.sqlite.*;
import android.content.*;

public class BDcore extends SQLiteOpenHelper
{
	private static String bdName="CentraldoAluno";
	private static int bdVersion = 16;
	public BDcore(Context context){
		super(context,bdName,null,bdVersion);
	}
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		String sql ="CREATE TABLE IF NOT EXISTS user ( id integer primary key autoincrement, userToken varchar(250) not null unique , email varchar(30) not null unique, senha varChar(20) not null);";
		p1.execSQL(sql);

		sql = "CREATE TABLE IF NOT EXISTS boletins ( id integer primary key autoincrement, label text not null, ano int(4),boletimId int not null unique);";
		p1.execSQL(sql);

		sql = "CREATE TABLE IF NOT EXISTS boletinNotas ( id integer primary key autoincrement, dados text not null);";
		p1.execSQL(sql);

		sql = "CREATE TABLE IF NOT EXISTS faltas ( id integer primary key autoincrement, dados text not null);";
		p1.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS horario ( id integer primary key autoincrement, dados text not null);";
        p1.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		p1.execSQL("drop table if exists user");
		p1.execSQL("drop table if exists boletins");
		p1.execSQL("drop table if exists boletinNotas");
		p1.execSQL("drop table if exists faltas");
        p1.execSQL("drop table if exists horario");
		onCreate(p1);
	}

}
