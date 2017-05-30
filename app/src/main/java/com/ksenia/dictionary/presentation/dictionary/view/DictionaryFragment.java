package com.ksenia.dictionary.presentation.dictionary.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksenia.dictionary.MyApplication;
import com.ksenia.dictionary.R;
import com.ksenia.dictionary.di.dictionary.DictionaryModule;
import com.ksenia.dictionary.MyApplication;
import com.ksenia.dictionary.presentation.dictionary.presenter.IDictionaryPresenter;

import javax.inject.Inject;

/**
 * Created by Ksenia on 29.05.2017.
 */

public class DictionaryFragment extends Fragment implements IDictionaryView {

    @Inject
    IDictionaryPresenter mDictionaryPresenter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		MyApplication.get(getContext()).applicationComponent().plus(new DictionaryModule()).inject(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.content_main, container, false);

		mDictionaryPresenter.bindView(this);
		return view;
	}

	@Override
	public void onDestroyView() {
		mDictionaryPresenter.unbindView();
		super.onDestroyView();
	}

    @Override
    public void addNewWord(String word) {
        mDictionaryPresenter.translateNewWord();
    }
}
