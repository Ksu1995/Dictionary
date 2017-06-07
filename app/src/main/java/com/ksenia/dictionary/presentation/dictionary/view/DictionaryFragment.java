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
import android.widget.EditText;
import android.widget.TextView;

import com.ksenia.dictionary.MyApplication;
import com.ksenia.dictionary.R;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.di.dictionary.DictionaryModule;
import com.ksenia.dictionary.presentation.dictionary.presenter.IDictionaryPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Ksenia on 29.05.2017.
 */

public class DictionaryFragment extends Fragment implements IDictionaryView {

	@Inject
	IDictionaryPresenter mDictionaryPresenter;

	private List<WordTranslationModel> mWordList;
	private WordListAdapter mWordListAdapter;
	private EditText mWordEditText;

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
		fab.setOnClickListener(view1 -> {
			addNewWord(mWordEditText.getText().toString());
			Snackbar.make(view1, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
		});
		mWordEditText = (EditText) view.findViewById(R.id.new_word);
		mWordList = new ArrayList<>();
		RecyclerView wordList = (RecyclerView) view.findViewById(R.id.word_list);
		wordList.setLayoutManager(new LinearLayoutManager(getContext()));
		mWordListAdapter = new WordListAdapter(mWordList);
		wordList.setAdapter(mWordListAdapter);
		mDictionaryPresenter.bindView(this);
		mDictionaryPresenter.loadDictionary();
		return view;
	}

	@Override
	public void onDestroyView() {
		mDictionaryPresenter.unbindView();
		super.onDestroyView();
	}

	@Override
	public void addNewWord(String word) {
		mDictionaryPresenter.clickAddNewWord(word);
	}

	@Override
	public void setNewWord(WordTranslationModel word) {
		mWordList.add(word);
		mWordListAdapter.notifyDataSetChanged();
	}

	@Override
	public void updateWordList(List<WordTranslationModel> words) {
		mWordList.clear();
		mWordList.addAll(words);
	}

	class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

		List<WordTranslationModel> mWordList;

		WordListAdapter(List<WordTranslationModel> wordList) {
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
			holder.mWord.setText(mWordList.get(position).getWord());
			holder.mTranslation.setText(mWordList.get(position).getTranslation());
		}

		@Override
		public int getItemCount() {
			return mWordList.size();
		}

		class WordViewHolder extends RecyclerView.ViewHolder {
			TextView mWord;
			TextView mTranslation;

			WordViewHolder(View itemView) {
				super(itemView);
				mWord = (TextView) itemView.findViewById(R.id.person_name);
				mTranslation = (TextView) itemView.findViewById(R.id.person_age);
			}
		}
	}
}
