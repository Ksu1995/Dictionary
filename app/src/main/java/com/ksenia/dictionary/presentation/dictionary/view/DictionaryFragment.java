package com.ksenia.dictionary.presentation.dictionary.view;


import android.support.v4.app.Fragment;

import com.ksenia.dictionary.presentation.dictionary.presenter.IDictionaryPresenter;

import javax.inject.Inject;

/**
 * Created by Ksenia on 29.05.2017.
 */

public class DictionaryFragment extends Fragment implements IDictionaryView {

	@Inject
	IDictionaryPresenter mDictionaryPresenter;


	@Override
	public void addNewWord(String word) {
		mDictionaryPresenter.translateNewWord();
	}
}
