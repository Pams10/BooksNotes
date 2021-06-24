package it.pams.booksnotes;


import com.google.firebase.firestore.Exclude;


public class Book {

    private String id;
    private  String author;
    private String name;
    private String edition;
    private String price;
    private String owner;
    private String description;
    //private String contact;
    private String photo;
   public Book() {

    }

    public Book(String name ,String author,String description,String edition,String price,String photo,String owner,String id) {
        this.author = author;
        this.name = name;
        this.edition = edition;
        this.price = price;
        this.description = description;
        this.photo=photo;
         this.owner =owner;
         this.id=id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
    public void setDescription(String description) {
        this.description = description;
    }

   /* public String getContact() {
        return contact;
    }*/

   /* public void setContact(String contact) {
        this.contact = contact;
    }*/


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id=id;}




}
