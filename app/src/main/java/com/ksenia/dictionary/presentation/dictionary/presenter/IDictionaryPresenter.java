package com.ksenia.dictionary.presentation.dictionary.presenter;

import com.ksenia.dictionary.presentation.dictionary.view.IDictionaryView;

/**
 * Created by Ksenia on 29.05.2017.
 */

public interface IDictionaryPresenter {

    void bindView(IDictionaryView dictionaryView);
    void unbindView();

    void clickAddNewWord();
}
