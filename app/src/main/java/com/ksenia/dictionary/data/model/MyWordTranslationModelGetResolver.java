package com.ksenia.dictionary.data.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

/**
 * Created by Samsonova_K on 06.06.2017.
 */

public class MyWordTranslationModelGetResolver extends DefaultGetResolver<WordTranslationModel> {

	@NonNull
	@Override
	public WordTranslationModel mapFromCursor(@NonNull Cursor cursor) {
		String word = cursor.getString(
				cursor.getColumnIndexOrThrow(DictionaryContract.DictionaryEntry.COLUMN_WORD));
		String translation = cursor.getString(
				cursor.getColumnIndexOrThrow(DictionaryContract.DictionaryEntry.COLUMN_TRANSLATION));

		return new WordTranslationModel(/*word, translation*/);
	}
}