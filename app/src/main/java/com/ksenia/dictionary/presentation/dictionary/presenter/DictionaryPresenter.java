package com.ksenia.dictionary.presentation.dictionary.presenter;

import android.util.Log;

import com.ksenia.dictionary.business.dictionary.IDictionaryInteractor;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.presentation.dictionary.view.IDictionaryView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Samsonova_K on 30.05.2017.
 */

public class DictionaryPresenter implements IDictionaryPresenter {

	private IDictionaryInteractor mDictionaryInteractor;

	private IDictionaryView mDictionaryView;

	public DictionaryPresenter(IDictionaryInteractor dictionaryInteractor) {
		mDictionaryInteractor = dictionaryInteractor;
	}

	@Override
	public void bindView(IDictionaryView dictionaryView) {
		mDictionaryView = dictionaryView;
	}

	@Override
	public void unbindView() {
		mDictionaryView = null;
	}

	@Override
	public void clickAddNewWord(String word) {
		mDictionaryInteractor.getWordTranslation(word).subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(wordData -> {
					mDictionaryView.setNewWord(new WordTranslationModel(word, wordData.getTranslation()[0]));
					mDictionaryInteractor.saveWordTranslation(wordData);
					Log.e("Current word", wordData.getTranslation()[0]);
				});
	}
}
