package com.ksenia.dictionary.di.app;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Samsonova_K on 30.05.2017.
 */
@Module
public class AppModule {

	private final Context mAppContext;

	public AppModule(@NonNull Context context) {
		mAppContext = context;
	}

	@Provides
	@Singleton
	Context provideContext() {
		return mAppContext;
	}

}
