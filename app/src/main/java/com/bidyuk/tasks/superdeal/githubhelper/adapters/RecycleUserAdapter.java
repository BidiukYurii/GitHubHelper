package com.bidyuk.tasks.superdeal.githubhelper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bidyuk.tasks.superdeal.githubhelper.R;
import com.squareup.picasso.Picasso;

import org.eclipse.egit.github.core.User;

import java.util.ArrayList;

public class RecycleUserAdapter extends RecyclerView.Adapter<RecycleUserAdapter.UserHolder> {
    private ArrayList<User> users;
    private Context context;

    class UserHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameTextView, locationTextView, blogTextView;
        public UserHolder(View v){
            super(v);
            imageView = (ImageView) v.findViewById(R.id.avatar_img);
            nameTextView = (TextView) v.findViewById(R.id.name_txt);
            locationTextView = (TextView) v.findViewById(R.id.location_txt);
            blogTextView = (TextView) v.findViewById(R.id.blog_txt);
        }
    }
    public RecycleUserAdapter(ArrayList<User> arrayList, Context ctx) {
        users = arrayList;
        context = ctx;
    }

    @Override
    public RecycleUserAdapter.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        UserHolder userHolder = new UserHolder(item);

        return userHolder;
    }

    @Override
    public void onBindViewHolder(RecycleUserAdapter.UserHolder holder, int position) {
        holder.nameTextView.setText(users.get(position).getName());
        holder.locationTextView.setText(users.get(position).getLocation());
        holder.blogTextView.setText(users.get(position).getBlog());
        Picasso.with(context).load(users.get(position).getAvatarUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
