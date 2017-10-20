package net.easyyy.countrynews;


import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jakewharton.rxbinding2.view.RxView;
import com.socks.library.KLog;

import net.easyyy.countrynews.acticity.BaseActivity;
import net.easyyy.countrynews.fragment.FragmentLogin;
import net.easyyy.countrynews.fragment.FragmentMain;
import net.easyyy.countrynews.present.QueryPresent;
import net.easyyy.countrynews.util.Constant;
import net.easyyy.countrynews.util.Utils;
import net.easyyy.countrynews.util.VToast;
import net.easyyy.countrynews.view.IFragmentActivity;
import net.easyyy.countrynews.view.IView;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateStatus;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class MainActivity extends BaseActivity implements IFragmentActivity,IView {
    public static String RECEIVE_REDIRECT_MESSAGE = "receive_redirect_message";
    protected static final String SUCCESS = "200";
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private final String COOKIE_KEY = "cookie";


    @Bind(R.id.main_mine_cv)
    ImageView main_mine_cv;
    @Bind(R.id.main_hot_cv)
    ImageView main_hot_cv;
    @Bind(R.id.main_local_cv)
    ImageView main_local_cv;

    @Bind(R.id.main_hot_tv)
    TextView main_hot_tv;
    @Bind(R.id.main_local_tv)
    TextView main_local_tv;
    @Bind(R.id.main_mine_tv)
    TextView main_mine_tv;

    @Bind(R.id.main_mine_lay)
    LinearLayout main_mine_lay;
    @Bind(R.id.main_local_lay)
    LinearLayout main_local_lay;
    @Bind(R.id.main_hot_lay)
    LinearLayout main_hot_lay;

    @Bind(R.id.main_bottom_lay)
    LinearLayout main_bottom_lay;

    @Bind(R.id.main_header_lay)
    RelativeLayout main_header_lay;

    SharedPreferences sp;
    QueryPresent present;
    Utils util;
    private static final String ALIAS_KEY = "alias";
    private String SetAlias = "setAlias";
    private static final int MSG_SET_ALIAS = 1001;

    BroadcastReceiver receiver_redirect = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            KLog.d("onReceive - " + intent.getAction());
            if (RECEIVE_REDIRECT_MESSAGE.equals(intent.getAction())) {

            }
        }
    };

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    KLog.v("Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    KLog.i("Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    KLog.i(logs);
                    sp.edit().putBoolean(SetAlias, true).commit();
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    KLog.i(logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    KLog.e(logs);
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fore_lay);
        ButterKnife.bind(MainActivity.this);
        initDate();
        initView();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver_redirect);

    }

    private void initDate() {
        Constant.temp_info = new HashMap<>();
        present = QueryPresent.getInstance(context);
        present.setView(MainActivity.this);
        executor = Executors.newSingleThreadScheduledExecutor();
        util = Utils.getInstance();
        sp = getSharedPreferences(COOKIE_KEY, 0);



        IntentFilter filter = new IntentFilter();
        filter.addAction(RECEIVE_REDIRECT_MESSAGE);
        registerReceiver(receiver_redirect, filter);

        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(MainActivity.this);
        // builder.statusBarDrawable = R.drawable.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        //    builder.notificationDefaults = Notification.;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(6, builder);


        BmobUpdateAgent.setUpdateListener((updateStatus, updateInfo) -> {
            if (updateStatus == UpdateStatus.Yes) {//版本有更新

            } else if (updateStatus == UpdateStatus.No) {
                KLog.v("版本无更新");
                if (Constant.MESSAGE_UPDATE_TIP.equals("")) {
                    Constant.MESSAGE_UPDATE_TIP = "<<商通天下>>已是最新版本!";
                } else {
                    VToast.toast(context, Constant.MESSAGE_UPDATE_TIP);
                }
            } else if (updateStatus == UpdateStatus.EmptyField) {//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
                KLog.v("请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。");
            } else if (updateStatus == UpdateStatus.IGNORED) {
                KLog.v("该版本已被忽略更新");
            } else if (updateStatus == UpdateStatus.ErrorSizeFormat) {
                KLog.v("请检查target_size填写的格式，请使用file.length()方法获取apk大小。");
            } else if (updateStatus == UpdateStatus.TimeOut) {
                KLog.v("查询出错或查询超时");
            }
        });
       // BmobUpdateAgent.initAppVersion(context);
        BmobUpdateAgent.update(MainActivity.this);
        startLocation();

    }
    private void initView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        fragment1 = new FragmentLogin();
        cur_page = 1;
        cur_fragment = fragment1;
        ft.show(fragment0);

        main_header_lay.setVisibility(View.VISIBLE);
        main_bottom_lay.setVisibility(View.VISIBLE);

        RxView.clicks(main_mine_lay).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> gotoMine());
        RxView.clicks(main_hot_lay).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> gotoMain());
        RxView.clicks(main_local_lay).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> gotoLocal());
    }

    private void gotoLocal() {

    }

    private void gotoMine() {

    }

    private void gotoPage(int pageId) {
        if (pageId == cur_page) {
            return;
        }
        cur_page = pageId;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(cur_fragment);

        switch(pageId){
            case 0:
                if (fragment0 == null) {
                    fragment0 = new FragmentMain();
                }
                if (!fragment0.isAdded()) {
                    ft.add(R.id.fragment_container, fragment0, PAGE_0);
                } else {
                    fragment0.refreshState();
                }
                cur_fragment = fragment0;
                ft.show(fragment0);
                main_header_lay.setVisibility(View.VISIBLE);
                main_bottom_lay.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (fragment1 == null) {
                    fragment1 = new FragmentLogin();
                }
                if (!fragment1.isAdded()) {
                    ft.add(R.id.fragment_container, fragment1, PAGE_1);
                } else {
                    fragment1.refreshState();
                }
                cur_fragment = fragment1;
                ft.show(fragment1);
                main_header_lay.setVisibility(View.GONE);
                main_bottom_lay.setVisibility(View.GONE);
                break;

        }

        ft.commitNowAllowingStateLoss();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(9000000);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        option.setLocationNotify(false);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(true);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private void startLocation() {

        mLocationClient = new LocationClient(getApplicationContext());
        initLocation();
        mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                KLog.v("onReceiveLocation" + location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                KLog.v("onReceiveLocation" + location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                KLog.v("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                KLog.v("定位失败，不能使用排序功能");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                KLog.v("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            } else {
                KLog.v("无法获取有效定位依据导致定位失败");
            }
            Constant.location = location;
            sendBroadcast(new Intent(Constant.RECEIVE_LOCATION_SUCCESS));
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            KLog.v(s);
        }
    }

    private void selectTab(int seq) {
        main_mine_cv.setImageDrawable(getResources().getDrawable(R.drawable.mine));
        main_hot_cv.setImageDrawable(getResources().getDrawable(R.drawable.hot));
        main_local_cv.setImageDrawable(getResources().getDrawable(R.drawable.loc));

        main_mine_tv.setTextColor(getResources().getColor(R.color.shangtongtianx_tab_txt));
        main_local_tv.setTextColor(getResources().getColor(R.color.shangtongtianx_tab_txt));
        main_mine_tv.setTextColor(getResources().getColor(R.color.shangtongtianx_tab_txt));
        switch (seq) {
            case 0:
                main_hot_cv.setImageDrawable(getResources().getDrawable(R.drawable.hot_s));
                main_hot_tv.setTextColor(getResources().getColor(R.color.shangtongtianx_txt));
                break;
            case 1:
                main_local_cv.setImageDrawable(getResources().getDrawable(R.drawable.loc_s));
                main_local_tv.setTextColor(getResources().getColor(R.color.shangtongtianx_txt));
                break;
            case 2:
                main_mine_cv.setImageDrawable(getResources().getDrawable(R.drawable.mine_s));
                main_mine_tv.setTextColor(getResources().getColor(R.color.shangtongtianx_txt));
                break;
        }
    }

    @Override
    public void gotoMain() {
        if (cur_fragment instanceof FragmentLogin) {
            if (!sp.getBoolean(SetAlias, false)) {
                KLog.v("MSG_SET_ALIAS");
                mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, Constant.user_info.opt(Constant.CODE)));//设置推送别名
            }

        }
        gotoPage(0);
        selectTab(0);
    }

    @Override
    public void gotoLogin() {
        gotoPage(0);
    }


    @Override
    public void gotoRegister() {
        gotoPage(1);
    }

    @Override
    public void gotoForget() {
        gotoPage(3);
    }

    @Override
    public void gotoFeedBack() {

    }

    @Override
    public void gotoProfile() {

    }


}
