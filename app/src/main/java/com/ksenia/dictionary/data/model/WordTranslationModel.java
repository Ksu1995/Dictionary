package com.ksenia.dictionary.data.model;

/**
 * Created by Ksenia on 28.05.2017.
 */

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = DictionaryContract.DictionaryEntry.TABLE_NAME)
public class WordTranslationModel {

	@StorIOSQLiteColumn(name = DictionaryContract.DictionaryEntry.COLUMN_WORD, key = true)
	String mWord;

	@StorIOSQLiteColumn(name = DictionaryContract.DictionaryEntry.COLUMN_TRANSLATION)
	String mTranslation;

	@StorIOSQLiteColumn(name = DictionaryContract.DictionaryEntry.COLUMN_LANGUAGE, key =true)
	String mLang;

	@StorIOSQLiteColumn(name = DictionaryContract.DictionaryEntry.COLUMN_FAVOURITE)
	boolean mFavourite;

	public WordTranslationModel() {
	}

	public String getWord() {
		return mWord;
	}

	public String getTranslation() {
		return mTranslation;
	}

	@NonNull
	public static WordTranslationModel newWordTranslationModel(String word, String translation, String language) {
		WordTranslationModel wordTranslationModel = new WordTranslationModel();
		wordTranslationModel.mWord = word;
		wordTranslationModel.mTranslation = translation;
		wordTranslationModel.mLang = language;
		wordTranslationModel.mFavourite = false;
		return wordTranslationModel;
	}

}
