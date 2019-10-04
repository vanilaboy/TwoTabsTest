package org.bitart.twotabstest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//import org.apache.commons.lang.builder.EqualsBuilder;
//import org.apache.commons.lang.builder.HashCodeBuilder;
//import org.apache.commons.lang.builder.ToStringBuilder;

public class Kot3Animal implements Parcelable {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("title")
    @Expose
    private String title;

    public Kot3Animal(String url, String title) {
        super();
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    protected Kot3Animal(Parcel in) {
        url = in.readString();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Kot3Animal> CREATOR = new Parcelable.Creator<Kot3Animal>() {
        @Override
        public Kot3Animal createFromParcel(Parcel in) {
            return new Kot3Animal(in);
        }

        @Override
        public Kot3Animal[] newArray(int size) {
            return new Kot3Animal[size];
        }
    };
}
