package com.ksenia.dictionary.data.model;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResolver;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Samsonova_K on 06.06.2017.
 */

public class MyWordTranslationModelDeleteResolver  extends DeleteResolver<WordTranslationModel> {


	@NonNull
	@Override
	public DeleteResult performDelete(@NonNull StorIOSQLite storIOSQLite, @NonNull WordTranslationModel object) {
		// We can even reuse StorIO methods
		storIOSQLite
				.delete()
				.object(object)
				.prepare()
				.executeAsBlocking();

		final Set<String> affectedTables = new HashSet<>(2);

		affectedTables.add(DictionaryContract.DictionaryEntry.TABLE_NAME);

		return DeleteResult.newInstance(1, affectedTables);
	}
}