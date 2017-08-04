package com.lzm.knittinghelp2;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lzm.knittinghelp2.counters.CountersFragment;
import com.lzm.knittinghelp2.domain.Pattern;
import com.lzm.knittinghelp2.enums.KnittingFragment;
import com.lzm.knittinghelp2.helpers.FragmentHelper;
import com.lzm.knittinghelp2.notebook.NotebookFragment;
import com.lzm.knittinghelp2.pattern.PatternFragment;

import static com.lzm.knittinghelp2.enums.KnittingFragment.COUNTERS;
import static com.lzm.knittinghelp2.enums.KnittingFragment.NOTEBOOK;
import static com.lzm.knittinghelp2.enums.KnittingFragment.PATTERN;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        NotebookFragment.OnPatternClickedListener {

    public static final String SAVED_ACTIVE_FRAGMENT = "activeFragment";
    public static final String SAVED_PATTERN_ID = "patternId";

    KnittingFragment activeFragment = NOTEBOOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        TextView versionTextView = navigationView.findViewById(R.id.drawer_version);
//        versionTextView.setText(getAppVersion());

        int titleRes = NOTEBOOK.getTitleId();
        NotebookFragment notebookFragment = NotebookFragment.newInstance();
        FragmentHelper.openFragment(this, notebookFragment, getString(titleRes), false);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private String getAppVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            return getString(R.string.app_version, version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setActiveFragment(KnittingFragment activeFragment) {
        this.activeFragment = activeFragment;
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        makeToolbarIconsWhite(menu);

        MenuItem itemPatternEdit = menu.findItem(R.id.action_pattern_edit);

        if (activeFragment == PATTERN) {
            itemPatternEdit.setVisible(true);
        } else {
            itemPatternEdit.setVisible(false);
        }

        return true;
    }

    private void makeToolbarIconsWhite(Menu menu) {
        int white = ContextCompat.getColor(this, R.color.white);
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(white, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        int titleRes = NOTEBOOK.getTitleId();
        Fragment fragment = NotebookFragment.newInstance();

        if (id == R.id.nav_notebook) {
            titleRes = NOTEBOOK.getTitleId();
            fragment = NotebookFragment.newInstance();
        } else if (id == R.id.nav_counters) {
            titleRes = COUNTERS.getTitleId();
            fragment = CountersFragment.newInstance();
        }

        FragmentHelper.openFragment(this, fragment, getString(titleRes), false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPatternClicked(Pattern pattern) {
        long patternId = pattern.getId();
        String title = pattern.getName();
        PatternFragment patternFragment = PatternFragment.newInstance(patternId);
        FragmentHelper.openFragment(this, patternFragment, title);
    }
}
