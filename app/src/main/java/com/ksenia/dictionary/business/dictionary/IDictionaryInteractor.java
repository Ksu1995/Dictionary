package com.ksenia.dictionary.business.dictionary;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.WordTranslation;

import java.util.List;

import rx.Observable;
import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public interface IDictionaryInteractor {

	Single<WordTranslation> getWordTranslation(String word);

	boolean saveWordTranslation(WordTranslation wordTranslation);

	Observable<List<WordTranslationModel>> getDictionary();

}
