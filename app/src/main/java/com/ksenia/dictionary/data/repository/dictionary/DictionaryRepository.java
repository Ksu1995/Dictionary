package com.ksenia.dictionary.data.repository.dictionary;

import android.content.Context;

import com.ksenia.dictionary.MyApplication;
import com.ksenia.dictionary.data.model.DictionaryEntry;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.Language;
import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.ksenia.dictionary.di.dictionary.BdModule;
import com.ksenia.dictionary.di.dictionary.DictionaryModule;
import com.ksenia.dictionary.di.dictionary.NetworkModule;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import javax.inject.Inject;

import rx.Single;

/**
 * Created by Samsonova_K on 30.05.2017.
 */

public class DictionaryRepository implements IDictionaryRepository {

	@Inject
	IDictionaryService mDictionaryService;

	@Inject
	StorIOSQLite mStorIOSQLite;

	public DictionaryRepository(Context context) {
		MyApplication.get(context).applicationComponent().plus(new DictionaryModule(), new BdModule(), new NetworkModule()).inject(this);
	}

	@Override
	public Single<WordTranslation> getWordTranslation(String word, Language langTo) {
		return mDictionaryService
				.translateWord("trnsl.1.1.20170530T132550Z.856fb0e22726f7be.6b55b7908badee4e4b7ebd4dd5ca03c824a438d2", word, langTo.getName());
	}

	@Override
	public Single<PutResult> saveWordTranslation(WordTranslationModel wordTranslation) {
		return mStorIOSQLite.put()
				.object(wordTranslation)
                .prepare()
                .asRxSingle();//.subscribe(putResult -> { putResult.wasInserted();});
	}

	@Override
	public Single<List<WordTranslationModel>> getDictionary() {
		return mStorIOSQLite.get()
                .listOfObjects(WordTranslationModel.class)
                .withQuery(Query.builder()
                        .table(DictionaryEntry.TABLE_NAME)
                        //.where(DictionaryContract.DictionaryEntry.COLUMN_WORD + " = ?")
                        //.whereArgs("and")
                        .build())
                .prepare()
                .asRxSingle();
	}
}
