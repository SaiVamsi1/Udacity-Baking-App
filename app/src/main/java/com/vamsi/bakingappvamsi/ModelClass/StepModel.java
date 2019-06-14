package com.vamsi.bakingappvamsi.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StepModel implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer Id;
    @SerializedName("videoURL")
    @Expose
    private String VideoUrl;
    @SerializedName("shortDescription")
    @Expose
    private String ShortDescription;
    @SerializedName("description")
    @Expose
    private String Description;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
