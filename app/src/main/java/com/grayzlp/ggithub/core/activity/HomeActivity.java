package com.grayzlp.ggithub.core.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.grayzlp.ggithub.R;
import com.grayzlp.ggithub.core.module.event.EventFragment;
import com.grayzlp.ggithub.core.module.gist.GistFragment;
import com.grayzlp.ggithub.core.module.people.PeopleFragment;
import com.grayzlp.ggithub.core.module.star.StarFragment;
import com.grayzlp.ggithub.data.model.user.User;
import com.grayzlp.ggithub.data.prefs.GithubPrefs;
import com.grayzlp.ggithub.util.LogUtils;
import com.grayzlp.ggithub.util.glide.GlideApp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class HomeActivity extends DaggerAppCompatActivity {

    private static final String TAG = LogUtils.makeLogTag("HomeActivity");

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

    @Inject
    EventFragment mEventsFragment;
    @Inject
    StarFragment mStarsFragment;
    @Inject
    PeopleFragment mPeopleFragment;
    @Inject
    GistFragment mGistFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setupNavigation();
        setupDrawer();
        setupToolbar();
        setupStatusBar();
        setupViewPager();
        prefs = GithubPrefs.get(this);
    }

    private void setupDrawer() {
        navigation.setNavigationItemSelectedListener(item -> {
            drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {

                @Override
                public void onDrawerClosed(View drawerView) {
                    drawer.removeDrawerListener(this);
                    switch (item.getItemId()) {
                        case R.id.drawer_events:
                        case R.id.drawer_stars:
                        case R.id.drawer_people:
                        case R.id.drawer_gists:
                            selectPageByMenu(item);
                            break;
                        case R.id.drawer_sign_out:
                            signOut();
                            break;
                        case R.id.drawer_clear:
                            clearCache();
                            break;
                        case R.id.drawer_repos:
                            RepositoriesListActivity.launch(HomeActivity.this, prefs.getUserName());
                            break;

                    }
                }
            });
            drawer.closeDrawers();
            return true;
        });
    }

    private void clearCache() {
        new ClearDiskCacheAsyncTask().execute(this);
        Snackbar.make(getWindow().getDecorView(),
                getString(R.string.clear_success),
                Snackbar.LENGTH_SHORT).show();
    }

    private void signOut() {
        new MaterialDialog.Builder(this)
                .content(R.string.sign_out_check)
                .positiveText(R.string.sign_out)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    prefs.signOut();
                    startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                    finish();
                })
                .onNegative((dialog, which) -> dialog.cancel())
                .build()
                .show();
    }

    public void selectPageByMenu(MenuItem item) {
        int position = MenuPagerMapHolder.MENU_PAGER_MAP.get(item.getItemId());
        contentPager.setCurrentItem(position);
        item.setChecked(true);
        drawer.closeDrawers();
    }


    private void setupViewPager() {
        tab.setupWithViewPager(contentPager, true);
        contentPager.setAdapter(
                new HomeContentAdapter(getSupportFragmentManager(),
                        this,
                        mEventsFragment,
                        mStarsFragment,
                        mPeopleFragment,
                        mGistFragment));
        contentPager.setOffscreenPageLimit(HomeContentAdapter.PAGE_COUNT);
        contentPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int itemId = MenuPagerMapHolder.MENU_PAGER_MAP.inverse().get(position);
                navigation.setCheckedItem(itemId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupStatusBar() {
        drawer.setOnApplyWindowInsetsListener((v, insets) -> {
            LinearLayout.LayoutParams lpStatus = (LinearLayout.LayoutParams)
                    statusBarBackground.getLayoutParams();
            lpStatus.height = insets.getSystemWindowInsetTop();
            statusBarBackground.setLayoutParams(lpStatus);

            return insets.consumeSystemWindowInsets();
        });
    }

    private void setupNavigation() {
        // https://stackoverflow.com/questions/33194594/navigationview-get-find-header-layout
        View header = navigation.inflateHeaderView(R.layout.drawer_header);
        username = header.findViewById(R.id.title_username);
        userEmail = header.findViewById(R.id.title_email);
        userAvatar = header.findViewById(R.id.avatar);
        userAvatar.setOnClickListener(v ->
                UserActivity.launch(HomeActivity.this, prefs.getUserName()));

        navigation.setCheckedItem(R.id.drawer_events);
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
        GlideApp.with(getApplicationContext())
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

    public static class MenuPagerMapHolder {

        static BiMap<Integer, Integer> MENU_PAGER_MAP =
                HashBiMap.create(HomeContentAdapter.PAGE_COUNT);

        static {
            MENU_PAGER_MAP.put(R.id.drawer_events, HomeContentAdapter.PAGE_EVENT);
            MENU_PAGER_MAP.put(R.id.drawer_stars, HomeContentAdapter.PAGE_STARS);
            MENU_PAGER_MAP.put(R.id.drawer_people, HomeContentAdapter.PAGE_PEOPLE);
            MENU_PAGER_MAP.put(R.id.drawer_gists, HomeContentAdapter.PAGE_GIST);
        }

    }

    public static class HomeContentAdapter extends FragmentStatePagerAdapter {

        static final int PAGE_EVENT = 0;
        static final int PAGE_STARS = 1;
        static final int PAGE_PEOPLE = 2;
        static final int PAGE_GIST = 3;


        Context mContext;

        static SparseIntArray PAGE_TITLE = new SparseIntArray(4);

        static {
            PAGE_TITLE.append(PAGE_EVENT, R.string.events);
            PAGE_TITLE.append(PAGE_STARS, R.string.stars);
            PAGE_TITLE.append(PAGE_PEOPLE, R.string.people);
            PAGE_TITLE.append(PAGE_GIST, R.string.gists);
        }

        static final int PAGE_COUNT = PAGE_TITLE.size();


        EventFragment mEventsFragment;
        StarFragment mStarsFragment;
        PeopleFragment mPeopleFragment;
        GistFragment mGistFragment;

        HomeContentAdapter(FragmentManager fm, Context context,
                           EventFragment eventsFragment,
                           StarFragment starsFragment,
                           PeopleFragment peopleFragment,
                           GistFragment gistFragment) {
            super(fm);
            mContext = context;
            mEventsFragment = eventsFragment;
            mStarsFragment = starsFragment;
            mPeopleFragment = peopleFragment;
            mGistFragment = gistFragment;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGE_EVENT:
                    return mEventsFragment;
                case PAGE_STARS:
                    return mStarsFragment;
                case PAGE_PEOPLE:
                    return mPeopleFragment;
                case PAGE_GIST:
                    return mGistFragment;
                default:
                    return new Fragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getResources().getString(PAGE_TITLE.get(position));
        }

        @Override
        public int getCount() {
            return PAGE_TITLE.size();
        }
    }


    private static class ClearDiskCacheAsyncTask extends AsyncTask<Context, Void, Void> {

        @Override
        protected Void doInBackground(Context... contexts) {
            GlideApp.get(contexts[0]).clearDiskCache();
            return null;
        }
    }

}
