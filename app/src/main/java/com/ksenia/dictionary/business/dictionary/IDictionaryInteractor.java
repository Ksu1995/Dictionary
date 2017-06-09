package com.ksenia.dictionary.business.dictionary;

import android.support.annotation.NonNull;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.WordTranslation;

import java.util.List;

import rx.Observable;
import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public interface IDictionaryInteractor {

	Single<WordTranslation> getWordTranslation(@NonNull String word, String langTo);

	boolean saveWordTranslation(WordTranslation wordTranslation);

	Observable<List<WordTranslationModel>> getDictionary();

}
