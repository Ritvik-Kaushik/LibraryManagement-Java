package com.library;

public class BookValues {
    private String id;
    private String title;
    private String authorName;
    private String publishDate;
   private String availability;
public BookValues(){
    this.id="";
    this.title="";
    this.authorName="";
    this.publishDate="";
    this.availability="";

}
    public BookValues(String id, String title, String authorName, String publishDate, String availability) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.publishDate = publishDate;
        this.availability = availability;

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPublishDate() {
        return publishDate;
    }

  public String getAvailability(){
    return this.availability;
  }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }


}
