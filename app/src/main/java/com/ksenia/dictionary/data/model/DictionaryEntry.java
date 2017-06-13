package com.ksenia.dictionary.data.model;

import android.provider.BaseColumns;

/**
 * Created by Samsonova_K on 31.05.2017.
 */

public class DictionaryEntry implements BaseColumns {
	public static final String TABLE_NAME = "dictionary";
	static final String COLUMN_WORD = "word";
	static final String COLUMN_TRANSLATION = "translation";
	static final String COLUMN_LANGUAGE = "lang";
	static final String COLUMN_FAVOURITE = "favourite";
}


