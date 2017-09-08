package com.grayzlp.ggithub.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.util.AnimUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Splash activity, also used to check the sign status. Then do different treatment for
 * different states.
 *
 */
public class SplashActivity extends Activity {

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
        checkSignIn();
    }

    private void checkSignIn() {
        // TODO Fix this after complete the module of api
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
