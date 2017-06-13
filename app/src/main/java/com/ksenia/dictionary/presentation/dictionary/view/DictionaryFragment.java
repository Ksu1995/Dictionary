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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ksenia.dictionary.MyApplication;
import com.ksenia.dictionary.R;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.di.dictionary.BdModule;
import com.ksenia.dictionary.di.dictionary.DictionaryModule;
import com.ksenia.dictionary.di.dictionary.NetworkModule;
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
	private Spinner mLangFrom;
	private Spinner mLangTo;
	private String mCurrentLang;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.get(getContext()).applicationComponent().plus(new DictionaryModule(), new BdModule(), new NetworkModule()).inject(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dictionary_fragment, container, false);
		FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(view1 -> addNewWord(mWordEditText.getText().toString(), mCurrentLang));
		mWordEditText = (EditText) view.findViewById(R.id.new_word);
		mWordList = new ArrayList<>();
		RecyclerView wordList = (RecyclerView) view.findViewById(R.id.word_list);
		wordList.setLayoutManager(new LinearLayoutManager(getContext()));
		mWordListAdapter = new WordListAdapter(mWordList);
		wordList.setAdapter(mWordListAdapter);
		mDictionaryPresenter.bindView(this);
		mDictionaryPresenter.loadDictionary();
		mLangTo = (Spinner) view.findViewById(R.id.langTo_spinner);
		mLangFrom = (Spinner) view.findViewById(R.id.langFrom_spinner);
		String[] languages = getResources().getStringArray(R.array.languages);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, languages);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mLangTo.setAdapter(adapter);
		mLangTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				mCurrentLang = languages[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				mCurrentLang = languages[0];
			}
		});
		return view;
	}

	@Override
	public void onDestroyView() {
		mDictionaryPresenter.unbindView();
		super.onDestroyView();
	}

	@Override
	public void addNewWord(String word, String langTo) {
		mDictionaryPresenter.clickAddNewWord(word, langTo);
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

	@Override
	public void showError() {
		Snackbar.make(getView(), "Error!!!", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show();
	}

	class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

		List<WordTranslationModel> mWordList;

		WordListAdapter(List<WordTranslationModel> wordList) {
			mWordList = wordList;
		}

		@Override
		public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
			return new WordViewHolder(v);
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
			ImageButton mFavouriteButton;

			WordViewHolder(View itemView) {
				super(itemView);
				mWord = (TextView) itemView.findViewById(R.id.person_name);
				mTranslation = (TextView) itemView.findViewById(R.id.person_age);
				mFavouriteButton = (ImageButton) itemView.findViewById(R.id.favorite);
				mFavouriteButton.setOnClickListener(v -> mFavouriteButton.setImageDrawable(itemView.getContext().getDrawable(android.R.drawable.star_big_on)));
			}
		}
	}
}
