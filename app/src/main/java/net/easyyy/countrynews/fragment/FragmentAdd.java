package net.easyyy.countrynews.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import net.easyyy.countrynews.R;
import net.easyyy.countrynews.bean.HotNewsBean;
import net.easyyy.countrynews.customView.FlowImageLayout;
import net.easyyy.countrynews.present.QueryPresent;
import net.easyyy.countrynews.util.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentAdd extends BaseFragment {


    @Bind(R.id.header_btn_lay)
    LinearLayout header_btn_lay;
    @Bind(R.id.header_setting_lay)
    LinearLayout header_setting_lay;

    @Bind(R.id.header_setting_iv)
    ImageView header_setting_iv;

    @Bind(R.id.header_title)
    TextView header_title;

    @Bind(R.id.add_author_avatar)
    CircleImageView add_author_avatar;

    @Bind(R.id.add_author_name)
    TextView add_author_name;
    @Bind(R.id.add_location)
    TextView add_location;
    @Bind(R.id.add_authority_sp)
    Spinner add_authority_sp;

    @Bind(R.id.add_feeling_et)
    EditText add_feeling_et;

    @Bind(R.id.add_img_fil)
    FlowImageLayout add_img_fil;
    String[] imag_urls;
    @Bind(R.id.add_show_more)
    LinearLayout add_show_more;

    SharedPreferences sp;
    QueryPresent present;
    Utils util;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_news, container, false);
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
        RxView.clicks(header_setting_lay).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Submit());
        RxView.clicks(add_show_more).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> ShowMore());

        header_setting_lay.setVisibility(View.VISIBLE);
        header_title.setText("更新状态");
        header_setting_iv.setImageResource(R.drawable.submit);

    }

    private void ShowMore() {
        imag_urls = new String[]{"",""};
    }

    private void Submit() {
        HotNewsBean hotNewsBean = new HotNewsBean();
        hotNewsBean.setAuthor_name(add_author_name.getText().toString());
        hotNewsBean.setEssay_images(imag_urls);
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
