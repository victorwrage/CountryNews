package net.easyyy.countrynews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import net.easyyy.countrynews.R;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * webview
 *
 * @author xiaoyl
 * @date 2013-07-20
 */
public class FragmentWebview extends BaseFragment {
    private String title = "";

    String url = "http://www.baidu.com";
    @Bind(R.id.tbsContent)
    com.tencent.smtt.sdk.WebView tbsContent;
    @Bind(R.id.empty_lay)
    RelativeLayout empty_lay;

    @Bind(R.id.header_btn_lay)
    LinearLayout header_btn_lay;
    @Bind(R.id.header_setting_lay)
    LinearLayout header_setting_lay;
    @Bind(R.id.header_setting_iv)
    ImageView header_setting_iv;
    @Bind(R.id.header_title)
    TextView header_title;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.webview_layout, container, false);
        ButterKnife.bind(FragmentWebview.this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {

        RxView.clicks(header_setting_lay).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Refresh());
        RxView.clicks(header_btn_lay).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Back());
        header_title.setText("商城");
        header_setting_lay.setVisibility(View.VISIBLE);

    }

    private void Refresh() {
        if (tbsContent != null && tbsContent.canGoBack()) {
            tbsContent.reload();
        }
    }

    public void Back() {
        super.Back();
        if (canBack()) {
            tbsContent.goBack();
        } else {

        }
    }

    @Override
    public void refreshState() {
        super.refreshState();

        refreshView();
    }

    public void setLoadUrl(String url) {
        this.url = url;
        if (tbsContent != null) {
            tbsContent.loadUrl(url);
        }
    }

    private void refreshView() {
        tbsContent.loadUrl(url);
        WebSettings webSettings = tbsContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        tbsContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                empty_lay.setVisibility(View.GONE);
                tbsContent.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    public boolean canBack() {
        return tbsContent.canGoBack();
    }

    @Override
    public void onPause() {
        tbsContent.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        tbsContent.onResume();
        super.onResume();
    }
}
