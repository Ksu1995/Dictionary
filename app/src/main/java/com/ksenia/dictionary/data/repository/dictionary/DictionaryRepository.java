package com.ksenia.dictionary.data.repository.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ksenia.dictionary.data.model.DictionaryContract;
import com.ksenia.dictionary.data.model.DictionaryDbHelper;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.WordTranslation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.Single;
import rx.Subscriber;

/**
 * Created by Samsonova_K on 30.05.2017.
 */

public class DictionaryRepository implements IDictionaryRepository {

	private Retrofit mRetrofit;
	private DictionaryDbHelper mDbHelper;
	private Context mContext;

	public DictionaryRepository(Context context) {
		mRetrofit = new Retrofit.Builder()
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl("https://translate.yandex.net")
				.build();
		mContext = context;
		mDbHelper = new DictionaryDbHelper(mContext);
	}

	@Override
	public Single<WordTranslation> getWordTranslation(String word) {
		IIDictionaryRepository weatherService = mRetrofit.create(IIDictionaryRepository.class);
		return weatherService.translateWord("trnsl.1.1.20170530T132550Z.856fb0e22726f7be.6b55b7908badee4e4b7ebd4dd5ca03c824a438d2", word, "ru");
	}

	@Override
	public void saveWordTranslation(WordTranslation wordTranslation) {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DictionaryContract.DictionaryEntry.COLUMN_WORD, wordTranslation.getWord());
		values.put(DictionaryContract.DictionaryEntry.COLUMN_TRANSLATION, wordTranslation.getTranslation()[0]);
		values.put(DictionaryContract.DictionaryEntry.COLUMN_LANGUAGE, wordTranslation.getLanguage());
		values.put(DictionaryContract.DictionaryEntry.COLUMN_FAVOURITE, 0);
		// Insert the new row, returning the primary key value of the new row
		long newRowId = db.insert(DictionaryContract.DictionaryEntry.TABLE_NAME, null, values);
	}

	@Override
	public List<WordTranslationModel> getDictionary() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		String[] projection = {
				DictionaryContract.DictionaryEntry.COLUMN_WORD,
				DictionaryContract.DictionaryEntry.COLUMN_TRANSLATION,
				//DictionaryContract.DictionaryEntry.COLUMN_NAME_SUBTITLE
		};

// How you want the results sorted in the resulting Cursor
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
		cursor.close();

		return wordList;
	}

	private static <T> Observable<T> makeObservable(final Callable<T> func) {
		return Observable.create(
				subscriber -> {
					try {
						subscriber.onNext(func.call());
					} catch(Exception ex) {
						Log.e("TAG", "Error reading from the database", ex);
					}
				});
	}

}
