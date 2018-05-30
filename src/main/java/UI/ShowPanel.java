package UI;

import modal.SinglePage;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowPanel extends JPanel implements ActionListener {
    public ControlPanel controlPanel = null;
    public ArrayList<SinglePage> contentArray = new ArrayList<SinglePage>();
    private Font font = new Font("标楷体", Font.PLAIN,16);
    public JComboBox indexBox = new JComboBox();
    public JTextPane jTextPane = new JTextPane();
    private BorderLayout borderLayout = new BorderLayout();
    private SimpleAttributeSet select = new SimpleAttributeSet();
    public ArrayList<Integer> patternCount = new ArrayList<Integer>();
    public ArrayList<Pattern> patterns = new ArrayList<Pattern>();
    private ArrayList<String> resultArray = new ArrayList<String>();
    private String finalString = null;
    public ShowPanel(){
        this.indexBox.addActionListener(this);
        this.setLayout(borderLayout);
        this.indexBox.setFont(font);
        this.add(indexBox, BorderLayout.NORTH);

        JScrollPane containSP = new JScrollPane(this.jTextPane);
        containSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.jTextPane.setEditable(false);
        this.add(containSP,BorderLayout.CENTER);

        this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("words")));
            String tempStr = null;
            while ((tempStr = bufferedReader.readLine())!=null){
                Pattern pattern = Pattern.compile(tempStr);
                this.patterns.add(pattern);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        StyleConstants.setForeground(select, Color.red);
        StyleConstants.setBackground(select, Color.yellow);


    }

    public void actionPerformed(ActionEvent e) {
        String title = (String)this.indexBox.getSelectedItem();
        SinglePage page =null;
        for (SinglePage singlePage : this.contentArray){
            if (singlePage.getTitle().equals(title)) {
                page = singlePage;
                this.jTextPane.setText(singlePage.getContent());
                break;
            }  // if
        }
        StyledDocument styledDocument =  this.jTextPane.getStyledDocument();
        this.patternCount.clear();
        this.resultArray.clear();
        if (patterns.size() == 0){
            return;
        }
        this.controlPanel.jTextArea.append(page.getTitle()+"具有敏感词:");
        this.finalString = null;
        this.finalString = page.getTitle()+"具有敏感词:";
        Integer tempCount = 0;
        for(Pattern p : this.patterns){
            Matcher m = p.matcher(this.jTextPane.getText());
            while(m.find()){
                try{
                    styledDocument.setCharacterAttributes(m.start(), m.end() - m.start(), select, false);
                    tempCount++;
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }  // while
            this.patternCount.add(tempCount);
            String result = p.toString()+String.valueOf(tempCount)+"个 ";
            finalString = finalString+p.toString()+String.valueOf(tempCount)+"个 ";
            this.controlPanel.jTextArea.append(result);
        }  // for

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("results"),true));
            bufferedWriter.write(finalString+"\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        this.controlPanel.jTextArea.append("\n");


    }
}
