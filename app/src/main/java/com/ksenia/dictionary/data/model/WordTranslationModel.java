package com.ksenia.dictionary.data.model;

/**
 * Created by Ksenia on 28.05.2017.
 */
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

@StorIOSQLiteType(table = DictionaryContract.DictionaryEntry.TABLE_NAME)
public class WordTranslationModel {

	public String getWord() {
		return mWord;
	}

	@StorIOSQLiteColumn(name = DictionaryContract.DictionaryEntry.COLUMN_WORD)
	String mWord;

	public String getTranslation() {
		return mTranslation;
	}

	@StorIOSQLiteColumn(name = DictionaryContract.DictionaryEntry.COLUMN_TRANSLATION)
	String mTranslation;

	public WordTranslationModel() {

	}

	@NonNull
	public static WordTranslationModel newWordTranslationModel(String word, String translation) {
		WordTranslationModel wordTranslationModel = new WordTranslationModel();
		wordTranslationModel.mWord= word;
		wordTranslationModel.mTranslation = translation;
		return wordTranslationModel;
	}
	/*public WordTranslationModel(String word, String translation) {
		mWord = word;
		mTranslation = translation;
	}*/

}
