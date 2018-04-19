# Worm
Android-jsoup 使用jsoup爬取网站数据
jsoup 简介
首先我们应该了接下什么是jsoup，jsoup 是一款Java 的HTML解析器，可直接解析某个URL地址、HTML文本内容。它提供了一套非常省力的API，可通过DOM，CSS以及类似于jQuery的操作方法来取出和操作数据（以上内容纯属百度百科copy的）。下面我们开始爬取网页的数据，第一次写，写的不好还请大家见谅。

下载jsoup Jar包

首先去官网下载最新的jar，我使用的是1.11的版本，下载好后倒入IDE中。下载地址https://jsoup.org/download

爬取数据

这次爬取的是某网的数据（本次爬取仅为测试，不得用于商业用途，用于商业用途与本人无关）

在浏览器中查看页面的源码，找到我们要爬的数据代码

整个条目的网页代码如下
<div class="pictxt02 clearfix">            
                <h3>  
                    <a href="http://tech.ifeng.com/a/20180411/44944998_0.shtml" target="_blank" title="关直播、关评论！ 抖音短视频全面整改">关直播、关评论！ 抖音短视频全面整改</a>  
                </h3>     
                <a href="http://tech.ifeng.com/a/20180411/44944998_0.shtml" target="_blank" title="关直播、关评论！ 抖音短视频全面整改">  
                    <img src="http://d.ifengimg.com/w188_h106/p0.ifengimg.com/pmop/2018/0411/82E68A0DCC7D44FA7336751F9BBE07B4BC9BEA01_size41_w600_h333.jpeg" width="188" height="106" title="关直播、关评论！ 抖音短视频全面整改" alt="关直播、关评论！ 抖音短视频全面整改">  
                </a>  
                <p>原标题：关直播！关评论！ 抖音短视频全面整改4月11号午间消息，抖音方面表示，即日起，为更好地向用户提供服务，抖音将对系统进行全面升级，期间直播功能与评论功能暂</p>  
                <div class="intr01">  
                    <div class="ly">  
                        <span class="ly">来源：前瞻网 </span><span class="zy"></span>  
                    </div>  
                    <div class="pl">  
                        <a href="http://comment.ifeng.com/view.php?docName=%E5%85%B3%E7%9B%B4%E6%92%AD%E3%80%81%E5%85%B3%E8%AF%84%E8%AE%BA%EF%BC%81%20%E6%8A%96%E9%9F%B3%E7%9F%AD%E8%A7%86%E9%A2%91%E5%85%A8%E9%9D%A2%E6%95%B4%E6%94%B9&docUrl=http://tech.ifeng.com/a/20180411/44944998_0.shtml&skey=28fad5" target="_blank" title="评论">0</a>  
                    </div>  
                </div>  
                </div>  
                
1.根据条目的class来定位我们要抓取的数据

[html] view plain copy
Elements elementsByClass = doc.getElementsByClass("pictxt02 clearfix");  
当前的Elements是包涵全部的条目的，所以需要去遍历每个条目

2.在遍历的循环中，在选取我们要提取的数据

Elements h3 = list.getElementsByTag("h3");//通过h3标签获取到Element对象
String url= h3.select("a").first().attr("href");//再通过h3对象获取到a标签中的页面链接

String img = list.getElementsByTag("a").select("img").first().attr("src");//获取图片链接
Elements p = list.getElementsByTag("p");//获取简介
String text = p.text();

Elements ly = list.getElementsByClass("ly");//获取来源
String ly = ly.get(0).text();

通过以上代码就能抓取当前也的数据了
