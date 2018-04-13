package com.logic.worm.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.logic.worm.db.bean.NewsItem;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/12
 */

public class NewsHelper extends OrmLiteSqliteOpenHelper {

    public static final Class NEWS_ITEM = NewsItem.class;

    private static final String TABLE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;
    private Map<String, Dao> daos = new HashMap<String, Dao>();
    private static NewsHelper instance;
    private Dao<NewsItem, Integer> dao=null;

    public static synchronized NewsHelper getHelper(Context ctx) {
        if (instance == null) {
            synchronized (NewsHelper.class) {
                if (instance == null)
                    instance = new NewsHelper(ctx, TABLE_NAME, null, DATABASE_VERSION);
            }
        }
        return instance;
    }

    public NewsHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, NewsItem.class);//创建表
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        try {
            TableUtils.dropTable(connectionSource, NewsItem.class, true);//删除表。连接源，class，是否忽视错误
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<NewsItem, Integer> getNewsDao(Class clazz) throws SQLException {

        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className))
        {
            dao = daos.get(className);
        }
        if (dao == null)
        {
            dao = getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet())
        {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
