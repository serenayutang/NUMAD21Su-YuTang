package neu.madcourse.numad21su_yutang;

public class LinkCard implements LinkClickListener{
    private  String name;
    private  String url;

    public LinkCard(String linkName, String linkUrl) {
        this.name = linkName;
        this.url = linkUrl;
    }

    public String getLinkName() {
        return name;
    }

    public String getLinkUrl() {
        return url;
    }

    @Override
    public void onItemClick(String url) {

    }
}
