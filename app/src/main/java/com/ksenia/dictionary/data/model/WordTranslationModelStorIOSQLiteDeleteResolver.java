package com.ksenia.dictionary.data.model;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

/**
 * Created by Samsonova_K on 15.06.2017.
 */

public class WordTranslationModelStorIOSQLiteDeleteResolver extends DefaultDeleteResolver<WordTranslationModel> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonNull
	public DeleteQuery mapToDeleteQuery(@NonNull WordTranslationModel object) {
		return DeleteQuery.builder()
				.table("dictionary")
				.where("word = ? AND lang = ?")
				.whereArgs(object.mWord, object.mLang.getName())
				.build();}
}

