package com.ksenia.dictionary.presentation.dictionary.view;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.ksenia.dictionary.MyApplication;
import com.ksenia.dictionary.R;
import com.ksenia.dictionary.data.model.WordTranslationModel;
import com.ksenia.dictionary.data.network.data.Language;
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

public class DictionaryFragment extends Fragment implements IDictionaryView, SearchView.OnQueryTextListener {

	@Inject
	IDictionaryPresenter mDictionaryPresenter;

	private List<WordTranslationModel> mWordList;
	private WordListAdapter mWordListAdapter;
	private RecyclerView mWordListRecyclerView;
	private EditText mWordEditText;
	private Language mLangFrom;
	private Language mLangTo;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		MyApplication.get(getContext()).applicationComponent().plus(new DictionaryModule(), new BdModule(), new NetworkModule()).inject(this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_main, menu);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
		searchView.setSubmitButtonEnabled(true);
		searchView.setOnQueryTextListener(this);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dictionary_fragment, container, false);
		FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
		fab.setOnClickListener(view1 -> clickNewWord(mWordEditText.getText().toString(), mLangTo, mLangFrom));
		mWordEditText = (EditText) view.findViewById(R.id.new_word);
		mWordList = new ArrayList<>();
		mWordListRecyclerView = (RecyclerView) view.findViewById(R.id.word_list);
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
		mLayoutManager.setReverseLayout(true);
		mLayoutManager.setStackFromEnd(true);
		mWordListRecyclerView.setLayoutManager(mLayoutManager);

		mWordListAdapter = new WordListAdapter(mWordList);
		mWordListRecyclerView.setAdapter(mWordListAdapter);
		mDictionaryPresenter.bindView(this);
		mDictionaryPresenter.loadDictionary();
		Spinner langToSpinner = (Spinner) view.findViewById(R.id.langTo_spinner);
		Spinner langFromSpinner = (Spinner) view.findViewById(R.id.langFrom_spinner);
		ArrayAdapter<Language> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Language.values());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		langToSpinner.setAdapter(adapter);
		langToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				mLangTo = Language.values()[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				mLangTo = Language.values()[0];
			}
		});

		langFromSpinner.setAdapter(adapter);
		langFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				mLangFrom = Language.values()[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				mLangFrom = Language.values()[0];
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
	public void clickNewWord(String word, Language langTo, Language langFrom) {
		mDictionaryPresenter.clickAddNewWord(word, langTo, langFrom);
	}

	@Override
	public void addNewWord(WordTranslationModel word) {
		mWordEditText.setText("");
		mWordList.add(word);
		mWordListAdapter.notifyDataSetChanged();
		mWordListRecyclerView.scrollToPosition(mWordList.size() - 1);
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

	@Override
	public void addToFavourite(int position) {
		WordListAdapter.WordViewHolder holder = (WordListAdapter.WordViewHolder) mWordListRecyclerView.findViewHolderForAdapterPosition(position);
		WordTranslationModel wordTranslationModel = mWordList.get(position);
		WordTranslationModel updatedWordTranslationModel = WordTranslationModel.newWordTranslationModel(wordTranslationModel.getWord(),
				wordTranslationModel.getTranslation(), wordTranslationModel.getLanguage(), !wordTranslationModel.isFavourite());
		mWordList.set(position, updatedWordTranslationModel);
		if (!wordTranslationModel.isFavourite()) {
			holder.mFavouriteButton.setImageDrawable(getView().getResources().getDrawable(android.R.drawable.star_big_on));
		} else {
			holder.mFavouriteButton.setImageDrawable(getView().getResources().getDrawable(android.R.drawable.star_big_off));
		}
		mDictionaryPresenter.updateFavouriteInDictionaryItem(updatedWordTranslationModel);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		mWordListAdapter.getFilter().filter(newText);
		return true;
	}

	class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> implements Filterable {

		private List<WordTranslationModel> mWordList;
		private List<WordTranslationModel> mFilteredWordList;
		private WordFilter mWordFilter;

		WordListAdapter(List<WordTranslationModel> wordList) {
			mWordList = wordList;
			mFilteredWordList = mWordList;
			getFilter();
		}

		@Override
		public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
			return new WordViewHolder(v);
		}

		@Override
		public void onBindViewHolder(WordViewHolder holder, int position) {
			WordTranslationModel wordTranslationModel = mFilteredWordList.get(position);
			holder.mWord.setText(wordTranslationModel.getWord());
			holder.mTranslation.setText(wordTranslationModel.getTranslation());
			if (wordTranslationModel.isFavourite()) {
				holder.mFavouriteButton.setImageDrawable(getView().getResources().getDrawable(android.R.drawable.star_big_on));
			}
		}

		@Override
		public int getItemCount() {
			return mFilteredWordList.size();
		}

		@Override
		public Filter getFilter() {
			if (mWordFilter == null) {
				mWordFilter = new WordFilter();
			}
			return mWordFilter;
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
				mFavouriteButton.setOnClickListener(v -> {
					addToFavourite(getAdapterPosition());
				});
			}
		}

		private class WordFilter extends Filter {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null && constraint.length() > 0) {
					ArrayList<WordTranslationModel> tempList = new ArrayList<>();

					for (WordTranslationModel wordTranslationModel : mWordList) {
						if (wordTranslationModel.getWord().toLowerCase().contains(constraint.toString().toLowerCase())) {
							tempList.add(wordTranslationModel);
						}
					}

					filterResults.count = tempList.size();
					filterResults.values = tempList;
				} else {
					filterResults.count = mWordList.size();
					filterResults.values = mWordList;
				}

				return filterResults;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				mFilteredWordList = (ArrayList<WordTranslationModel>) results.values;
				notifyDataSetChanged();
			}
		}
	}
}
