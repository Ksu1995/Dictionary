package com.ksenia.dictionary.business.dictionary;

import com.ksenia.dictionary.data.model.WordTranslationModel;

import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public interface IDictionaryInteractor {

    Single<WordTranslationModel> getWordTranslation();

}
