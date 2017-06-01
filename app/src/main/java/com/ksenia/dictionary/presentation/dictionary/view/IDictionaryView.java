package com.ksenia.dictionary.presentation.dictionary.view;

import com.ksenia.dictionary.data.model.WordTranslationModel;

import java.util.List;

/**
 * Created by Ksenia on 29.05.2017.
 */

public interface IDictionaryView {

	void addNewWord(String word);

	void setNewWord(WordTranslationModel word);

	void updateWordList(List<WordTranslationModel> words);

}
