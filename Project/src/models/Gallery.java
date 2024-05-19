package models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class Gallery {
    private int galleryId;
    private List<String> photoUrls;

    public Gallery() {}

    public Gallery(int galleryId) {
        this.galleryId = galleryId;
        this.photoUrls = new ArrayList<>();
    }

    public Gallery(int galleryId, List<String> photoUrls) {
        this.galleryId = galleryId;
        this.photoUrls = photoUrls;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public static Gallery fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Gallery.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "Id: " + galleryId +
                ", photoUrls: " + photoUrls +
                '}';
    }
}
