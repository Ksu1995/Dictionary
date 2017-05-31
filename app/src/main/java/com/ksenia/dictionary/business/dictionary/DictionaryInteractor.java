package com.ksenia.dictionary.business.dictionary;


import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.ksenia.dictionary.data.repository.dictionary.IDictionaryRepository;

import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public class DictionaryInteractor implements IDictionaryInteractor {

	private IDictionaryRepository mDictionaryRepository;

	public DictionaryInteractor(IDictionaryRepository dictionaryRepository) {
		mDictionaryRepository = dictionaryRepository;
	}

	@Override
	public Single<WordTranslation> getWordTranslation() {
		return mDictionaryRepository.getWordTranslation();//.onErrorReturn(throwable -> null/*Log.e("a", throwable.getLocalizedMessage()*/);
	}
}
