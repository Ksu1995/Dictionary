package com.ksenia.dictionary.data.model;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Samsonova_K on 06.06.2017.
 */

public class MyWordTranslationModelPutResolver extends PutResolver<WordTranslationModel> {

	@NonNull
	@Override
	public PutResult performPut(@NonNull StorIOSQLite storIOSQLite, @NonNull WordTranslationModel tweetWithUser) {
		// We can even reuse StorIO methods
		final PutResult putResults = storIOSQLite
				.put()
				.object(tweetWithUser)
				.prepare() // BTW: it will use transaction!
				.executeAsBlocking();

		final Set<String> affectedTables = new HashSet<>(1);

		affectedTables.add(DictionaryContract.DictionaryEntry.TABLE_NAME);

		// Actually, it's not very clear what PutResult should we return here…
		// Because there is no table for this pair of tweet and user
		// So, let's just return Update Result
		return PutResult.newUpdateResult(1, affectedTables);
	}
}