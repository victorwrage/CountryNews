package net.easyyy.countrynews.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import net.easyyy.countrynews.R;
import net.easyyy.countrynews.present.QueryPresent;
import net.easyyy.countrynews.util.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentMine extends BaseFragment {
    private static final String COOKIE_KEY = "cookie";
    private static final String ALIAS_KEY = "alias";


    @Bind(R.id.header_btn_lay)
    LinearLayout header_btn_lay;
    @Bind(R.id.header_setting_lay)
    LinearLayout header_setting_lay;

    @Bind(R.id.header_title)
    TextView header_title;

    @Bind(R.id.user_profile_lay)
    RelativeLayout user_profile_lay;

    @Bind(R.id.user_add_lay)
    RelativeLayout user_add_lay;

    @Bind(R.id.manager_nickname_tv)
    TextView manager_nickname_tv;
    @Bind(R.id.manager_level_tv)
    TextView manager_level_tv;

    SharedPreferences sp;
    QueryPresent present;
    Utils util;

    private String AwardCount = "-1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_lay, container, false);
        ButterKnife.bind(FragmentMine.this, view);
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
        present.setView(FragmentMine.this);
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
