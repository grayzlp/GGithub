package com.grayzlp.ggithub.core.module.people;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.user.SimpleUser;
import com.grayzlp.ggithub.ui.widget.CircularImageView;
import com.grayzlp.ggithub.util.LogUtils;
import com.grayzlp.ggithub.util.glide.GlideApp;

import java.util.List;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleHolder> {

    private Activity mHost;
    private List<SimpleUser> mItems;
    private LayoutInflater mInflater;

    PeopleAdapter(@Nonnull Activity host,
                  @Nonnull List<SimpleUser> items,
                  @Nonnull LayoutInflater inflater) {
        mHost = host;
        mItems = items;
        mInflater = inflater;
    }

    void swapItem(List<SimpleUser> newItems) {
        mItems = newItems;
        notifyDataSetChanged();
    }

    @Override
    public PeopleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = mInflater.inflate(R.layout.people_list_item, parent, false);
        return new PeopleHolder(root);
    }

    @Override
    public void onBindViewHolder(PeopleHolder holder, int position) {
        GlideApp.with(mHost)
                .load(mItems.get(position).avatar_url)
                .into(holder.avatar);

        holder.username.setText(mItems.get(position).login);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    static class PeopleHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        CircularImageView avatar;
        @BindView(R.id.username)
        TextView username;

        PeopleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
