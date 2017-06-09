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
	public void clickAddNewWord(String word, String langTo) {
		mDictionaryInteractor.getWordTranslation(word, langTo).subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(wordData -> {
					wordData.setWord(word);
					if (mDictionaryInteractor.saveWordTranslation(wordData)) {
						mDictionaryView.setNewWord(WordTranslationModel.newWordTranslationModel(word, wordData.getTranslation()[0], wordData.getLanguage()));
						Log.e("Current word", wordData.getTranslation()[0]);
					}
				}, this::handleErrorTranslateWord);
	}
	private void handleErrorTranslateWord(Throwable throwable) {
		mDictionaryView.showError();
	}


	@Override
	public void loadDictionary() {
		mDictionaryInteractor.getDictionary().subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dictionary -> {
					mDictionaryView.updateWordList(dictionary);
				});
	}
}
