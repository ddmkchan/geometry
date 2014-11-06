package you.ctrip.entity;


/**
 * 主游记实体
 * 
 * @author mai
 * 
 */
public class Travelnote {
    public Integer travelContentId;
    public Integer travelId;
    public String title;
    public String content;
    public String publishDate;

    public Integer getTravelContentId() {
        return travelContentId;
    }

    public void setTravelContentId(Integer travelContentId) {
        this.travelContentId = travelContentId;
    }

    public Integer getTravelId() {
        return travelId;
    }

    public void setTravelId(Integer travelId) {
        this.travelId = travelId;
    }

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

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
