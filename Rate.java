package com.example.multiTranslator;

public class Rate {
    public String Email;
    public int ratings;
    public String reviews;

    public Rate() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Rate(String Email, int ratings, String reviews) {
           this.Email=Email;
           this.ratings=ratings;
           this.reviews=reviews;

    }

}
