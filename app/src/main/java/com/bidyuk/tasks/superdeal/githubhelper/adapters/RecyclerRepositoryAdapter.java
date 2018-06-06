package com.bidyuk.tasks.superdeal.githubhelper.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bidyuk.tasks.superdeal.githubhelper.R;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.ArrayList;

public class RecyclerRepositoryAdapter extends RecyclerView.Adapter<RecyclerRepositoryAdapter.RepositoryHolder> {
    private ArrayList<Repository> repositoryArrayList;

    public RecyclerRepositoryAdapter(ArrayList<Repository> repositories) {
        repositoryArrayList = repositories;
    }

    @Override
    public RepositoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repos_list_item, parent, false);

        RepositoryHolder repositoryHolder = new RepositoryHolder(view);
        return repositoryHolder;
    }

    @Override
    public void onBindViewHolder(RepositoryHolder holder, int position) {
        holder.nameOfReposTextView
                .setText(repositoryArrayList.get(position)
                        .getName());
        holder.descriptionTextView
                .setText(repositoryArrayList.get(position)
                        .getDescription());
    }

    @Override
    public int getItemCount() {
        return repositoryArrayList.size();
    }

    public class RepositoryHolder extends RecyclerView.ViewHolder {
        TextView nameOfReposTextView, descriptionTextView;

        public RepositoryHolder(View itemView) {
            super(itemView);

            nameOfReposTextView = itemView.findViewById(R.id.repos_name_tv);
            descriptionTextView = itemView.findViewById(R.id.description_tv);
        }
    }
}
