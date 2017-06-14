package com.ksenia.dictionary.di.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.ksenia.dictionary.data.model.DictionaryDbHelper;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.model.WordTranslationModelStorIOSQLiteDeleteResolver;
import com.ksenia.dictionary.data.model.WordTranslationModelStorIOSQLiteGetResolver;
import com.ksenia.dictionary.data.model.WordTranslationModelStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Samsonova_K on 13.06.2017.
 */
@Module
public class BdModule {

	@Provides
	SQLiteOpenHelper provideSQLiteOpenHelper(Context context) {
		return new DictionaryDbHelper(context);
	}

	@Provides
	StorIOSQLite provideStorIOSQLite(SQLiteOpenHelper dbHelper) {
		return DefaultStorIOSQLite.builder()
				.sqliteOpenHelper(dbHelper).addTypeMapping(WordTranslationModel.class, SQLiteTypeMapping.<WordTranslationModel>builder()
						.putResolver(new WordTranslationModelStorIOSQLitePutResolver())
						.getResolver(new WordTranslationModelStorIOSQLiteGetResolver())
						.deleteResolver(new WordTranslationModelStorIOSQLiteDeleteResolver())
						.build())
				.build();
	}
}
