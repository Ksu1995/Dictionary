package com.ksenia.dictionary.business.dictionary;


import android.support.annotation.NonNull;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.ksenia.dictionary.data.repository.dictionary.IDictionaryRepository;

import java.util.List;

import rx.Observable;
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
	public Single<WordTranslation> getWordTranslation(@NonNull String word, String langTo) {
		/*if (word.isEmpty()) {
			throw new DictionaryInteractorException("Empty word to translate");
		}*/
		return mDictionaryRepository.getWordTranslation(word, langTo);//.onErrorResumeNext(throwable -> Observable.error(
				//new DictionaryInteractorException("Incorrect personal info"))).toSingle();//.doOnError(on->throw new DictionaryInteractorException());
	}

	@Override
	public boolean saveWordTranslation(WordTranslation wordTranslation) {
		return mDictionaryRepository.saveWordTranslation(wordTranslation);
	}

	@Override
	public Observable<List<WordTranslationModel>> getDictionary() {
		return mDictionaryRepository.getDictionary();
	}

}
