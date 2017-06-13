package com.ksenia.dictionary.data.model;

/**
 * Created by Ksenia on 28.05.2017.
 */

import android.support.annotation.NonNull;

import com.google.common.base.Objects;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = DictionaryEntry.TABLE_NAME)
public class WordTranslationModel {

	@StorIOSQLiteColumn(name = DictionaryEntry.COLUMN_WORD, key = true)
	String mWord;

	@StorIOSQLiteColumn(name = DictionaryEntry.COLUMN_TRANSLATION)
	String mTranslation;

	@StorIOSQLiteColumn(name = DictionaryEntry.COLUMN_LANGUAGE, key = true)
	String mLang;

	@StorIOSQLiteColumn(name = DictionaryEntry.COLUMN_FAVOURITE)
	boolean mFavourite;

	WordTranslationModel() {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		WordTranslationModel that = (WordTranslationModel) o;
		return mFavourite == that.mFavourite &&
				Objects.equal(mWord, that.mWord) &&
				Objects.equal(mTranslation, that.mTranslation) &&
				Objects.equal(mLang, that.mLang);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(mWord, mTranslation, mLang, mFavourite);
	}
}
