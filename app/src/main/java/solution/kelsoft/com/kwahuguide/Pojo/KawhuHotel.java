package solution.kelsoft.com.kwahuguide.Pojo;

/**
 * Created by Rukuvwe on 3/20/2016.
 */
public class KawhuHotel {
    int id;
    String name;
    String synopsis;
    String latitude;
    String longitude;
    String location;
    String address;
    String region;
    String features;
    String openinghour;
    String closinghour;
    String checkin;
    String checkout;
    private String icon;

    //Creating constructor with no argument kawhuHotel
    public KawhuHotel() {

    }

    //Creating constructor with argument of all variables above
    public KawhuHotel(int id,
                      String icon,
                      String name,
                      String synopsis,
                      String latitude,
                      String longitude,
                      String location,
                      String address,
                      String region,
                      String features,
                      String openinghour,
                      String closinghour,
                      String checkin,
                      String checkout) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.synopsis = synopsis;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.address = address;
        this.region = region;
        this.features = features;
        this.openinghour = openinghour;
        this.closinghour = closinghour;
        this.checkin = checkin;
        this.checkout = checkout;
    }

    @Override
    public String toString() {
        return "KawhuHotel{" +
                "id=" + id +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", location='" + location + '\'' +
                ", address='" + address + '\'' +
                ", region='" + region + '\'' +
                ", features='" + features + '\'' +
                ", openinghour='" + openinghour + '\'' +
                ", closinghour='" + closinghour + '\'' +
                ", checkin='" + checkin + '\'' +
                ", checkout='" + checkout + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getOpeninghour() {
        return openinghour;
    }

    public void setOpeninghour(String openinghour) {
        this.openinghour = openinghour;
    }

    public String getClosinghour() {
        return closinghour;
    }

    public void setClosinghour(String closinghour) {
        this.closinghour = closinghour;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

}
