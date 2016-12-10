package com.boxfish.lsw.calendarview;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.boxfish.lishaowei.DaoMaster;
import com.boxfish.lishaowei.DaoSession;

/**
 * Created by lishaowei on 16/12/10
 */
public class BaseApplication extends Application {

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setUpDataBase();
    }

    private void setUpDataBase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        helper = new DaoMaster.DevOpenHelper(this, "note", null);
        database = helper.getWritableDatabase();
        daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return database;
    }
}
