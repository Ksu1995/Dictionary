package com.ksenia.dictionary.data.repository.dictionary;

import com.ksenia.dictionary.data.model.WordTranslationModel;

import rx.Single;

/**
 * Created by Samsonova_K on 30.05.2017.
 */

public class DictionaryRepository implements IDictionaryRepository {
	@Override
	public Single<WordTranslationModel> getWordTranslation() {
		return null;
	}
}
