package com.ksenia.dictionary.data.model;

/**
 * Created by Samsonova_K on 14.06.2017.
 */

public class WordTranslationWithResult {

	public WordTranslationModel getWordTranslationModel() {
		return mWordTranslationModel;
	}

	private WordTranslationModel mWordTranslationModel;

	public boolean isInserted() {
		return mIsInserted;
	}

	private boolean mIsInserted;

	public WordTranslationWithResult (WordTranslationModel wordTranslationModel, boolean result) {
		mWordTranslationModel = wordTranslationModel;
		mIsInserted = result;
	}
}