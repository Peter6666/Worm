package com.logic.worm;

import android.app.Activity;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.logic.worm.db.bean.NewsItem;
import com.logic.worm.db.dao.NewsItemDao;
import com.logic.worm.util.FileUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/14
 */

public class MainActivity extends Activity {

    private String url = "http://tech.ifeng.com/";
    private String nextUrl = "http://tech.ifeng.com/listpage/800/0/1/rtlist.shtml";
    private String TAG = "MainActivity";
    private NewsItemDao mNewsItemDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            mNewsItemDao = new NewsItemDao(getApplicationContext());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initView();
        nextUrl();
    }

    private  List<NewsItem> mDataList= new ArrayList<>();

    private void index() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Connection conn = Jsoup.connect(url);
                    Document doc = null;

                    doc = conn.get();
//                    String s = doc.html().toString();
//                    FileUtil.saveFile(s, "temp.html");

                    Elements elementsByClass = doc.getElementsByClass("pictxt02 clearfix");
                    int size = elementsByClass.size();

                    for (Element list : elementsByClass) {
                        String text = list.text();

                        Elements h3 = list.getElementsByTag("h3");
                        String textH3 = h3.text();
                        String val = h3.select("a").first().attr("href");


                        String img = list.getElementsByTag("a").select("img").first().attr("src");

                        Elements p = list.getElementsByTag("p");
                        String textP = p.text();

                        Elements ly = list.getElementsByClass("ly");
                        String textly = ly.get(0).text();
                        NewsItem newsItem = new NewsItem(textH3, textP, textly, img,val, "0");

                        List query = mNewsItemDao.query(newsItem.getTitle());
                        if (query.size() <= 0)
                            mDataList.add(newsItem);

                        Log.i(TAG, textH3 + "\n\t" + textP + "\n\t" + textly + val + "  \n\t****************");
                    }
                    Elements more = doc.getElementsByClass("more");
                    nextUrl = more.first().select("a").first().attr("href");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                saveData();

            }
        }).start();

    }

    public void saveData(){

        if (mDataList.size()>0){
            try {
                mNewsItemDao.createAll(mDataList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        mDataList.clear();
    }

    private void nextUrl(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                int err=0;
                while (true){

                    try {
                        Connection conn = Jsoup.connect(nextUrl);
                        Document doc = null;
                        doc = conn.get();
//                        String s = doc.html().toString();
//                        FileUtil.saveFile(s, "temp.html");

                        Elements elementsByClass = doc.getElementsByClass("zheng_list pl10 box");
                        int size = elementsByClass.size();

                        for (Element list : elementsByClass) {
                            Elements function = list.getElementsByClass("Function");
                            String time = function.text();

                            Elements t_css = list.getElementsByClass("t_css");
                            String href = t_css.attr("href");
                            String desc = t_css.text();

                            Elements zxbd_clearfix = list.getElementsByClass("zxbd clearfix");
                            String text = zxbd_clearfix.text();
                            NewsItem newsItem = new NewsItem(desc, text, "1", time, href);
                            List query = mNewsItemDao.query(desc);
                            if (query.size() <= 0)
                                mDataList.add(newsItem);
                        };

                        Elements r_end = doc.getElementsByClass("r_end");
                        nextUrl = r_end.select("a").first().attr("href");

                    } catch (Exception e) {
                        e.printStackTrace();
                        err++;
                        if (err>3){
                            saveData();
                            err=0;
                            return;
                        }
                    }

                    try {
                        if (mDataList.size()>=100)
                            saveData();
                        Thread.sleep(new Random(System.currentTimeMillis()).nextInt(4000)+1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void initView() {


    }

}
