package com.ksenia.dictionary.presentation.dictionary.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ksenia.dictionary.MyApplication;
import com.ksenia.dictionary.R;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.di.dictionary.DictionaryModule;
import com.ksenia.dictionary.presentation.dictionary.presenter.IDictionaryPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ksenia on 29.05.2017.
 */

public class DictionaryFragment extends Fragment implements IDictionaryView {

	@Inject
	IDictionaryPresenter mDictionaryPresenter;

	private RecyclerView mWordList;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		MyApplication.get(getContext()).applicationComponent().plus(new DictionaryModule()).inject(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dictionary_fragment, container, false);
		FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDictionaryPresenter.clickAddNewWord();
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		mWordList = (RecyclerView) view.findViewById(R.id.word_list);
		mWordList.setLayoutManager(new LinearLayoutManager(getContext()));
		List<WordTranslationModel> strings = new ArrayList<>();
		Collections.addAll(strings,new WordTranslationModel("a", "bb"));
		mWordList.setAdapter(new WordListAdapter(strings));
		mDictionaryPresenter.bindView(this);
		return view;
	}

	@Override
	public void onDestroyView() {
		mDictionaryPresenter.unbindView();
		super.onDestroyView();
	}

	@Override
	public void addNewWord(String word) {
		mDictionaryPresenter.clickAddNewWord();
	}

	public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

		List<WordTranslationModel> mWordList;

		public WordListAdapter(List<WordTranslationModel> wordList) {
			mWordList = wordList;
		}

		@Override
		public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
			WordViewHolder pvh = new WordViewHolder(v);
			return pvh;
		}

		@Override
		public void onBindViewHolder(WordViewHolder holder, int position) {
			holder.personName.setText(mWordList.get(position).mWord);
			holder.personAge.setText(mWordList.get(position).mTranslation);
		}

		@Override
		public int getItemCount() {
			return mWordList.size();
		}

		public class WordViewHolder extends RecyclerView.ViewHolder {
			TextView personName;
			TextView personAge;

			WordViewHolder(View itemView) {
				super(itemView);
				personName = (TextView) itemView.findViewById(R.id.person_name);
				personAge = (TextView) itemView.findViewById(R.id.person_age);
			}
		}
	}
}
