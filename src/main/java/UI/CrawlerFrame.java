package UI;

import javax.swing.*;
import java.awt.*;

public class CrawlerFrame extends JFrame {
    private ControlPanel controlPanel = new ControlPanel();
    private ShowPanel showPanel = new ShowPanel();
    public CrawlerFrame(){

        this.setTitle("爬虫");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(controlPanel, BorderLayout.WEST);
        this.add(showPanel, BorderLayout.CENTER);

        this.controlPanel.showPanel = this.showPanel;
        this.showPanel.controlPanel = this.controlPanel;

        // 设置Frame属性
        this.setSize(800,500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // 添加两个Panel





    }
    public static void main(String[] argv){
        CrawlerFrame crawlerFrame = new CrawlerFrame();
    }
}
