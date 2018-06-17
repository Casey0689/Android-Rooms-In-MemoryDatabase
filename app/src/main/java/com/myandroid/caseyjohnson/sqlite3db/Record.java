package com.myandroid.caseyjohnson.sqlite3db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.sql.Blob;
import java.util.Date;

@Entity(tableName = "records")
public class Record {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recordID")
    private long recordID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "price")
    private float price;

    @ColumnInfo(name = "rating")
    private int rating;

//    @ColumnInfo(name = "image")
//    private Blob image;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "dateModified")
    private Date dateModified;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "dateCreated")
    private Date dateCreated;


//-------------------------------- GETTERS AND SETTERS ------------------------------
    public long getRecordID() {
        return recordID;
    }

    public void setRecordID(long recordID) {
        this.recordID = recordID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

//    public Blob getImage() {
//        return image;
//    }
//
//    public void setImage(Blob image) {
//        this.image = image;
//    }
//
    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
