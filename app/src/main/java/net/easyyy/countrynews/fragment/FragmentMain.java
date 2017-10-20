package net.easyyy.countrynews.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.socks.library.KLog;

import net.easyyy.countrynews.R;
import net.easyyy.countrynews.adapter.HotNewsAdapter;
import net.easyyy.countrynews.bean.HotNewsBean;
import net.easyyy.countrynews.customView.RecyclerViewWithEmpty;
import net.easyyy.countrynews.present.QueryPresent;
import net.easyyy.countrynews.util.Utils;
import net.easyyy.countrynews.view.IView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.listener.SaveListener;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

public class FragmentMain extends BaseFragment implements IView {

    QueryPresent present;
    Utils util;
    SharedPreferences sp;
    View view;

    ArrayList<HotNewsBean> data;
    HotNewsAdapter adapter;
    @Bind(R.id.hot_news_list)
    RecyclerViewWithEmpty hot_news_list;

    @Bind(R.id.empty_iv)
    ImageView empty_iv;
    @Bind(R.id.empty_tv)
    TextView empty_tv;
    @Bind(R.id.empty_lay)
    RelativeLayout empty_lay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(FragmentMain.this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        initView();
    }

    @JavascriptInterface
    private void initView() {

        adapter = new HotNewsAdapter(data, getContext());
        hot_news_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, hot_news_list);
        hot_news_list.setEmptyView(empty_lay);
        hot_news_list.setAdapter(animatorAdapter);

        setEmptyStatus(false);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void Back() {
        super.Back();
    }

    @Override
    public void refreshState() {
        super.refreshState();
    }

    private void initDate() {
        util = Utils.getInstance();
        present = QueryPresent.getInstance(getContext());
        present.setView(FragmentMain.this);
        sp = getContext().getSharedPreferences(COOKIE_KEY, Context.MODE_PRIVATE);

        HotNewsBean hotNewsBean = new HotNewsBean();
        hotNewsBean.setAuthor_avatar("苹果日报 ");
        hotNewsBean.setAuthor_name("苹果日报 ");
        hotNewsBean.setComment_count(200L);
        hotNewsBean.setEssay_content("苹果日报 ");
        hotNewsBean.setEssay_create_time(System.currentTimeMillis());
        hotNewsBean.setEmotion_count(300l);

        hotNewsBean.setAuthor_avatar("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=java.lang.IllegalStateException%3A%20Can%20not%20perform%20this%20action%20after%20onSaveIn&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=960265520,2932106354&os=2193161758,1915593990&simid=4232287102,954387401&pn=9&rn=1&di=182032548940&ln=1981&fr=&fmq=1508495608622_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fwww.livehacking.com%2Fweb%2Fwp-content%2Fuploads%2F2012%2F12%2Fjava-square.png&rpstart=0&rpnum=0&adpicid=0");
        hotNewsBean.setEssay_source("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=java.lang.IllegalStateException%3A%20Can%20not%20perform%20this%20action%20after%20onSaveIn&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=960265520,2932106354&os=2193161758,1915593990&simid=4232287102,954387401&pn=9&rn=1&di=182032548940&ln=1981&fr=&fmq=1508495608622_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fwww.livehacking.com%2Fweb%2Fwp-content%2Fuploads%2F2012%2F12%2Fjava-square.png&rpstart=0&rpnum=0&adpicid=0");

        hotNewsBean.setEssay_tags(new ArrayList<>());
        hotNewsBean.setEssay_extra("6张照片");
        hotNewsBean.setEssay_type_extra(0);
        hotNewsBean.setLook_count(4000l);
        hotNewsBean.setShare_count(4000l);
        hotNewsBean.save(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                KLog.v("SAVE SUCCESS");
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }

    protected void setEmptyStatus(boolean isOffLine) {

        if (isOffLine) {
            empty_iv.setImageResource(R.drawable.netword_error);
            empty_tv.setText("(=^_^=)，粗错了，点我刷新试试~");
            empty_lay.setEnabled(true);
            RxView.clicks(empty_iv).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> emptyClick());
        } else {
            empty_lay.setEnabled(false);
            empty_iv.setImageResource(R.drawable.smile);
            empty_tv.setText("暂时没有文章");
        }
    }

    protected void emptyClick() {
        showWaitDialog("正在努力加载...");
        fetchFromNetWork();
    }

    private void fetchFromNetWork() {

    }


}
