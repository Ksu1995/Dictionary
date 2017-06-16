package com.ksenia.dictionary.data.repository.dictionary;

import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.Language;
import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.List;

import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public interface IDictionaryRepository {

	Single<WordTranslation> getWordTranslation(String word, Language langTo, Language langFrom);

	Single<PutResult> saveWordTranslation(WordTranslationModel wordTranslation);

	Single<List<WordTranslationModel>> getDictionary();
}
