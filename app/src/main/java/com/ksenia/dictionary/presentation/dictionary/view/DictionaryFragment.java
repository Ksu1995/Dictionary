package com.ksenia.dictionary.presentation.dictionary.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ksenia.dictionary.MyApplication;
import com.ksenia.dictionary.presentation.dictionary.presenter.IDictionaryPresenter;

import javax.inject.Inject;

/**
 * Created by Ksenia on 29.05.2017.
 */

public class DictionaryFragment extends Fragment implements IDictionaryView {

    @Inject
    IDictionaryPresenter mProfilePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        MyApplication.get(getContext()).applicationComponent().plus(new DictionaryModule()).inject(this);
    }

    @Override
    public void addWordToList(String word) {

    }
}
