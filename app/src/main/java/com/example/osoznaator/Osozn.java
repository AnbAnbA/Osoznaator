package com.example.osoznaator;
import android.os.Parcel;
import android.os.Parcelable;

public class Osozn implements Parcelable {
    public Integer ID;
    public String Cel;
    public String DRazum;
    public String Prostr;
    public int Ocenkaemotion;
    public String Emotion;
    public String Doi;
    public String Think;
    public String Time;

    protected Osozn(Parcel in) {
        ID = in.readInt();
        Cel = in.readString();
        DRazum = in.readString();
        Prostr = in.readString();
        Ocenkaemotion = in.readInt();
        Emotion = in.readString();
        Doi = in.readString();
        Think = in.readString();
        Time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Cel);
        dest.writeString(DRazum);
        dest.writeString(Prostr);
        dest.writeInt(Ocenkaemotion);
        dest.writeString(Emotion);
        dest.writeString(Doi);
        dest.writeString(Think);
        dest.writeString(Time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Osozn> CREATOR = new Creator<Osozn>() {
        @Override
        public Osozn createFromParcel(Parcel in) {
            return new Osozn(in);
        }

        @Override
        public Osozn[] newArray(int size) {
            return new Osozn[size];
        }
    };

    public void setID(Integer ID) {
        ID = ID;
    }

    public void setCel(String cel) {
        Cel = cel;
    }

    public void setDRazum(String dRazum) {
        DRazum = dRazum;
    }

    public void setProstr(String prostr) {
        Prostr = prostr;
    }

    public void setOcenkaemotion(int ocenkaemotion) {
        Ocenkaemotion = ocenkaemotion;
    }

    public void setEmotion(String emotion) {
        Emotion = emotion;
    }

    public void setDoi(String doi) {Doi = doi;}

    public void setThink(String think) {Think = think;}

    public void setTime(String time) {Time = time;}


    public int getID() {
        return ID;
    }

    public String getCel() {
        return Cel;
    }

    public String getDRazum() {
        return DRazum;
    }

    public String getProstr() {
        return Prostr;
    }

    public int getOcenkaemotion() {
        return Ocenkaemotion;
    }

    public String getEmotion() {
        return Emotion;
    }
    public String getDoi() {
        return Doi;
    }

    public String getThink() {
        return Think;
    }
    public String getTime() {
        return Time;
    }


    public Osozn(Integer ID, String cel, String dRazum, String prostr, int ocenkaemotion, String emotion, String doi, String think, String time) {
        ID = ID;
        Cel = cel;
        DRazum = dRazum;
        Prostr = prostr;
        Ocenkaemotion = ocenkaemotion;
        Emotion = emotion;
        Doi = doi;
        Think=think;
        Time=time;
    }
}
