package dao;

import com.google.gson.Gson;
import models.Gallery;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GalleryDao implements DaoInterface<Gallery> {
    private static GalleryDao galleryDao;

    private final Connection connection = DatabaseConnection.getConnection();

    public GalleryDao() throws SQLException {}

    public static GalleryDao getInstance() throws SQLException {
        if(galleryDao == null) {
            galleryDao = new GalleryDao();
        }
        return galleryDao;
    }

    @Override
    public void add(Gallery gallery) throws SQLException {
        String sql = "INSERT INTO gallery (galleryId, photo_urls) VALUES (?, ?);";
        Gson gson = new Gson();
        String jsonPhotoUrls = gson.toJson(gallery.getPhotoUrls());

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gallery.getGalleryId());
            statement.setString(2, jsonPhotoUrls);
            statement.executeUpdate();
        }
    }

    @Override
    public Gallery read(int galleryId) throws SQLException {
        String sql = "SELECT * FROM gallery WHERE galleryId = ?";
        ResultSet rs = null;
        Gallery gallery = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, galleryId);
            rs = statement.executeQuery();

            if (rs.next()) {
                gallery = new Gallery();
                gallery.setGalleryId(galleryId);
                Gson gson = new Gson();
                List<String> photoUrls = gson.fromJson(rs.getString("photo_urls"), List.class);
                gallery.setPhotoUrls(photoUrls);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return gallery;
    }

    @Override
    public void delete(Gallery gallery) throws SQLException {
        String sql = "DELETE FROM gallery WHERE galleryId = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gallery.getGalleryId());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Gallery gallery) throws SQLException {
        String sql = "UPDATE gallery SET photo_urls = ? WHERE galleryId = ?";
        Gson gson = new Gson();
        String jsonPhotoUrls = gson.toJson(gallery.getPhotoUrls());

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, jsonPhotoUrls);
            statement.setInt(2, gallery.getGalleryId());
            statement.executeUpdate();
        }
    }

    public Gallery readLastGallery() throws SQLException {
        String sql = "SELECT * FROM gallery ORDER BY galleryId DESC LIMIT 1";
        ResultSet rs = null;
        Gallery gallery = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();

            if (rs.next()) {
                gallery = new Gallery();
                gallery.setGalleryId(rs.getInt("galleryId"));
                Gson gson = new Gson();
                List<String> photoUrls = gson.fromJson(rs.getString("photo_urls"), List.class);
                gallery.setPhotoUrls(photoUrls);
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return gallery;
    }
}
