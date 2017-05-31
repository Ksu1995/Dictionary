package com.ksenia.dictionary.presentation.dictionary.view;

import com.ksenia.dictionary.data.model.WordTranslationModel;

/**
 * Created by Ksenia on 29.05.2017.
 */

public interface IDictionaryView {

	void addNewWord(String word);

	void setNewWord(WordTranslationModel word);

}
