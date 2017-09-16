package com.grayzlp.ggithub.core.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.data.api.model.user.User;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.status_bar_background)
    View statusBarBackground;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.action_bar_tab)
    TabLayout tab;

    @BindView(R.id.content_pager)
    ViewPager contentPager;

    TextView username;
    TextView userEmail;
    ImageView userAvatar;

    ActionBarDrawerToggle drawerToggle;

    GithubPrefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupNavigationHeader();
        setupToolbar();
        setupStatusBar();
        setupViewPager();
        prefs = GithubPrefs.get(this);
    }

    private void setupViewPager() {
        tab.setupWithViewPager(contentPager, true);


    }

    private void setupStatusBar() {
        drawer.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                LinearLayout.LayoutParams lpStatus = (LinearLayout.LayoutParams)
                        statusBarBackground.getLayoutParams();
                lpStatus.height = insets.getSystemWindowInsetTop();
                statusBarBackground.setLayoutParams(lpStatus);

                return insets.consumeSystemWindowInsets();
            }
        });
    }

    private void setupNavigationHeader() {
        // https://stackoverflow.com/questions/33194594/navigationview-get-find-header-layout
        View header = navigation.inflateHeaderView(R.layout.drawer_header);
        username = (TextView) header.findViewById(R.id.title_username);
        userEmail = (TextView) header.findViewById(R.id.title_email);
        userAvatar = (ImageView) header.findViewById(R.id.avatar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        preloadUser();

    }

    private void preloadUser() {
        User user = prefs.getUser();

        username.setText(user.name);
        userEmail.setText(user.email);
        Glide.with(getApplicationContext())
                .load(user.avatar_url)
                .into(userAvatar);

    }

    private void setupToolbar() {
        drawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(R.string.app_name);

        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);

        drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }
}
