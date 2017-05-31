package com.ksenia.dictionary.data.network.data;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Created by Samsonova_K on 31.05.2017.
 */

public class WordTranslation extends TranslationResponse {

	private String mLanguage;
	private String[] mTranslation;
	//private String mWord;

	public String getLanguage() {
		return mLanguage;
	}

	@JsonSetter("lang")
	public void setLanguage(String language) {
		mLanguage = language;
	}

	public String[] getTranslation() {
		return mTranslation;
	}

	@JsonSetter("text")
	public void setTranslation(String[] translation) {
		mTranslation = translation;
	}
}
