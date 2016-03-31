package solution.kelsoft.com.kwahuguide.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rukuvwe on 3/14/2016.
 */
public class KawhuAttraction implements Parcelable {
    public static final Creator<KawhuAttraction> CREATOR = new Creator<KawhuAttraction>() {
        @Override
        public KawhuAttraction createFromParcel(Parcel in) {
            return new KawhuAttraction(in);
        }

        @Override
        public KawhuAttraction[] newArray(int size) {
            return new KawhuAttraction[size];
        }
    };
    private int id;
    private String icon;
    private String name;
    private String synopsis;
    private String address;
    private String location;
    private String latitude;
    private String longitude;
    private String details;

    public KawhuAttraction(Parcel in) {
        id = in.readInt();
        icon = in.readString();
        name = in.readString();
        synopsis = in.readString();
        address = in.readString();
        location = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        details = in.readString();
    }

    public KawhuAttraction() {

    }



    @Override
    public String toString() {
        return "KawhuAttraction{" +
                "id=" + id +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", details='" + details + '\'' +
                '}';
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeString(synopsis);
        dest.writeString(address);
        dest.writeString(location);
        dest.writeString(latitude);
        dest.writeString(details);
    }
}
