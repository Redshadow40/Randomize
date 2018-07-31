package com.example.aria.randomize;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {
    WebView web;

    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);

        web = (WebView)view.findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl("https://www.random.org/integers/");

        return view;
    }

    public void backHistory(){
        web.goBack();
    }

    public boolean checkHistory(){
        return web.canGoBack();
    }

}
