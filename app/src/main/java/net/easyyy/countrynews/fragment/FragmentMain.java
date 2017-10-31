package net.easyyy.countrynews.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import net.easyyy.countrynews.R;
import net.easyyy.countrynews.adapter.HotNewsAdapter;
import net.easyyy.countrynews.bean.HotNewsBean;
import net.easyyy.countrynews.customView.RecyclerViewWithEmpty;
import net.easyyy.countrynews.present.QueryPresent;
import net.easyyy.countrynews.util.Utils;
import net.easyyy.countrynews.util.VToast;
import net.easyyy.countrynews.view.IView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

public class FragmentMain extends BaseFragment implements IView,HotNewsAdapter.IHotNewsAdapter{

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

    @Bind(R.id.main_add_more)
    RelativeLayout main_add_more;

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


    private void initView() {

        VToast.toast(getContext(), "欢迎" + BmobUser.getObjectByKey(getContext(),"nickname"));

        RxView.clicks(main_add_more).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Add());

        data = new ArrayList<>();
        adapter = new HotNewsAdapter(data, getContext(),FragmentMain.this);
        hot_news_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, hot_news_list);
        hot_news_list.setEmptyView(empty_lay);
        hot_news_list.setAdapter(animatorAdapter);

        setEmptyStatus(false);
        adapter.notifyDataSetChanged();

        fetchFromNetWork();
    }

    private void Add() {
        listener.gotoAdd();
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
            empty_tv.setText("暂时没有内容");
        }
    }

    protected void emptyClick() {
        showWaitDialog("正在努力加载...");
        fetchFromNetWork();
    }

    private void fetchFromNetWork() {
        showWaitDialog("请稍等");
        BmobQuery<HotNewsBean> query = new BmobQuery<HotNewsBean>();

        query.setLimit(50);

        query.findObjects(getContext(), new FindListener<HotNewsBean>() {
            @Override
            public void onSuccess(List<HotNewsBean> list) {
                data.addAll(list);
                adapter.notifyDataSetChanged();
                hideWaitDialog();


            }

            @Override
            public void onError(int i, String s) {
                hideWaitDialog();
                VToast.toast(getContext(), "欢迎" + (String) BmobUser.getObjectByKey(getContext(),"username"));
            }
        });
    }

    @Override
    public void Detail() {
        listener.gotoDetail();
    }
}
