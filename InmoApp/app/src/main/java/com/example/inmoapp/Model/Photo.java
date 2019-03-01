package com.example.inmoapp.Model;

public class Photo {

    private String propertyId;
    private String imgurLink;
    private String deletehash;

    public Photo() {
    }

    public Photo(String propertyId, String imgurLink, String deletehash) {
        this.propertyId = propertyId;
        this.imgurLink = imgurLink;
        this.deletehash = deletehash;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getImgurLink() {
        return imgurLink;
    }

    public void setImgurLink(String imgurLink) {
        this.imgurLink = imgurLink;
    }

    public String getDeletehash() {
        return deletehash;
    }

    public void setDeletehash(String deletehash) {
        this.deletehash = deletehash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (propertyId != null ? !propertyId.equals(photo.propertyId) : photo.propertyId != null)
            return false;
        if (imgurLink != null ? !imgurLink.equals(photo.imgurLink) : photo.imgurLink != null)
            return false;
        return deletehash != null ? deletehash.equals(photo.deletehash) : photo.deletehash == null;
    }

    @Override
    public int hashCode() {
        int result = propertyId != null ? propertyId.hashCode() : 0;
        result = 31 * result + (imgurLink != null ? imgurLink.hashCode() : 0);
        result = 31 * result + (deletehash != null ? deletehash.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "propertyId='" + propertyId + '\'' +
                ", imgurLink='" + imgurLink + '\'' +
                ", deletehash='" + deletehash + '\'' +
                '}';
    }
}
