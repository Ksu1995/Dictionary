package com.ksenia.dictionary.data.repository.dictionary;


import com.ksenia.dictionary.data.network.data.WordTranslation;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Single;

/**
 * Created by Ksenia on 28.05.2017.
 */

public interface IDictionaryService {

    @POST("/api/v1.5/tr.json/translate")
	Single<WordTranslation> translateWord(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);
}
