package Blog;

import UI.ControlPanel;
import UI.ShowPanel;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

import java.io.File;

import java.util.ArrayList;

public class SinglePageThread extends Thread {
    public ArrayList<String> urlArray = new ArrayList<String>();
    public ControlPanel controlPanel = null;
    public ShowPanel showPanel = null;
    public void run(){
        long startTime, endTime;
        UIPipeline uiPipeline = new UIPipeline();
        uiPipeline.showPanel = this.showPanel;
        startTime = System.currentTimeMillis();
        for (String url:urlArray){
            Spider.create(new SingleUrlPageProcessor()).addPipeline(uiPipeline).addPipeline(new ConsolePipeline()).addUrl(url).thread(1).start();
        }
        endTime = System.currentTimeMillis();
        String info = "【爬虫结束】共抓取" + urlArray.size() + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，请查收！\n";
        this.controlPanel.jTextArea.append(info);

    }
}
