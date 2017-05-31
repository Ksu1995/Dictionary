package com.ksenia.dictionary.data.repository.dictionary;

import com.ksenia.dictionary.data.network.data.WordTranslation;
import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public interface IDictionaryRepository {

	Single<WordTranslation> getWordTranslation();
}
