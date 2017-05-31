package com.ksenia.dictionary.data.network.data;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Created by Samsonova_K on 31.05.2017.
 */

public class TranslationResponse {

	private int mResponseCode;

	public long getResponseCode() {
		return mResponseCode;
	}

	@JsonSetter("code")
	public void setResponseCode(int code) {
		mResponseCode = code;
	}
}
