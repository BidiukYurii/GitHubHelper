package com.bidyuk.tasks.superdeal.githubhelper.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bidyuk.tasks.superdeal.githubhelper.MainActivity;
import com.bidyuk.tasks.superdeal.githubhelper.R;
import com.bidyuk.tasks.superdeal.githubhelper.adapters.RecycleUserAdapter;
import com.bidyuk.tasks.superdeal.githubhelper.listeners.OnItemClickListener;
import com.bidyuk.tasks.superdeal.githubhelper.listeners.RecyclerItemClickListener;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.UserService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchUserFragment extends Fragment {
    public static final String TAG = "SearchFragment";
    @BindView(R.id.editText2)
    EditText editText;
    ArrayList<User> arrayList;
    RecycleUserAdapter usersAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    GitHubClient gitHubClient;

    public SearchUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        arrayList = new ArrayList<>();
        gitHubClient = new GitHubClient("api.github.com");
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initListeners(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(TAG, arrayList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            arrayList = (ArrayList<User>) savedInstanceState.getSerializable(TAG);
            usersAdapter.notifyDataSetChanged();
        }
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        usersAdapter = new RecycleUserAdapter(arrayList, getActivity().getApplicationContext());
        recyclerView.setAdapter(usersAdapter);
    }

    private void initListeners(View view) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                arrayList.clear();
                if (editable.length() >= 3) {
                    SearchTask searchTask = new SearchTask();
                    searchTask.execute();

                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext()
                , recyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((MainActivity)getActivity()).showRepositoryFragment(arrayList.get(position));
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    private class SearchTask extends AsyncTask<Void, Void, User> {
        private UserService userService;
        private User user;
        @Override
        protected User doInBackground(Void... voids) {
            userService = new UserService(gitHubClient);
            try {
                if (editText.getText() != null)
                    user = userService.getUser(editText.getText().toString());
                else
                    return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        protected void onPostExecute(User us) {
            arrayList.clear();
            if (us !=null)
                arrayList.add(us);
            usersAdapter.notifyDataSetChanged();
        }
    }

}
