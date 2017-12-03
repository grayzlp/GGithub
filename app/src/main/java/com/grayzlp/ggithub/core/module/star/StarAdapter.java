package com.grayzlp.ggithub.core.module.star;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.repo.Starred;
import com.grayzlp.ggithub.util.LogUtils;
import com.grayzlp.ggithub.util.glide.GlideApp;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Activity mHost;
    private List<Starred> mItems;
    private final LayoutInflater mInflater;

    StarAdapter(Activity host,
                @Nonnull List<Starred> items,
                LayoutInflater inflater) {
        mHost = host;
        mItems = items;
        mInflater = inflater;
    }

    void swapItem(List<Starred> items) {
        mItems = items;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO add callback
        return new StarViewHolder(mInflater.inflate(R.layout.star_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StarViewHolder viewHolder = (StarViewHolder) holder;
        Starred starred = mItems.get(position);

        viewHolder.title.setText(starred.full_name);
        viewHolder.tag.setText(starred.language);
        viewHolder.description.setText(starred.description);
        viewHolder.forksCount.setText(String.valueOf(starred.forks_count));
        viewHolder.stargazersCount.setText(String.valueOf(starred.stargazers_count));

        GlideApp.with(mHost)
                .load(starred.owner.avatar_url)
                .into(viewHolder.avatar);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    static class StarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repo_owner_avatar) ImageView avatar;
        @BindView(R.id.repo_title) TextView title;
        @BindView(R.id.repo_tag) TextView tag;
        @BindView(R.id.more_action) ImageView actionBar;
        @BindView(R.id.description) TextView description;
        @BindView(R.id.forks_count) TextView forksCount;
        @BindView(R.id.stargazers_count) TextView stargazersCount;

        public StarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
