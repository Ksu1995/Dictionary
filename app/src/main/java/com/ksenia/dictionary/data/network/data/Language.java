package com.ksenia.dictionary.data.network.data;

import android.support.annotation.Nullable;

/**
 * Created by Samsonova_K on 15.06.2017.
 */

public enum Language {
	English("en"), Russian("ru"), German("de");
	Language(String name) {
		mName = name;
	}
	private String mName;

	public String getName() {
		return mName;
	}

	@Nullable
	static public Language getLanguageByName(String name){
		for(Language l: Language.values()) {
			if (l.mName.equals(name)) {
				return l;
			}
		}
		return null;
	}
}
