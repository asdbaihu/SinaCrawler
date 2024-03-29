package modal;

public class SinglePage {
    private String title;// 标题

    private String content;// 内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return "Blog [title=" + title + ", content=" + content+"]";
    }
}
