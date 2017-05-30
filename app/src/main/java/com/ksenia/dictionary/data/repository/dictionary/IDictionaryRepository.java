package com.ksenia.dictionary.data.repository.dictionary;


import com.ksenia.dictionary.data.model.WordTranslationModel;

import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public interface IDictionaryRepository {
    Single<WordTranslationModel> getWordTranslation();
}
