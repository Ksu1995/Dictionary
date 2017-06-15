package com.ksenia.dictionary.presentation.dictionary.presenter;

import android.util.Log;

import com.ksenia.dictionary.business.dictionary.IDictionaryInteractor;
import com.ksenia.dictionary.data.network.data.Language;
import com.ksenia.dictionary.presentation.dictionary.view.IDictionaryView;
import com.ksenia.dictionary.utils.rx.IRxSchedulers;


/**
 * Created by Samsonova_K on 30.05.2017.
 */

public class DictionaryPresenter implements IDictionaryPresenter {

	private IDictionaryInteractor mDictionaryInteractor;

	private IDictionaryView mDictionaryView;
	private IRxSchedulers mRxSchedulers;

	public DictionaryPresenter(IDictionaryInteractor dictionaryInteractor, IRxSchedulers schedulers) {
		mDictionaryInteractor = dictionaryInteractor;
		mRxSchedulers = schedulers;
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
	public void clickAddNewWord(String word, Language langTo) {
		mDictionaryInteractor.getWordTranslation(word, langTo).subscribeOn(mRxSchedulers.getIOScheduler())
				.observeOn(mRxSchedulers.getMainThreadScheduler())
				.subscribe(wordTranslationWithResult -> {
					if (wordTranslationWithResult.isInserted()) {
						mDictionaryView.addNewWord(wordTranslationWithResult.getWordTranslationModel());
						Log.e("Current word", wordTranslationWithResult.getWordTranslationModel().getTranslation());
					}
				}, this::handleErrorTranslateWord);
	}

	@Override
	public void loadDictionary() {
		mDictionaryInteractor.getDictionary().subscribeOn(mRxSchedulers.getComputationScheduler())
				.observeOn(mRxSchedulers.getMainThreadScheduler())
				.subscribe(dictionary -> {
					mDictionaryView.updateWordList(dictionary);
				});
	}

	private void handleErrorTranslateWord(Throwable throwable) {
		mDictionaryView.showError();
	}
}
