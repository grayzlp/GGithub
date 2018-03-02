package com.grayzlp.ggithub.util;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.grayzlp.ggithub.util.glide.GlideApp;

import javax.annotation.Nonnull;

/**
 * A util to get GitHub contribution chart.
 */

// TODO How to convert svg to image view
public class GitHubChartAPI {

    private static final String ENDPOINT = "http://ghchart.rshah.org/";

    private GitHubChartAPI(){}


    public static void loadGitHubChart(@Nonnull Context context,
                                       @Nonnull ImageView imageView,
                                       @Nonnull String userName) {
        GlideApp.with(context).load(ENDPOINT + userName).into(imageView);
    }

}
