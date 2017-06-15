package com.ksenia.dictionary.business.dictionary;


import android.support.annotation.NonNull;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.model.WordTranslationWithResult;
import com.ksenia.dictionary.data.network.data.Language;
import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.ksenia.dictionary.data.repository.dictionary.IDictionaryRepository;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import rx.Observable;
import rx.Single;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Ksenia on 28.05.2017.
 */

public class DictionaryInteractor implements IDictionaryInteractor {

	private IDictionaryRepository mDictionaryRepository;

	public DictionaryInteractor(IDictionaryRepository dictionaryRepository) {
		mDictionaryRepository = dictionaryRepository;
	}

	@Override
	public Single<WordTranslationWithResult> getWordTranslation(@NonNull String word, Language langTo) {
		return mDictionaryRepository.getWordTranslation(word, langTo).map(wordTranslation -> {
			if (wordTranslation.getResponseCode() != 200) {
				throw new DictionaryInteractorException("Response code = " + wordTranslation.getResponseCode());
			} else {
				return wordTranslation;
			}
		}).flatMap(new Func1<WordTranslation, Single<WordTranslationWithResult>>() {
			@Override
			public Single<WordTranslationWithResult> call(WordTranslation wordTranslation) {
				WordTranslationModel wordTranslationModel =
						WordTranslationModel.newWordTranslationModel(word, wordTranslation.getTranslation()[0],
						langTo);

				return Single.zip(Single.fromCallable(() -> wordTranslationModel),
						mDictionaryRepository.saveWordTranslation(wordTranslationModel), new Func2<WordTranslationModel, PutResult, WordTranslationWithResult>() {
							@Override
							public WordTranslationWithResult call(WordTranslationModel wordTranslationModel, PutResult putResult) {
								return new WordTranslationWithResult(wordTranslationModel, putResult.wasInserted());
							}
						});
			}
		}).toObservable().onErrorResumeNext(throwable -> Observable.error(new DictionaryInteractorException("Empty Word"))).toSingle();
	}

	@Override
	public Single<List<WordTranslationModel>> getDictionary() {
		return mDictionaryRepository.getDictionary();
	}
}
