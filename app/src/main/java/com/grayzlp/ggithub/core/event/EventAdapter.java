package com.grayzlp.ggithub.core.event;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.api.model.event.BaseEvent;
import com.grayzlp.ggithub.data.api.model.event.inheritance.WatchEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for event fragment on home page.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final Activity mHost;
    BaseEvent[] mItems;
    private final LayoutInflater mLayoutInflater;

    public EventAdapter(Activity host,
                        @Nullable BaseEvent[] items,
                        LayoutInflater layoutInflater) {
        this.mHost = host;
        if (items == null){
            items = new BaseEvent[0];
        }
        this.mItems = items;
        this.mLayoutInflater = layoutInflater;
    }

    public void swapItem(BaseEvent[] items){
        mItems = items;
        notifyDataSetChanged();
    }

//    interface EventItemListener {
//        void onAvatarClick(BaseEvent event);
//        void onActorClick(BaseEvent event);
//        void onDetailClick(BaseEvent event);
//    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final EventHolder holder = new EventHolder(mLayoutInflater.inflate
                (R.layout.event_list_item, parent, false));
        // TODO add callback
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EventHolder eventHolder = (EventHolder) holder;
        BaseEvent event = mItems[position];
        Glide.with(mHost)
                .load(event.actor.avatar_url)
                .into(eventHolder.avatar);
        eventHolder.actor.setText(event.actor.login);
        eventHolder.action.setText(event.type);
        eventHolder.createAt.setText(event.created_at.toString());
        eventHolder.detail.setText("Faker detail");

        if (event instanceof WatchEvent) {
            WatchEvent we = (WatchEvent) event;
            eventHolder.detail.setText(we.actor.login + " " + we.payload.action + " " + we.repo.name);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }

    static class EventHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.actor)
        TextView actor;
        @BindView(R.id.action)
        TextView action;
        @BindView(R.id.detail)
        TextView detail;
        @BindView(R.id.create_at)
        TextView createAt;

        public EventHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
