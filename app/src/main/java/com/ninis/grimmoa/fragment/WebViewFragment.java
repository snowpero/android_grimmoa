package com.ninis.grimmoa.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ninis.grimmoa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gypark on 2017. 3. 20..
 */

public class WebViewFragment extends Fragment {

    private static final String KEY_LINK_URL = "key_link_url";

    @BindView(R.id.wv_body)
    WebView webView;
    @BindView(R.id.iv_empty_page)
    ImageView ivEmptyIcon;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private String loadUrl;

    public static WebViewFragment create() {
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initWebView();
    }

    private void initWebView() {
        webView.setWebViewClient(new WebViewClientEx());
        webView.setWebChromeClient(new WebChromeClientEx());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);

        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private void checkLinkUrl() {
        Bundle bundle = this.getArguments();
        if( bundle != null && bundle.containsKey(KEY_LINK_URL) ) {
            String link = bundle.getString(KEY_LINK_URL);
            if(URLUtil.isValidUrl(link))
                webView.loadUrl(link);
        } else {
            webView.loadUrl("http://m.naver.com");
        }
    }

    public void setLoadUrl(String loadUrl) {
        this.loadUrl = loadUrl;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        if( menuVisible ) {
            webView.loadUrl(loadUrl);
        }
    }

    private class WebViewClientEx extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if( ivEmptyIcon.getVisibility() == View.VISIBLE )
                ivEmptyIcon.setVisibility(View.GONE);

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }

    private class WebChromeClientEx extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }
    }
}
