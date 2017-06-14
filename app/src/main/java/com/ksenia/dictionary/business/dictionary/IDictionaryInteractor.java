package com.ksenia.dictionary.business.dictionary;

import android.support.annotation.NonNull;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.model.WordTranslationWithResult;

import java.util.List;

import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public interface IDictionaryInteractor {

	Single<WordTranslationWithResult> getWordTranslation(@NonNull String word, String langTo);

	Single<List<WordTranslationModel>> getDictionary();

}
