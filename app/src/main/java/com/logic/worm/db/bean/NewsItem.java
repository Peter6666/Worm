package com.logic.worm.db.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author logic.    Email:2778500267@qq.com
 * @data 2018/4/11
 */
@DatabaseTable(tableName = "tb_news_item")
public class NewsItem {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "title",useGetSet = true, canBeNull = false, unique = true)//表示当前属性在表中代表哪个字段,是否使用Getter/Setter方法来访问这个字段,字段是否可以为空，默认值是true,字段是否唯一
    private String title;
    @DatabaseField(columnName = "desc")
    private String desc;
    @DatabaseField(columnName = "ly")//来源
    private String ly;
    @DatabaseField(columnName = "img_url")
    private String img_url;
    @DatabaseField(columnName = "url")
    private String url;
    @DatabaseField(columnName = "type")//0为带图
    private String type;

    @DatabaseField(columnName = "time")
    private String time;

    public NewsItem() {
    }

    /**
     * 带图的
     * @param title
     * @param desc
     * @param ly
     * @param img_url
     * @param type
     */
    public NewsItem( String title, String desc, String ly, String img_url, String url,String type) {
        this.title = title;
        this.desc = desc;
        this.ly = ly;
        this.img_url = img_url;
        this.img_url = img_url;
        this.url = url;
        this.type = type;
    }

    /**
     * 不带图的
     * @param title
     * @param desc
     * @param type
     * @param time
     */
    public NewsItem(String title, String desc, String type, String time, String url) {
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.url = url;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly;
    }

    public String getImgUrl() {
        return img_url;
    }

    public void setImgUrl(String img) {
        this.img_url = img;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
