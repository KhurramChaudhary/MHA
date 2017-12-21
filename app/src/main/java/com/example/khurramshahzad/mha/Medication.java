package com.example.khurramshahzad.mha;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Khurramshahzad on 11/c/2017.
 */

public class Medication implements Parcelable {

    private Long id;
    private String sid;
    private String medicineName;
    private String recommendedBy;

    private boolean morning;
    private Double morningQuantity;
    private Long morningTime;
    private Integer morningCounter;

    private boolean afternoon;
    private Double afternoonQuantity;
    private Long afternoonTime;
    private Integer afternoonCounter;

    private boolean night;
    private Double nightQuantity;
    private Long nightTime;
    private Integer nightCounter;

    private String comment;
    private Integer missed;

    private String currentTime;

    public Medication() {
    }

    protected Medication(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        sid = in.readString();
        medicineName = in.readString();
        recommendedBy = in.readString();
        morning = in.readByte() != 0;
        if (in.readByte() == 0) {
            morningQuantity = null;
        } else {
            morningQuantity = in.readDouble();
        }
        if (in.readByte() == 0) {
            morningTime = null;
        } else {
            morningTime = in.readLong();
        }
        if (in.readByte() == 0) {
            morningCounter = null;
        } else {
            morningCounter = in.readInt();
        }
        afternoon = in.readByte() != 0;
        if (in.readByte() == 0) {
            afternoonQuantity = null;
        } else {
            afternoonQuantity = in.readDouble();
        }
        if (in.readByte() == 0) {
            afternoonTime = null;
        } else {
            afternoonTime = in.readLong();
        }
        if (in.readByte() == 0) {
            afternoonCounter = null;
        } else {
            afternoonCounter = in.readInt();
        }
        night = in.readByte() != 0;
        if (in.readByte() == 0) {
            nightQuantity = null;
        } else {
            nightQuantity = in.readDouble();
        }
        if (in.readByte() == 0) {
            nightTime = null;
        } else {
            nightTime = in.readLong();
        }
        if (in.readByte() == 0) {
            nightCounter = null;
        } else {
            nightCounter = in.readInt();
        }
        comment = in.readString();
        if (in.readByte() == 0) {
            missed = null;
        } else {
            missed = in.readInt();
        }
        currentTime = in.readString();
    }

    public static final Creator<Medication> CREATOR = new Creator<Medication>() {
        @Override
        public Medication createFromParcel(Parcel in) {
            return new Medication(in);
        }

        @Override
        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public Double getMorningQuantity() {
        return morningQuantity;
    }

    public void setMorningQuantity(Double morningQuantity) {
        this.morningQuantity = morningQuantity;
    }

    public Long getMorningTime() {
        return morningTime;
    }

    public void setMorningTime(Long morningTime) {
        this.morningTime = morningTime;
    }

    public Integer getMorningCounter() {
        return morningCounter;
    }

    public void setMorningCounter(Integer morningCounter) {
        this.morningCounter = morningCounter;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public Double getAfternoonQuantity() {
        return afternoonQuantity;
    }

    public void setAfternoonQuantity(Double afternoonQuantity) {
        this.afternoonQuantity = afternoonQuantity;
    }

    public Long getAfternoonTime() {
        return afternoonTime;
    }

    public void setAfternoonTime(Long afternoonTime) {
        this.afternoonTime = afternoonTime;
    }

    public Integer getAfternoonCounter() {
        return afternoonCounter;
    }

    public void setAfternoonCounter(Integer afternoonCounter) {
        this.afternoonCounter = afternoonCounter;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }

    public Double getNightQuantity() {
        return nightQuantity;
    }

    public void setNightQuantity(Double nightQuantity) {
        this.nightQuantity = nightQuantity;
    }

    public Long getNightTime() {
        return nightTime;
    }

    public void setNightTime(Long nightTime) {
        this.nightTime = nightTime;
    }

    public Integer getNightCounter() {
        return nightCounter;
    }

    public void setNightCounter(Integer nightCounter) {
        this.nightCounter = nightCounter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getMissed() {
        return missed;
    }

    public void setMissed(Integer missed) {
        this.missed = missed;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(sid);
        dest.writeString(medicineName);
        dest.writeString(recommendedBy);
        dest.writeByte((byte) (morning ? 1 : 0));
        if (morningQuantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(morningQuantity);
        }
        if (morningTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(morningTime);
        }
        if (morningCounter == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(morningCounter);
        }
        dest.writeByte((byte) (afternoon ? 1 : 0));
        if (afternoonQuantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(afternoonQuantity);
        }
        if (afternoonTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(afternoonTime);
        }
        if (afternoonCounter == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(afternoonCounter);
        }
        dest.writeByte((byte) (night ? 1 : 0));
        if (nightQuantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(nightQuantity);
        }
        if (nightTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(nightTime);
        }
        if (nightCounter == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(nightCounter);
        }
        dest.writeString(comment);
        if (missed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(missed);
        }
        dest.writeString(currentTime);
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", sid='" + sid + '\'' +
                ", medicineName='" + medicineName + '\'' +
                ", recommendedBy='" + recommendedBy + '\'' +
                ", morning=" + morning +
                ", morningQuantity=" + morningQuantity +
                ", morningTime=" + morningTime +
                ", morningCounter=" + morningCounter +
                ", afternoon=" + afternoon +
                ", afternoonQuantity=" + afternoonQuantity +
                ", afternoonTime=" + afternoonTime +
                ", afternoonCounter=" + afternoonCounter +
                ", night=" + night +
                ", nightQuantity=" + nightQuantity +
                ", nightTime=" + nightTime +
                ", nightCounter=" + nightCounter +
                ", comment='" + comment + '\'' +
                ", missed=" + missed +
                ", currentTime='" + currentTime + '\'' +
                '}';
    }
}