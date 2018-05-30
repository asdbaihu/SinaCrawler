package Blog;

import UI.ControlPanel;
import UI.ShowPanel;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

public class CrawlerThread extends Thread {
    public ControlPanel controlPanel = null;
    public ShowPanel showPanel = null;
    public String name = null;
    public String url = null;
    public CrawlerThread(){

    }
    public void run(){
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        BlogPageProcess blogPageProcess = new BlogPageProcess();
        blogPageProcess.URL_LIST = "http://blog\\.sina\\.com\\.cn/s/articlelist_"+name+"_0_\\d+\\.html";
        System.out.println(blogPageProcess.URL_LIST);
        UIPipeline uiPipeline = new UIPipeline();
        uiPipeline.showPanel = this.showPanel;
        Spider.create(blogPageProcess).addPipeline(uiPipeline).addPipeline(new ConsolePipeline()).addUrl(url).thread(5).run();
        endTime = System.currentTimeMillis();
        String info = "【爬虫结束】共抓取" + blogPageProcess.size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，请查收！\n";
        this.controlPanel.jTextArea.append(info);
        if(this.showPanel.contentArray.size()>0){
            this.showPanel.jTextPane.setText(this.showPanel.contentArray.get(0).getContent());
        }
    }
}
