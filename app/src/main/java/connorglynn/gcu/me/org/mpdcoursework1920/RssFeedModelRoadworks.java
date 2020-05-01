package connorglynn.gcu.me.org.mpdcoursework1920;
// connor Glynn S1626555
import java.util.Date;

public class RssFeedModelRoadworks {

    public String title;
    public String description;
    public Float Lat;
    public Float Long;
    public Date startDate;
    public Date endDate;


    public RssFeedModelRoadworks() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String xmldescription){ this.description = xmldescription;}

    public Float getLat() {
        return Lat;
    }

    public void setLat(Float lat) {
        Lat = lat;
    }

    public Float getLong() {
        return Long;
    }

    public void setLong(Float aLong) {
        Long = aLong;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }






}
