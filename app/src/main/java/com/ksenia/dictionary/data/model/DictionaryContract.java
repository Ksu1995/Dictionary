package com.ksenia.dictionary.data.model;

import android.provider.BaseColumns;

/**
 * Created by Samsonova_K on 31.05.2017.
 */
public final class DictionaryContract {
	static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + DictionaryEntry.TABLE_NAME + " (" +
					DictionaryEntry._ID + " INTEGER PRIMARY KEY," +
					DictionaryEntry.COLUMN_WORD + " TEXT," +
					DictionaryEntry.COLUMN_TRANSLATION + " TEXT," +
					DictionaryEntry.COLUMN_LANGUAGE + " TEXT," +
					DictionaryEntry.COLUMN_FAVOURITE + " INTEGER)";

	static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + DictionaryEntry.TABLE_NAME;

	private DictionaryContract() {}

	public static class DictionaryEntry implements BaseColumns {
		public static final String TABLE_NAME = "dictionary";
		public static final String COLUMN_WORD = "word";
		public static final String COLUMN_TRANSLATION = "translation";
		public static final String COLUMN_LANGUAGE = "lang";
		public static final String COLUMN_FAVOURITE = "favourite";
	}
}

