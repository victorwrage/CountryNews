package net.easyyy.countrynews;


import com.baidu.mapapi.SDKInitializer;

import net.easyyy.countrynews.util.Constant;

import cat.ereza.customactivityoncrash.config.CaocConfig;
import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;


/**
 * @ClassName:	NFCApplication 
 * @Description:TODO(Application) 
 * @author:	xiaoyl
 * @date:	2013-7-10 下午4:01:27 
 *  
 */
public class ONApplication extends VApplication {
	private ONApplication instance;

	/** A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher. */
	public static final boolean ENCRYPTED = false;
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
		initCrash();
		SDKInitializer.initialize(getInstance());
		Bmob.initialize(getInstance(), Constant.PUBLIC_BMOB_KEY);

	//	MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, ENCRYPTED ? "history-db-encrypted" : "history-db",null);
	//	Database db = ENCRYPTED ? helper.getEncryptedWritableDb("shang_tong_tian_xia") : helper.getWritableDb();


	}



	private void initCrash() {
		CaocConfig.Builder.create()
				.backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
				.enabled(true) //default: true
				.showErrorDetails(true) //default: true
				.showRestartButton(true) //default: true
				.trackActivities(true) //default: false
				.minTimeBetweenCrashesMs(2000) //default: 3000
				.restartActivity(MainActivity.class) //default: null (your app's launch activity)
				.errorActivity(null) //default: null (default error activity)
				.eventListener(null) //default: null
				.apply();
	}

	public ONApplication getInstance() {
		if (instance == null) {
			instance = this;
		}
		return instance;
	}



}
