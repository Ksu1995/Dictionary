package com.ksenia.dictionary.data.repository.dictionary;

import com.ksenia.dictionary.data.network.data.WordTranslation;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Single;

/**
 * Created by Samsonova_K on 30.05.2017.
 */

public class DictionaryRepository implements IDictionaryRepository {

	private Retrofit mRetrofit;

	public DictionaryRepository() {
		mRetrofit = new Retrofit.Builder()
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl("https://translate.yandex.net")
				.build();
	}


	@Override
	public Single<WordTranslation> getWordTranslation() {
		IIDictionaryRepository weatherService = mRetrofit.create(IIDictionaryRepository.class);
		return weatherService.translateWord("trnsl.1.1.20170530T132550Z.856fb0e22726f7be.6b55b7908badee4e4b7ebd4dd5ca03c824a438d2", "mother", "en");
	}
}
