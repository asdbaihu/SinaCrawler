package UI;

import Blog.CrawlerThread;
import Blog.SinglePageThread;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ControlPanel extends JPanel implements ActionListener {
    public ArrayList<String> urlArray = new ArrayList<String>();
    public ShowPanel showPanel = null;
    public JTextArea jTextArea = new JTextArea();
    private BorderLayout borderLayout = new BorderLayout();
    private JTextField urlField = new JTextField();
    private JTextField nameField = new JTextField();
    private JTextField sensitiveWordsField = new JTextField();
    private JButton sensitiveButton = new JButton("建立敏感词库");
    private JButton urlButton = new JButton("使用url爬取");
    private JButton nameButton = new JButton("用户账号爬取");
    private JButton loadUrlButton = new JButton("加载本地url");
    private JButton startCrawler = new JButton("开始执行");

    public ControlPanel() {
        this.setLayout(borderLayout);
        this.setPreferredSize(new Dimension(300, 500));

        this.urlButton.addActionListener(this);
        this.nameButton.addActionListener(this);
        this.loadUrlButton.addActionListener(this);
        this.startCrawler.addActionListener(this);
        this.sensitiveButton.addActionListener(this);

//        this.setBackground(Color.BLUE);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(300, 180));
        BorderLayout layout = new BorderLayout();
        bottomPanel.setLayout(layout);
        JPanel bottomPanelLeft = new JPanel();
        GridLayout layoutLeft = new GridLayout(4, 1, 2, 2);
        bottomPanelLeft.setLayout(layoutLeft);
        bottomPanelLeft.setPreferredSize(new Dimension(200, 180));
        bottomPanelLeft.add(urlField);
        bottomPanelLeft.add(nameField);
        bottomPanelLeft.add(sensitiveWordsField);
        bottomPanelLeft.add(loadUrlButton);

        JPanel bottomPanelRight = new JPanel();
        GridLayout layoutRight = new GridLayout(4, 1, 2, 2);
        bottomPanelRight.setLayout(layoutRight);
        bottomPanelRight.add(urlButton);
        bottomPanelRight.add(nameButton);
        bottomPanelRight.add(sensitiveButton);
        bottomPanelRight.add(startCrawler);

        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);


        JScrollPane containSP = new JScrollPane(this.jTextArea);
        containSP.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        containSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jTextArea.setLineWrap(true);
        this.jTextArea.setEditable(false);
        this.add(containSP, BorderLayout.CENTER);


    }

    public void actionPerformed(ActionEvent e) {
        this.showPanel.indexBox.removeAllItems();
        this.showPanel.contentArray.clear();
        this.showPanel.jTextPane.setText("");

        if (e.getSource() == urlButton) {
            String url = this.urlField.getText();
            this.jTextArea.append("【爬虫开始】请耐心等待一大波数据到你碗里来...\n");
            this.urlField.setText("");
            ArrayList<String> arr = new ArrayList<String>();
            arr.add(url);
            SinglePageThread singlePageThread = new SinglePageThread();
            singlePageThread.urlArray = arr;
            singlePageThread.showPanel = this.showPanel;
            singlePageThread.controlPanel = this;
            singlePageThread.start();
        } else if (e.getSource() == nameButton) {
            String name = this.nameField.getText();
            this.nameField.setText("");
            this.jTextArea.append("【爬虫开始】请耐心等待一大波数据到你碗里来...\n");
            String url = "http://blog.sina.com.cn/s/articlelist_"+name+"_0_1.html";
            CrawlerThread crawlerThread = new CrawlerThread();
            crawlerThread.controlPanel = this;
            crawlerThread.showPanel = this.showPanel;
            crawlerThread.name = name;
            crawlerThread.url = url;
            crawlerThread.start();
        } else if (e.getSource() ==  loadUrlButton) {
            this.urlArray.clear();
            String fileName = "url";
            int count = 0;
            try {
                BufferedReader urlReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                String url = null;
                while((url = urlReader.readLine())!=null){
                    urlArray.add(url);
                    count++;
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            this.jTextArea.append("加载链接成功，共"+count+"条\n");
            System.out.println(this.urlArray);

        } else if (e.getSource() == startCrawler) {
            this.jTextArea.append("【爬虫开始】请耐心等待一大波数据到你碗里来...\n");
            SinglePageThread singlePageThread = new SinglePageThread();
            singlePageThread.urlArray = this.urlArray;
            singlePageThread.showPanel = this.showPanel;
            singlePageThread.controlPanel = this;
            singlePageThread.start();
        }else if(e.getSource() == sensitiveButton){
            String words = this.sensitiveWordsField.getText();
            String[] wordsArr = words.split(",");
            this.showPanel.patterns.clear();
            BufferedWriter bufferedWriter = null;
            try {
                File file = new File("words");
                bufferedWriter = new BufferedWriter(new FileWriter(file));
                file.createNewFile();
                for (String s:wordsArr){
                    Pattern p = Pattern.compile(s);
                    this.showPanel.patterns.add(p);
                    bufferedWriter.write(s+"\n");
                    bufferedWriter.flush();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }finally {
                try {
                    this.jTextArea.append("敏感词:"+words+" 敏感词库建立完成!\n");
                    bufferedWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
//1487828712_0_\d+\.html