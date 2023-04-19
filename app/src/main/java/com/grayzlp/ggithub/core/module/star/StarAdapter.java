package com.grayzlp.ggithub.core.module.star;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.repo.Starred;
import com.grayzlp.ggithub.util.glide.GlideApp;

import java.util.List;

import javax.annotation.Nonnull;

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

        if (!TextUtils.isEmpty(starred.language)) {
            viewHolder.tag.setVisibility(View.VISIBLE);
            viewHolder.tag.setText(starred.language);
        } else {
            viewHolder.tag.setVisibility(View.GONE);
        }


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

        StarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
