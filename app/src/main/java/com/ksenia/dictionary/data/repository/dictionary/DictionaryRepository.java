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
	private DictionaryDbHelper mDbHelper;
	private Context mContext;
	private StorIOSQLite mStorIOSQLite;

	public DictionaryRepository(Context context) {
		mRetrofit = new Retrofit.Builder()
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl("https://translate.yandex.net")
				.build();
		mContext = context;
		mDbHelper = new DictionaryDbHelper(mContext);
		mStorIOSQLite = DefaultStorIOSQLite.builder()
				.sqliteOpenHelper(mDbHelper).addTypeMapping(WordTranslationModel.class, SQLiteTypeMapping.<WordTranslationModel>builder()
						.putResolver(new WordTranslationModelStorIOSQLitePutResolver()) // object that knows how to perform Put Operation (insert or update)
						.getResolver(new WordTranslationModelStorIOSQLiteGetResolver()) // object that knows how to perform Get Operation
						.deleteResolver(new WordTranslationModelStorIOSQLiteDeleteResolver())  // object that knows how to perform Delete Operation
						.build())
				.build();
	}

	@Override
	public Single<WordTranslation> getWordTranslation(String word) {
		IIDictionaryRepository weatherService = mRetrofit.create(IIDictionaryRepository.class);
		return weatherService.translateWord("trnsl.1.1.20170530T132550Z.856fb0e22726f7be.6b55b7908badee4e4b7ebd4dd5ca03c824a438d2", word, "ru");
	}

	@Override
	public void saveWordTranslation(WordTranslation wordTranslation) {

		mStorIOSQLite
				.put() // Insert or Update
				.object(WordTranslationModel.newWordTranslationModel(wordTranslation.getWord(), wordTranslation.getTranslation()[0])) // Type mapping!
				.prepare()
				.executeAsBlocking();
		/*SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DictionaryContract.DictionaryEntry.COLUMN_WORD, wordTranslation.getWord());
		values.put(DictionaryContract.DictionaryEntry.COLUMN_TRANSLATION, wordTranslation.getTranslation()[0]);
		values.put(DictionaryContract.DictionaryEntry.COLUMN_LANGUAGE, wordTranslation.getLanguage());
		values.put(DictionaryContract.DictionaryEntry.COLUMN_FAVOURITE, 0);
		// Insert the new row, returning the primary key value of the new row
		long newRowId = db.insert(DictionaryContract.DictionaryEntry.TABLE_NAME, null, values);*/
	}

	@Override
	public Observable<List<WordTranslationModel>> getDictionary() {
		return mStorIOSQLite
				.get()
				.listOfObjects(WordTranslationModel.class)
				.withQuery(Query.builder()
						.table(DictionaryContract.DictionaryEntry.TABLE_NAME)
						.build())
				.prepare()
				.asRxObservable(); // Get Result as rx.Observable and subscribe to further updates of tables from Query!

		/*SQLiteDatabase db = mDbHelper.getReadableDatabase();

		String[] projection = {
				DictionaryContract.DictionaryEntry.COLUMN_WORD,
				DictionaryContract.DictionaryEntry.COLUMN_TRANSLATION,
		};

		String sortOrder =
				DictionaryContract.DictionaryEntry.COLUMN_WORD + " DESC";

		Cursor cursor = db.query(
				DictionaryContract.DictionaryEntry.TABLE_NAME,                     // The table to query
				projection,                               // The columns to return
				null,                                // The columns for the WHERE clause
				null,                            // The values for the WHERE clause
				null,                                     // don't group the rows
				null,                                     // don't filter by row groups
				sortOrder                                 // The sort order
		);
		List<WordTranslationModel> wordList = new ArrayList<>();
		while(cursor.moveToNext()) {
			String word = cursor.getString(
					cursor.getColumnIndexOrThrow(DictionaryContract.DictionaryEntry.COLUMN_WORD));
			String translation = cursor.getString(
					cursor.getColumnIndexOrThrow(DictionaryContract.DictionaryEntry.COLUMN_TRANSLATION));
			wordList.add(new WordTranslationModel(word, translation));
		}
		cursor.close();*/

		//return wordList;
	}
}
