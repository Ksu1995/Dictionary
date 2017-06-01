package com.ksenia.dictionary.data.repository.dictionary;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.WordTranslation;

import java.util.List;

import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public interface IDictionaryRepository {

	Single<WordTranslation> getWordTranslation(String word);

	void saveWordTranslation(WordTranslation wordTranslation);

	List<WordTranslationModel> getDictionary();
}
