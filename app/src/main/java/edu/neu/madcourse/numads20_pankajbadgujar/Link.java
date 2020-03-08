package edu.neu.madcourse.numads20_pankajbadgujar;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "links")
public class Link {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "linkId")
    private int id;

    private String linkName;

    private String linkURL;


    public Link(String linkName, String linkURL) {
        this.linkName = linkName;
        this.linkURL = linkURL;
    }

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

}
