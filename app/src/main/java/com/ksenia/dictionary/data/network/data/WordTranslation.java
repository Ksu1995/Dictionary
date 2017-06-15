package com.ksenia.dictionary.data.network.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Samsonova_K on 31.05.2017.
 */

public class WordTranslation extends TranslationResponse {
	@JsonProperty("lang")
	private String mLanguage;
	@JsonProperty("text")
	private String[] mTranslation;

	public WordTranslation(String lang, String[] translations, int returnCode) {
		super(returnCode);
		mLanguage = lang;
		mTranslation = translations;
	}

	WordTranslation() {
	}

	public String getLanguage() {
		return mLanguage;
	}

	public String[] getTranslation() {
		return mTranslation;
	}

}
