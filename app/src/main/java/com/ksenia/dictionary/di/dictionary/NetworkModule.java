package com.ksenia.dictionary.di.dictionary;

import com.ksenia.dictionary.data.repository.dictionary.IDictionaryService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Samsonova_K on 13.06.2017.
 */
@Module
public class NetworkModule {

	@Provides
	IDictionaryService provideDictionaryService() {
		Retrofit retrofit = new Retrofit.Builder()
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl("https://translate.yandex.net")
				.build();
		return retrofit.create(IDictionaryService.class);
	}
}
