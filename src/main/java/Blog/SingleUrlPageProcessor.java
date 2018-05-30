package Blog;

import modal.SinglePage;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class SingleUrlPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setUserAgent(
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    public void process(Page page) {
        Html html = page.getHtml();
        SinglePage singlePage = new SinglePage();
        String title = html.xpath("//div[@class='articalTitle']/h2/text()").replace("&nbsp;","").toString();
        String content = html.xpath("//div[@id='articlebody']//div[@class='articalContent']/text()").replace("\\s+","").replace("=","").toString();
        title = title.trim();
        content = content.trim();

        if(title.length() == 0 || content.length() <= 30){
            return;
        }
        System.out.println(content);
        singlePage.setTitle(title);
        singlePage.setContent(content);

        page.putField("page",singlePage);
    }

    public Site getSite() {
        return site;
    }
}
