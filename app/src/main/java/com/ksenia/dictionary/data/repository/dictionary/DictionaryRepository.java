package com.ksenia.dictionary.data.repository.dictionary;

import android.content.Context;

import com.ksenia.dictionary.data.model.DictionaryContract;
import com.ksenia.dictionary.data.model.DictionaryDbHelper;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.model.WordTranslationModelStorIOSQLiteDeleteResolver;
import com.ksenia.dictionary.data.model.WordTranslationModelStorIOSQLiteGetResolver;
import com.ksenia.dictionary.data.model.WordTranslationModelStorIOSQLitePutResolver;
import com.ksenia.dictionary.data.network.data.WordTranslation;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Single;

/**
 * Created by Samsonova_K on 30.05.2017.
 */

public class DictionaryRepository implements IDictionaryRepository {

	private Retrofit mRetrofit;
	private StorIOSQLite mStorIOSQLite;

	public DictionaryRepository(Context context) {
		mRetrofit = new Retrofit.Builder()
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl("https://translate.yandex.net")
				.build();
		DictionaryDbHelper dbHelper = new DictionaryDbHelper(context);
		mStorIOSQLite = DefaultStorIOSQLite.builder()
				.sqliteOpenHelper(dbHelper).addTypeMapping(WordTranslationModel.class, SQLiteTypeMapping.<WordTranslationModel>builder()
						.putResolver(new WordTranslationModelStorIOSQLitePutResolver())
						.getResolver(new WordTranslationModelStorIOSQLiteGetResolver())
						.deleteResolver(new WordTranslationModelStorIOSQLiteDeleteResolver())
						.build())
				.build();
	}

	@Override
	public Single<WordTranslation> getWordTranslation(String word, String langTo) {
		IIDictionaryRepository weatherService = mRetrofit.create(IIDictionaryRepository.class);
		return weatherService.translateWord("trnsl.1.1.20170530T132550Z.856fb0e22726f7be.6b55b7908badee4e4b7ebd4dd5ca03c824a438d2", word, langTo);
	}

	@Override
	public boolean saveWordTranslation(WordTranslation wordTranslation) {
		return mStorIOSQLite.put()
				.object(WordTranslationModel.newWordTranslationModel(wordTranslation.getWord(), wordTranslation.getTranslation()[0], wordTranslation.getLanguage()))
				.prepare()
				.executeAsBlocking().wasInserted();
	}

	@Override
	public Observable<List<WordTranslationModel>> getDictionary() {
		return mStorIOSQLite.get()
				.listOfObjects(WordTranslationModel.class)
				.withQuery(Query.builder()
						.table(DictionaryContract.DictionaryEntry.TABLE_NAME)
						//.where(DictionaryContract.DictionaryEntry.COLUMN_WORD + " = ?")
						//.whereArgs("and")
						.build())
				.prepare()
				.asRxObservable();
	}
}
