package com.ksenia.dictionary.data.network.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Samsonova_K on 31.05.2017.
 */

public class TranslationResponse {

	@JsonProperty("code")
	private int mResponseCode;

	TranslationResponse(int returnCode) {
		mResponseCode = returnCode;
	}

	TranslationResponse() {
	}

	public long getResponseCode() {
		return mResponseCode;
	}
}
