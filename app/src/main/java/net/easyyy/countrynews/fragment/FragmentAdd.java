package net.easyyy.countrynews.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import net.easyyy.countrynews.R;
import net.easyyy.countrynews.present.QueryPresent;
import net.easyyy.countrynews.util.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentAdd extends BaseFragment {


    @Bind(R.id.header_btn_lay)
    LinearLayout header_btn_lay;
    @Bind(R.id.header_setting_lay)
    LinearLayout header_setting_lay;

    @Bind(R.id.header_title)
    TextView header_title;

    SharedPreferences sp;
    QueryPresent present;
    Utils util;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_lay, container, false);
        ButterKnife.bind(FragmentAdd.this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        initView();
    }

    private void initDate() {
        util = Utils.getInstance();
        sp = getContext().getSharedPreferences(COOKIE_KEY, Context.MODE_PRIVATE);
        present = QueryPresent.getInstance(getActivity());
        present.setView(FragmentAdd.this);
    }

    private void initView() {

        RxView.clicks(header_btn_lay).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> listener.gotoMain());

        header_setting_lay.setVisibility(View.VISIBLE);
        header_title.setText("个人中心");
       // manager_nickname_tv.setText(Constant.user_info != null ? Constant.user_info.optString("name") : "未注册");
     //   manager_level_tv.setText(Constant.user_info != null ? Constant.user_info.optString("level_name") : "游客");
    }


    @Override
    public void Back() {
        super.Back();
        listener.gotoMain();
    }

    @Override
    public void refreshState() {
        super.refreshState();
   //     manager_nickname_tv.setText(Constant.user_info != null ? Constant.user_info.optString("name") : "未注册");
   //     manager_level_tv.setText(Constant.user_info != null ? Constant.user_info.optString("level_name") : "游客");
    }

}
