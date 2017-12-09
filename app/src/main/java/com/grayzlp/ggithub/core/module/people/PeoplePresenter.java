package com.grayzlp.ggithub.core.module.people;

import android.util.Log;

import com.google.common.base.Preconditions;
import com.grayzlp.ggithub.data.model.user.User;
import com.grayzlp.ggithub.data.repo.people.PeopleRepository;
import com.grayzlp.ggithub.di.ActivityScoped;
import com.grayzlp.ggithub.util.LogUtils;
import com.grayzlp.ggithub.util.scheduler.BaseSchedulerProvider;
import com.grayzlp.ggithub.util.scheduler.SchedulerProvider;

import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@ActivityScoped
public class PeoplePresenter implements PeopleContract.Presenter {

    private static final String TAG = LogUtils.makeLogTag("PeoplePresenter");

    private PeopleContract.View mView;
    private PeopleRepository mRepo;

    private PeopleType mType = PeopleType.FOLLOWER;


    private boolean mFirstLoad = true;

    private CompositeDisposable mCompositeDisposable;

    private final BaseSchedulerProvider mProvider;

    @Inject
    PeoplePresenter(PeopleRepository repository) {
        mRepo = repository;
        mCompositeDisposable = new CompositeDisposable();
        mProvider = SchedulerProvider.getInstance();
    }

    @Override
    public void takeView(PeopleContract.View view) {
        mView = Preconditions.checkNotNull(view);
        load(false);
    }

    @Override
    public void dropView() {
        mView = null;
        mCompositeDisposable.clear();
    }



    private void loadFollowers(boolean forceUpdate) {
        mView.showLoadingIndicator(true);
        if (forceUpdate || mFirstLoad) {
            mRepo.refresh();
            mFirstLoad = false;
        }

        mCompositeDisposable.clear();
        Disposable disposable = mRepo
                .getFollowers()
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui())
                .subscribe(
                        followers -> {
                            mView.showLoadingIndicator(false);
                            mView.showPeople(followers);
                        },
                        error -> {
                            LogUtils.LOGD(TAG, Arrays.toString(error.getStackTrace()));
                            mView.showLoadingError();
                            mView.showLoadingIndicator(false);
                        }
                );
        mCompositeDisposable.add(disposable);

    }

    private void loadFollowings(boolean forceUpdate) {
        mView.showLoadingIndicator(true);
        if (forceUpdate || mFirstLoad) {
            mRepo.refresh();
            mFirstLoad = false;
        }

        mCompositeDisposable.clear();
        Disposable disposable = mRepo
                .getFollowing()
                .subscribeOn(mProvider.io())
                .observeOn(mProvider.ui())
                .subscribe(
                        followers -> {
                            mView.showLoadingIndicator(false);
                            mView.showPeople(followers);
                        },
                        error -> {
                            mView.showLoadingError();
                            mView.showLoadingIndicator(false);
                        }
                );
        mCompositeDisposable.add(disposable);

    }

    @Override
    public void load(boolean forceUpdate) {
        if (mType == PeopleType.FOLLOWER) {
            loadFollowers(forceUpdate);
        } else {
            loadFollowings(forceUpdate);
        }
    }

    @Override
    public void setPeopleType(PeopleType type) {
        if (mType != type) {
            mType = type;
            load(false);
        }
    }
}
