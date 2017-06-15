package com.ksenia.dictionary.data.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.ksenia.dictionary.data.network.data.Language;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

/**
 * Created by Samsonova_K on 15.06.2017.
 */
public class WordTranslationModelStorIOSQLiteGetResolver extends DefaultGetResolver<WordTranslationModel> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonNull
	public WordTranslationModel mapFromCursor(@NonNull Cursor cursor) {
		WordTranslationModel object = new WordTranslationModel();

		object.mWord = cursor.getString(cursor.getColumnIndex("word"));
		object.mTranslation = cursor.getString(cursor.getColumnIndex("translation"));
		object.mLang = Language.getLanguageByName(cursor.getString(cursor.getColumnIndex("lang")));
		object.mFavourite = cursor.getInt(cursor.getColumnIndex("favourite")) == 1;

		return object;
	}
}