package com.grayzlp.ggithub.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.util.AnimUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Splash activity, also used to check the sign status. Then do different treatment for
 * different states.
 *
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.branch) TextView branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_screen);
        ButterKnife.bind(this);
        animateBranch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        branch.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkSignIn();
            }
        }, 800);
    }

    private void checkSignIn() {
        GithubPrefs githubPrefs = GithubPrefs.get(this);
        if (githubPrefs.isSignedIn()) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
        }

        finish();
    }

    private void animateBranch() {
        branch.setAlpha(0f);
        branch.setScaleX(0.8f);

        branch.animate()
                .alpha(1f)
                .scaleX(1f)
                .setStartDelay(300)
                .setDuration(500)
                .setInterpolator(AnimUtils.getFastOutLinearInInterpolator(this));


    }
}
