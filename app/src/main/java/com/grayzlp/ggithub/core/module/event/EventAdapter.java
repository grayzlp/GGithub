package com.grayzlp.ggithub.core.module.event;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.core.activity.RepositoryActivity;
import com.grayzlp.ggithub.core.activity.UserActivity;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.event.inheritance.WatchEvent;
import com.grayzlp.ggithub.util.LogUtils;
import com.grayzlp.ggithub.util.glide.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for event fragment on home page.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = LogUtils.makeLogTag(EventAdapter.class);

    private final Activity host;
    private List<BaseEvent> items;
    private final LayoutInflater layoutInflater;

    EventAdapter(Activity host,
                 @NonNull List<BaseEvent> items,
                 LayoutInflater layoutInflater) {
        this.host = host;
        this.items = items;
        this.layoutInflater = layoutInflater;
    }

    void swapItem(List<BaseEvent> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    List<BaseEvent> getItem() {
        return items;
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
        final EventHolder holder = new EventHolder(layoutInflater.inflate
                (R.layout.event_list_item, parent, false));
        holder.avatar.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(host, UserActivity.class);
            BaseEvent event = getItem().get(holder.getAdapterPosition());
            UserActivity.launch(host, event.actor.login);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EventHolder eventHolder = (EventHolder) holder;
        BaseEvent event = items.get(position);
        GlideApp.with(host)
                .load(event.actor.avatar_url)
                .placeholder(R.drawable.portrait_placeholder)
                .into(eventHolder.avatar);
        eventHolder.actor.setText(event.actor.login);
        eventHolder.action.setText(event.type);
        eventHolder.createAt.setText(DateUtils.getRelativeTimeSpanString(event.created_at.getTime(),
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS)
                .toString().toLowerCase());
        // TODO Fix event detail
        eventHolder.detail.setText("Faker detail");
        eventHolder.detail.setOnClickListener(
                v -> RepositoryActivity.launch(host, event.repo.name));

        if (event instanceof WatchEvent) {
            WatchEvent we = (WatchEvent) event;
            eventHolder.detail.setText(
                    String.format("%s %s %s", we.actor.login, we.payload.action, we.repo.name));
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
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
