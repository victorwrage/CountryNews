package net.easyyy.countrynews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
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
import net.easyyy.countrynews.adapter.HotNewsAdapter;
import net.easyyy.countrynews.adapter.HotNewsCommentAdapter;
import net.easyyy.countrynews.bean.HotNewsBean;
import net.easyyy.countrynews.bean.HotNewsCommentBean;
import net.easyyy.countrynews.bean._Article;
import net.easyyy.countrynews.customView.RecyclerViewWithEmpty;
import net.easyyy.countrynews.util.Constant;
import net.easyyy.countrynews.util.VToast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

/**
 * webview
 *
 * @author xiaoyl
 * @date 2013-07-20
 */
public class FragmentDetail extends BaseFragment{
    private String title = "";

    String url = "http://www.baidu.com";
    @Bind(R.id.tbsContent)
    TextView tbsContent;

    ArrayList<HotNewsCommentBean> data;
    HotNewsCommentAdapter adapter;

    @Bind(R.id.comment_lay)
    RecyclerViewWithEmpty comment_lay;

    @Bind(R.id.empty_iv2)
    ImageView empty_iv2;
    @Bind(R.id.empty_tv2)
    TextView empty_tv2;
    @Bind(R.id.empty_lay2)
    RelativeLayout empty_lay2;

    @Bind(R.id.empty_iv)
    ImageView empty_iv;
    @Bind(R.id.empty_tv)
    TextView empty_tv;
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
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(FragmentDetail.this, view);
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

        data = new ArrayList<>();
        adapter = new HotNewsCommentAdapter(data, getContext());
        comment_lay.setLayoutManager(new LinearLayoutManager(getActivity()));
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, comment_lay);
        comment_lay.setEmptyView(empty_lay);
        comment_lay.setAdapter(animatorAdapter);
        setEmptyStatus(false);
        adapter.notifyDataSetChanged();

        //查找Person表里面id为6b6c11c537的数据
        BmobQuery<_Article> bmobQuery = new BmobQuery<_Article>();
        bmobQuery.getObject(getContext(), Constant.ESSAY_DETAIL.getArticle(), new GetListener<_Article>() {
            @Override
            public void onSuccess(_Article article) {
                tbsContent.setText(Html.fromHtml(article.getContent()));
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });


    }

    private void Refresh() {

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
            empty_tv.setText("暂时没有评论");
        }
    }

    protected void emptyClick() {
        showWaitDialog("正在努力加载...");
        fetchFromNetWork();
    }

    private void fetchFromNetWork() {

        BmobQuery<HotNewsCommentBean> query = new BmobQuery<HotNewsCommentBean>();

        query.setLimit(10);

        query.findObjects(getContext(), new FindListener<HotNewsCommentBean>() {
            @Override
            public void onSuccess(List<HotNewsCommentBean> list) {
                data.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }



    public void Back() {
        super.Back();

    }

    @Override
    public void refreshState() {
        super.refreshState();
    }



}
