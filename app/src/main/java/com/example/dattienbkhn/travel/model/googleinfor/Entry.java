
package com.example.dattienbkhn.travel.model.googleinfor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Entry {

    @SerializedName("xmlns")
    @Expose
    private String xmlns;
    @SerializedName("xmlns$gphoto")
    @Expose
    private String xmlnsgphoto;
    @SerializedName("id")
    @Expose
    private Id id;
    @SerializedName("published")
    @Expose
    private Published published;
    @SerializedName("updated")
    @Expose
    private Updated updated;
    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("summary")
    @Expose
    private Summary summary;
    @SerializedName("link")
    @Expose
    private List<Link> link = null;
    @SerializedName("author")
    @Expose
    private List<Author> author = null;
    @SerializedName("gphoto$user")
    @Expose
    private GphotoUser gphotouser;
    @SerializedName("gphoto$nickname")
    @Expose
    private GphotoNickname gphotonickname;
    @SerializedName("gphoto$thumbnail")
    @Expose
    private GphotoThumbnail gphotothumbnail;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getXmlnsgphoto() {
        return xmlnsgphoto;
    }

    public void setXmlnsgphoto(String xmlns$gphoto) {
        this.xmlnsgphoto = xmlns$gphoto;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Published getPublished() {
        return published;
    }

    public void setPublished(Published published) {
        this.published = published;
    }

    public Updated getUpdated() {
        return updated;
    }

    public void setUpdated(Updated updated) {
        this.updated = updated;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<Link> getLink() {
        return link;
    }

    public void setLink(List<Link> link) {
        this.link = link;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public GphotoUser getGphotouser() {
        return gphotouser;
    }

    public void setGphotouser(GphotoUser gphoto$user) {
        this.gphotouser = gphoto$user;
    }

    public GphotoNickname getGphotonickname() {
        return gphotonickname;
    }

    public void setGphotonickname(GphotoNickname gphoto$nickname) {
        this.gphotonickname = gphoto$nickname;
    }

    public GphotoThumbnail getGphotothumbnail() {
        return gphotothumbnail;
    }

    public void setGphotothumbnail(GphotoThumbnail gphoto$thumbnail) {
        this.gphotothumbnail = gphoto$thumbnail;
    }

}
