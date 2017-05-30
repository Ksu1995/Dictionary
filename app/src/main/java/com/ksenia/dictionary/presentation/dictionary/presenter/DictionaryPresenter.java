package com.ksenia.dictionary.presentation.dictionary.presenter;

import com.ksenia.dictionary.business.dictionary.IDictionaryInteractor;
import com.ksenia.dictionary.presentation.dictionary.view.IDictionaryView;

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
	public void translateNewWord() {

	}
}
