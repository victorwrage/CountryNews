package net.easyyy.countrynews.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.socks.library.KLog;

import net.easyyy.countrynews.R;
import net.easyyy.countrynews.bean.UserBean;
import net.easyyy.countrynews.present.QueryPresent;
import net.easyyy.countrynews.util.Constant;
import net.easyyy.countrynews.util.Utils;
import net.easyyy.countrynews.util.VToast;
import net.easyyy.countrynews.view.IView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FragmentRegister extends BaseFragment implements IView {

    @Bind(R.id.register_user_et)
    EditText register_user_et;
    @Bind(R.id.register_password_et)
    EditText register_password_et;
    @Bind(R.id.register_invite_et)
    EditText register_invite_et;
    @Bind(R.id.register_code_et)
    EditText register_code_et;
    @Bind(R.id.register_nickname_et)
    EditText register_nickname_et;
    @Bind(R.id.register_email_et)
    EditText register_email_et;

    @Bind(R.id.register_register_btn)
    Button register_register_btn;
    @Bind(R.id.register_step1_btn)
    Button register_step1_btn;
    @Bind(R.id.register_step2_btn)
    Button register_step2_btn;
    @Bind(R.id.register_agree_btn)
    CheckBox register_agree_btn;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.register_tip_tv)
    TextView register_tip_tv;
    @Bind(R.id.register_code_again)
    TextView register_code_again;

    @Bind(R.id.register_protocol)
    TextView register_protocol;
    @Bind(R.id.register_show_iv)
    CheckBox register_show_iv;
    @Bind(R.id.header_btn_lay)
    LinearLayout header_btn_lay;
    @Bind(R.id.register_step1_lay)
    LinearLayout register_step1_lay;
    @Bind(R.id.register_step2_lay)
    LinearLayout register_step2_lay;
    @Bind(R.id.register_step3_lay)
    LinearLayout register_step3_lay;

    SharedPreferences sp;
    QueryPresent present;
    Utils util;
    String vertify, phone, password, vcode, referee;
    private Disposable disposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(FragmentRegister.this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        initView();
    }

    public void Back() {
        super.Back();
        if (register_step1_lay.getVisibility() == View.VISIBLE) {
            listener.gotoLogin();
        } else if (register_step3_lay.getVisibility() == View.VISIBLE) {
            register_step2_lay.setVisibility(View.VISIBLE);
            register_step3_lay.setVisibility(View.GONE);
        } else {
            register_step1_lay.setVisibility(View.VISIBLE);
            register_step2_lay.setVisibility(View.GONE);
        }
    }

    @Override
    public void refreshState() {
        super.refreshState();

        header_title.setText("注册");
        register_step1_lay.setVisibility(View.VISIBLE);
        register_step2_lay.setVisibility(View.GONE);
        register_step3_lay.setVisibility(View.GONE);
        register_user_et.setText("");
        register_invite_et.setText("");
        register_nickname_et.setText("");
        register_show_iv.setChecked(false);
        register_password_et.setText("");
        register_code_et.setText("");
        register_agree_btn.setChecked(true);
        referee = null;
        password = null;
        phone = null;
        vertify = null;
        vcode = null;
    }

    private void initView() {

        RxView.clicks(register_register_btn).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Forward());
        RxView.clicks(register_step1_btn).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Forward());
        RxView.clicks(register_step2_btn).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Forward());
        RxView.clicks(register_code_again).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Code());
        RxView.clicks(register_protocol).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Protocol());

        RxView.clicks(header_btn_lay).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Back());
        register_show_iv.setOnCheckedChangeListener((compoundButton, b) -> {
            visiblePw(b);
        });
        refreshState();
    }

    private void Protocol() {
        // Constant.InDirectTo = 100;
        // listener.gotoWebview();
    }


    private void Code() {
        showWaitDialog("获取验证码中");
        register_code_again.setEnabled(false);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendTime = format.format(new Date());
//        BmobSMS.requestSMS(getContext(), register_user_et.getText().toString().trim(), "审核通过后的短信内容", null, new cn.bmob.newsmssdk.listener.RequestSMSCodeListener() {
//            @Override
//            public void done(Integer integer, cn.bmob.newsmssdk.exception.BmobException e) {
//                if (e == null) {
//                    register_step2_lay.setVisibility(View.VISIBLE);
//                    register_step1_lay.setVisibility(View.GONE);
//                    register_tip_tv.setText("验证码已发送至您的手机:" + util.getPhoneEncrypt(phone));
//
//                    vertify = integer + "";
//                    timer();
//                    header_title.setText("验证手机号码");
//                    KLog.v("短信发送成功");
//                } else {
//                    KLog.v("短信发送失败");
//                }
//            }
//        });
        // present.initRetrofit(Constant.URL_SHANGTONGTIANXIA, false);
        // present.QueryCode(util.UrlEnco(Constant.WDT_SECRET), "1", phone);
    }

    private void Forward() {
        if (!util.isNetworkConnected(getContext())) {
            VToast.toast(getContext(), "没有网络连接");
            return;
        }
        if (register_step1_lay.getVisibility() == View.VISIBLE) {
            if (!util.verifyPhone(register_user_et.getText().toString().trim())) {
                VToast.toast(getContext(), "请输入正确的手机号");
                return;
            }
            if (register_password_et.getText().toString().trim().equals("")) {
                VToast.toast(getContext(), "请输入密码");
                return;
            }
            if (!util.isEmail(register_email_et.getText().toString().trim())) {
                VToast.toast(getContext(), "请输入正确的邮箱");
                return;
            }

           /* if (!util.verifyPhone(register_invite_et.getText().toString().trim())) {
                VToast.toast(getContext(), "请输入正确的推广人手机号码");
                return;
            }*/
            referee = register_invite_et.getText().toString().trim();
            password = util.getMD5(register_password_et.getText().toString().trim());
            phone = register_user_et.getText().toString().trim();
            if (vertify == null) {
                Submit();
               // Code();
            } else {
                register_step1_lay.setVisibility(View.GONE);
                register_step2_lay.setVisibility(View.VISIBLE);
            }

        } else if (register_step2_lay.getVisibility() == View.VISIBLE) {
            if (register_code_et.getText().toString().trim().equals("")) {
                VToast.toast(getContext(), "请输入验证码");
                return;
            }
            vcode = register_code_et.getText().toString().trim();
            register_step2_lay.setVisibility(View.GONE);
            register_step3_lay.setVisibility(View.VISIBLE);
        } else {
            Submit();
        }
    }

    private void Submit() {
        if (register_nickname_et.getText().toString().trim().equals("")) {
            VToast.toast(getContext(), "请输入昵称");
            return;
        }
        showWaitDialog("正在注册");
        UserBean bu = new UserBean();
        bu.setUsername(register_user_et.getText().toString().trim());
        bu.setPassword(util.getMD5(register_password_et.getText().toString().trim()));
        bu.setEmail(register_email_et.getText().toString().trim());
        bu.setNickname(register_nickname_et.getText().toString().trim());

        bu.signUp(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                VToast.toast(getContext(), "注册成功，请使用此账号登录");
                listener.gotoLogin();
                hideWaitDialog();
            }

            @Override
            public void onFailure(int i, String s) {
                VToast.toast(getContext(), "注册失败，" + s);
                hideWaitDialog();
            }
        });

    }

    private void visiblePw(Boolean b) {
        if (b) {
            register_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            register_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        register_password_et.postInvalidate();
        CharSequence charSequence = register_password_et.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    private void initDate() {
        sp = getContext().getSharedPreferences(COOKIE_KEY, 0);
        present = QueryPresent.getInstance(getContext());
        present.initRetrofit(Constant.URL_SHANGTONGTIANXIA, false);
        present.setView(FragmentRegister.this);
        util = Utils.getInstance();

        //BmobSMS.initialize(getContext(), Constant.PUBLIC_BMOB_KEY, new MySMSCodeListener());

    }

    /**
     * 计时器
     **/
    private void timer() {
        int limit = Constant.DEFAULT_MESSAGE_TIMEOUT;
        Disposable temp_dis;
        temp_dis = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(limit + 1)
                .map(s -> limit - s.intValue())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> complete())
                .subscribe(s -> next(s));

        if (disposable != null) {
            disposable.dispose();
        }
        disposable = temp_dis;
    }

    private void complete() {

        register_code_again.setEnabled(true);
        register_code_again.setText("重新获取");

    }

    private void next(int s) {
        if (s >= 10) {
            register_code_again.setText("(" + s + ")\n重新获取");
        } else {
            register_code_again.setText("(0" + s + ")\n重新获取");
        }
    }


//    class MySMSCodeListener implements SMSCodeListener {
//
//        @Override
//        public void onReceive(String content) {
//            if (register_code_et != null) {
//                register_code_et.setText(content);
//            }
//        }
//
//    }
}
