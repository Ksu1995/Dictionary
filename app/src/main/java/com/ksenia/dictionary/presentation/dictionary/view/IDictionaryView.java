package com.ksenia.dictionary.presentation.dictionary.view;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.Language;

import java.util.List;

/**
 * Created by Ksenia on 29.05.2017.
 */

public interface IDictionaryView {

	void clickNewWord(String word, Language langTo, Language langFrom);

	void addNewWord(WordTranslationModel word);

	void updateWordList(List<WordTranslationModel> words);

	void showError();

	void addToFavourite(int position);

}
