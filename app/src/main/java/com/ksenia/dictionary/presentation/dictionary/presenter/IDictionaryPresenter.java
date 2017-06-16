package com.ksenia.dictionary.presentation.dictionary.presenter;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.Language;
import com.ksenia.dictionary.presentation.dictionary.view.IDictionaryView;

/**
 * Created by Ksenia on 29.05.2017.
 */

public interface IDictionaryPresenter {

	void bindView(IDictionaryView dictionaryView);

	void unbindView();

	void clickAddNewWord(String word, Language langTo, Language langFrom);

	void loadDictionary();

	void updateFavouriteInDictionaryItem(WordTranslationModel wordTranslationModel);
}
