package com.grayzlp.ggithub.core.module.gist;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.gist.Gist;

import java.util.List;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GistAdapter extends RecyclerView.Adapter<GistAdapter.GistHolder> {

    private Activity mHost;
    private List<Gist> mItems;
    private LayoutInflater mInflater;

    public GistAdapter(@Nonnull Activity host,
                       @Nonnull List<Gist> items,
                       @Nonnull LayoutInflater inflater) {
        mHost = host;
        mItems = items;
        mInflater = inflater;
    }

    void swapItem(List<Gist> newItems) {
        mItems = newItems;
        notifyDataSetChanged();
    }

    @Override
    public GistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GistHolder(mInflater.inflate(R.layout.gist_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GistHolder holder, int position) {
        holder.ownerText.setText(mItems.get(position).owner.login);
        holder.description.setText(mItems.get(position).description);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    static class GistHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.owner) TextView ownerText;
        @BindView(R.id.description) TextView description;

        public GistHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
