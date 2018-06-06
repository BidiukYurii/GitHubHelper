package com.bidyuk.tasks.superdeal.githubhelper.fragments;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bidyuk.tasks.superdeal.githubhelper.MainActivity;
import com.bidyuk.tasks.superdeal.githubhelper.R;
import com.bidyuk.tasks.superdeal.githubhelper.adapters.RecyclerRepositoryAdapter;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RepositoryFragment extends Fragment {
    @BindView(R.id.fragment_recycler_view)
    RecyclerView recyclerView;
    private RecyclerRepositoryAdapter repositoryAdapter;

    private ArrayList<Repository> repositoryArrayList;

    private static final String USER_PARAM = "user";
    private static final String ARRAY_PARAM = "array";


    public RepositoryFragment() {
        // Required empty public constructor
    }

    public static RepositoryFragment newInstance(User user) {
        RepositoryFragment fragment = new RepositoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_PARAM, user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repositoryArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        User user = (User) getArguments().getSerializable(USER_PARAM);
        SearchTask searchTask = new SearchTask();
        searchTask.execute(user);
        toolBarInit(view, user);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ARRAY_PARAM, repositoryArrayList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            repositoryArrayList = (ArrayList<Repository>) savedInstanceState.getSerializable(ARRAY_PARAM);
    }

    private void toolBarInit(View view, User user) {
        android.widget.Toolbar toolbar = view.findViewById(R.id.tool_bar);
        Drawable drawable= getResources().getDrawable(R.drawable.arrow_left);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources()
                , Bitmap.createScaledBitmap(bitmap, 80, 50, true));
        toolbar.setNavigationIcon(newdrawable);
        toolbar.setTitle(user.getName() + " Repositories (" + user.getPublicRepos() + ")");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showSearchUserFragment();
            }
        });
    }

    private class SearchTask extends AsyncTask<User, Void, ArrayList<Repository>> {
        private RepositoryService repositoryService;
        ArrayList<Repository> repositories;
        User user;
        @Override
        protected ArrayList<Repository> doInBackground(User... users) {
            user = users[0];
            try {
                repositoryService = new RepositoryService();
                repositories = (ArrayList<Repository>) repositoryService.getRepositories(user.getLogin());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return repositories;
        }

        @Override
        protected void onPostExecute(ArrayList<Repository> result) {
            if (result != null)
                repositoryArrayList.addAll(result);
            repositoryAdapter = new RecyclerRepositoryAdapter(repositoryArrayList);
            recyclerView.setAdapter(repositoryAdapter);
        }
    }

}
