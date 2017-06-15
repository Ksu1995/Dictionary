package com.ksenia.dictionary.data.model;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

/**
 * Created by Samsonova_K on 15.06.2017.
 */

public class WordTranslationModelStorIOSQLitePutResolver extends DefaultPutResolver<WordTranslationModel> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonNull
	public InsertQuery mapToInsertQuery(@NonNull WordTranslationModel object) {
		return InsertQuery.builder()
				.table("dictionary")
				.build();}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonNull
	public UpdateQuery mapToUpdateQuery(@NonNull WordTranslationModel object) {
		return UpdateQuery.builder()
				.table("dictionary")
				.where("word = ? AND lang = ?")
				.whereArgs(object.mWord, object.mLang.getName())
				.build();}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonNull
	public ContentValues mapToContentValues(@NonNull WordTranslationModel object) {
		ContentValues contentValues = new ContentValues(4);

		contentValues.put("word", object.mWord);
		contentValues.put("translation", object.mTranslation);
		contentValues.put("lang", object.mLang.getName());
		contentValues.put("favourite", object.mFavourite);

		return contentValues;
	}
}
