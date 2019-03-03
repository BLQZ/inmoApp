package com.example.inmoapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Inmueble implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ownerId")
    @Expose
    private User ownerId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("rooms")
    @Expose
    private int rooms;
    @SerializedName("categoryId")
    @Expose
    private Category categoryId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("province")
    @Expose
    private String province;
    @SerializedName("loc")
    @Expose
    private String loc;
    @SerializedName("photos")
    @Expose
    private String photos[];
    @SerializedName("isFav")
    @Expose
    private boolean isFav;

    public Inmueble() {
    }

    public Inmueble(String id, User ownerId, String title, String description, double price, int rooms,
                    Category categoryId, String address, String zipcode, String city, String province, String loc) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.categoryId = categoryId;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.province = province;
        this.loc = loc;
    }

    public Inmueble(String id, User ownerId, String title, String description, double price, int rooms,
                    Category categoryId, String address, String zipcode, String city, String province,
                    String loc, String[] photos) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.categoryId = categoryId;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.province = province;
        this.loc = loc;
        this.photos = photos;
    }

    public Inmueble(String id, User ownerId, String title, String description, double price, int rooms,
                    Category categoryId, String address, String zipcode, String city, String province,
                    String loc, String[] photos, boolean isFav) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.categoryId = categoryId;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.province = province;
        this.loc = loc;
        this.photos = photos;
        this.isFav = isFav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(User ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inmueble inmueble = (Inmueble) o;

        if (Double.compare(inmueble.price, price) != 0) return false;
        if (rooms != inmueble.rooms) return false;
        if (id != null ? !id.equals(inmueble.id) : inmueble.id != null) return false;
        if (ownerId != null ? !ownerId.equals(inmueble.ownerId) : inmueble.ownerId != null)
            return false;
        if (title != null ? !title.equals(inmueble.title) : inmueble.title != null) return false;
        if (description != null ? !description.equals(inmueble.description) : inmueble.description != null)
            return false;
        if (categoryId != null ? !categoryId.equals(inmueble.categoryId) : inmueble.categoryId != null)
            return false;
        if (address != null ? !address.equals(inmueble.address) : inmueble.address != null)
            return false;
        if (zipcode != null ? !zipcode.equals(inmueble.zipcode) : inmueble.zipcode != null)
            return false;
        if (city != null ? !city.equals(inmueble.city) : inmueble.city != null) return false;
        if (province != null ? !province.equals(inmueble.province) : inmueble.province != null)
            return false;
        return loc != null ? loc.equals(inmueble.loc) : inmueble.loc == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + rooms;
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (loc != null ? loc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "id='" + id + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rooms=" + rooms +
                ", categoryId='" + categoryId + '\'' +
                ", adress='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
