package com.grayzlp.ggithub.core.module.event;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.event.inheritance.WatchEvent;
import com.grayzlp.ggithub.util.glide.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for event fragment on home page.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Activity mHost;
    private List<BaseEvent> mItems;
    private final LayoutInflater mLayoutInflater;

    EventAdapter(Activity host,
                        @NonNull List<BaseEvent> items,
                        LayoutInflater layoutInflater) {
        this.mHost = host;
        this.mItems = items;
        this.mLayoutInflater = layoutInflater;
    }

    void swapItem(List<BaseEvent> items){
        mItems = items;
        notifyDataSetChanged();
    }

    List<BaseEvent> getItem() {
        return mItems;
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_list_enter);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
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
        BaseEvent event = mItems.get(position);
        GlideApp.with(mHost)
                .load(event.actor.avatar_url)
                .placeholder(R.drawable.portrait_placeholder)
                .into(eventHolder.avatar);
        eventHolder.actor.setText(event.actor.login);
        eventHolder.action.setText(event.type);
        eventHolder.createAt.setText(DateUtils.getRelativeTimeSpanString(event.created_at.getTime(),
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS)
                .toString().toLowerCase());
        eventHolder.detail.setText("Faker detail");

        if (event instanceof WatchEvent) {
            WatchEvent we = (WatchEvent) event;
            eventHolder.detail.setText(
                    String.format("%s %s %s", we.actor.login, we.payload.action, we.repo.name));
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
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
