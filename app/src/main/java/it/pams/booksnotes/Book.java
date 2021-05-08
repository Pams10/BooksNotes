package it.pams.booksnotes;

import com.google.firebase.firestore.Exclude;

public class Book {
    @Exclude
    private Long id;
    private String author;
    private String name;
    private String edition;
    private String price;
    private String description;
    private String contact;
    private String photo;
   public Book() {

    }

    public Book(String name ,String author,String description,String edition,String price,String photo) {
        this.author = author;
        this.name = name;
        this.edition = edition;
        this.price = price;
        this.description = description;
        this.photo=photo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getEdition() {
        return edition;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setEdition(String  edition) {
        this.edition = edition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }




}
