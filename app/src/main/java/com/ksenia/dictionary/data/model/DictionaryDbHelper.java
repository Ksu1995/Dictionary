package com.ksenia.dictionary.data.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Samsonova_K on 31.05.2017.
 */

public class DictionaryDbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Dictionary.db";
	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + DictionaryEntry.TABLE_NAME + " (" +
					DictionaryEntry.COLUMN_WORD + " TEXT," +
					DictionaryEntry.COLUMN_TRANSLATION + " TEXT," +
					DictionaryEntry.COLUMN_LANGUAGE + " TEXT," +
					DictionaryEntry.COLUMN_FAVOURITE +
					" INTEGER, PRIMARY KEY (" + DictionaryEntry.COLUMN_WORD + "," + DictionaryEntry.COLUMN_LANGUAGE + "))";

	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + DictionaryEntry.TABLE_NAME;

	public DictionaryDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}