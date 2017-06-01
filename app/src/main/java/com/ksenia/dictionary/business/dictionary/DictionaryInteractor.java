package com.ksenia.dictionary.business.dictionary;


import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.ksenia.dictionary.data.repository.dictionary.IDictionaryRepository;

import java.util.List;

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
	public Single<WordTranslation> getWordTranslation(String word) {
		return mDictionaryRepository.getWordTranslation(word);//.onErrorReturn(throwable -> null/*Log.e("a", throwable.getLocalizedMessage()*/);
	}

	@Override
	public boolean saveWordTranslation(WordTranslation wordTranslation) {
		mDictionaryRepository.saveWordTranslation(wordTranslation);
		return true;
	}

	@Override
	public List<WordTranslationModel> getDictionary() {
		return mDictionaryRepository.getDictionary();
	}

}
