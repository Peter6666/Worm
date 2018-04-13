package com.logic.worm.db.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.logic.worm.db.helper.NewsHelper;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/12
 */

public class NewsItemDao {

    private  Dao mNewsItemDao = null;

    public NewsItemDao(Context ctx) throws SQLException {
        if (mNewsItemDao == null)
            mNewsItemDao = NewsHelper.getHelper(ctx).getDao(NewsHelper.NEWS_ITEM);

    }

    public  int create(Object var1) throws SQLException {
        return mNewsItemDao.create(var1);
    }

    public  int createAll(List var1) throws SQLException {
        int size = var1.size();
        if (size<=0)
            return -1;

        for (int i=0;i<size;i++){
            mNewsItemDao.create(var1.get(i));
        }
        return 0;
    }

    public  List query(String query) throws SQLException {
        QueryBuilder builder = mNewsItemDao.queryBuilder();
        return builder.where().eq("title", query).query();
    }

    public long countOf() throws SQLException {
        return mNewsItemDao.countOf();
    }

}
