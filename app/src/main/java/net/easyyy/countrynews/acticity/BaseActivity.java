package net.easyyy.countrynews.acticity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import net.easyyy.countrynews.ONApplication;
import net.easyyy.countrynews.R;
import net.easyyy.countrynews.customView.ProgressBarItem;
import net.easyyy.countrynews.fragment.BaseFragment;
import net.easyyy.countrynews.fragment.FragmentAdd;
import net.easyyy.countrynews.fragment.FragmentDetail;
import net.easyyy.countrynews.fragment.FragmentLogin;
import net.easyyy.countrynews.fragment.FragmentMain;
import net.easyyy.countrynews.fragment.FragmentRegister;
import net.easyyy.countrynews.util.DoubleConfirm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;


/**
 * @ClassName: BaseActivity
 * @Description:TODO(界面的基类)
 * @author: xiaoyl
 * @date: 2013-7-10 下午2:30:06
 */
public class BaseActivity extends FragmentActivity {

    protected Executor executor;

    protected FragmentMain fragment0;
    protected FragmentLogin fragment1;
    protected FragmentRegister fragment2;
    protected FragmentAdd fragment3;
    protected FragmentDetail fragment4;

    protected static final String PAGE_0 = "page_0";
    protected static final String PAGE_1 = "page_1";
    protected static final String PAGE_2 = "page_2";
    protected static final String PAGE_3 = "page_3";
    protected static final String PAGE_4 = "page_4";
    protected static final String PAGE_5 = "page_5";
    protected static final String PAGE_6 = "page_6";
    protected static final String PAGE_7 = "page_7";
    protected static final String PAGE_8 = "page_8";
    protected static final String PAGE_9 = "page_9";
    protected static final String PAGE_10 = "page_10";
    protected static final String PAGE_11 = "page_11";
    protected static final String PAGE_12 = "page_12";
    protected static final String PAGE_13 = "page_13";
    protected static final String PAGE_14 = "page_14";
    protected static final String PAGE_15 = "page_15";
    protected static final String PAGE_16 = "page_16";
    protected static final String PAGE_17 = "page_17";
    protected static final String PAGE_18 = "page_18";
    protected static final String PAGE_19 = "page_19";
    protected static final String PAGE_20 = "page_20";
    protected static final String PAGE_21 = "page_21";
    protected static final String PAGE_22 = "page_22";
    protected static final String PAGE_23 = "page_23";
    protected static final String PAGE_24 = "page_24";
    protected static final String PAGE_25 = "page_25";
    protected static final String PAGE_26 = "page_26";
    protected static final String PAGE_27 = "page_27";
    protected static final String PAGE_28 = "page_28";
    protected static final String PAGE_29 = "page_29";
    protected static final String PAGE_30 = "page_30";
    protected static final String PAGE_31 = "page_31";

    protected BaseFragment cur_fragment;

    private DoubleConfirm double_c;

    protected Context context;
    protected int cur_page = -1;

    /**
     * 双击事件
     */
    private DoubleConfirm.DoubleConfirmEvent doubleConfirmEvent = new DoubleConfirm.DoubleConfirmEvent() {
        public void doSecondConfirmEvent() {
            new ONApplication().getInstance().exitApplication();
        }

        public int getFirstConfirmTipsId() {
            return R.string.msg_exit;
        }
    };

    public String currentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    protected void showWaitDialog(String tip){
        ProgressBarItem.show(BaseActivity.this,tip,false,null);
    }
    protected void hideWaitDialog() {
        ProgressBarItem.hideProgress();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new ONApplication().getInstance().addActivitys(this);
        context = getApplicationContext();
        this.double_c = new DoubleConfirm();
        this.double_c.setEvent(this.doubleConfirmEvent);
    }


    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == KeyEvent.KEYCODE_BACK) {
            switch (cur_page) {
                case 0:
                    this.double_c.onKeyPressed(paramKeyEvent, this);
                    return true;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    cur_fragment.Back();
                    return true;
                default:
                    this.double_c.onKeyPressed(paramKeyEvent, this);
                    return true;
            }
        }
        return false;
    }

    protected void gotoMain() {

    }


}
