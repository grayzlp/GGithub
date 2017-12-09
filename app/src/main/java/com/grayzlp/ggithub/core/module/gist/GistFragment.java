package com.grayzlp.ggithub.core.module.gist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.di.ActivityScoped;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


// TODO may develop this module in the future
@ActivityScoped
public class GistFragment extends DaggerFragment {

    @Inject
    public GistFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gist, container, false);
    }
}
