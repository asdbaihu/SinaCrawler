package Blog;

import UI.ShowPanel;
import modal.SinglePage;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class UIPipeline implements Pipeline {
    public ShowPanel showPanel = null;
    public void process(ResultItems resultItems, Task task) {
        SinglePage singlePage = (SinglePage)resultItems.get("page");
        showPanel.indexBox.addItem(singlePage.getTitle());
        showPanel.contentArray.add(singlePage);



    }
}
