package com.ninis.grimmoa;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.ninis.grimmoa.fragment.MainFragment;
import com.ninis.grimmoa.fragment.WebViewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.adView)
    AdView adView;

    private MainFragment mainFragment;
    private WebViewFragment webViewFragment;

    public static interface OnPageModeEvent {
        void moveWebViewPage(String url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPager();
        initAdMob();
    }

    private void initViewPager() {
        mainFragment = MainFragment.create();
        webViewFragment = WebViewFragment.create();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initAdMob() {
        MobileAds.initialize(getApplicationContext(), getString(R.string.admob_app_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position > 0) {
                fragment = webViewFragment;
            } else {
                fragment = mainFragment;
                mainFragment.setOnPageModeEvent(onPageModeEvent);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    OnPageModeEvent onPageModeEvent = new OnPageModeEvent() {
        @Override
        public void moveWebViewPage(String url) {
            if (URLUtil.isValidUrl(url)) {
                webViewFragment.setLoadUrl(url);
                viewPager.setCurrentItem(1, true);
            }
        }
    };

    @Override
    public void onBackPressed() {
        if( viewPager.getCurrentItem() > 0 ) {
            viewPager.setCurrentItem(0, true);
        } else {
            super.onBackPressed();
        }
    }
}
