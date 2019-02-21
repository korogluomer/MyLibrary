package com.example.omer.mylibrary.BookTransactions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiListing implements IBookList {

    private static ArrayList<String> bookId;
    private static ArrayList<String> bookName;
    private static ArrayList<String> authorName;
    private static ArrayList<String> bookPageNumber;
    FirebaseDatabase firebaseDatabase;
    private static ArrayList<String> bookNote;

    public static ArrayList<String> getBookId() {
        return bookId;
    }

    public static ArrayList<String> getBookName() {
        return bookName;
    }

    public static ArrayList<String> getAuthorName() {
        return authorName;
    }

    public static ArrayList<String> getBookPageNumber() {
        return bookPageNumber;
    }

    public static ArrayList<String> getBookNote() {
        return bookNote;
    }

    public static ArrayList<String> getBuyLocation() {
        return buyLocation;
    }

    public static ArrayList<String> getBuyDate() {
        return buyDate;
    }

    public static ArrayList<String> getBookImage() {
        return bookImage;
    }

    private static ArrayList<String> buyLocation;
    private static ArrayList<String> buyDate;
    private static ArrayList<String> bookImage;
    private String uId;

    public MultiListing(String uId) {
        firebaseDatabase = FirebaseDatabase.getInstance();

        bookId = new ArrayList<String>();
        bookName = new ArrayList<String>();
        authorName = new ArrayList<String>();
        bookPageNumber = new ArrayList<String>();
        bookNote = new ArrayList<String>();
        buyLocation = new ArrayList<String>();
        buyDate = new ArrayList<String>();
        bookImage = new ArrayList<String>();
        this.uId=uId;
    }


    //Kullanıcının tüm kitaplarını listeler ve dizilere atar
    @Override
    public void Listing() {
        bookName.clear();
        authorName.clear();
        bookImage.clear();
        bookNote.clear();
        buyDate.clear();
        buyLocation.clear();
        bookPageNumber.clear();
        bookId.clear();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("users")
                .child(uId)
                .child("books");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    try {
                        bookId.add(snapshot.getKey().toString());
                        HashMap<String, String> hashMap = (HashMap<String, String>) snapshot.getValue();//verileri key-value olarak hashleyip alındı
                        bookName.add(hashMap.get("bookName"));
                        authorName.add(hashMap.get("authorName"));
                        bookImage.add(hashMap.get("bookImage"));
                        bookNote.add(hashMap.get("bookNote"));
                        buyDate.add(hashMap.get("buyDate"));
                        buyLocation.add(hashMap.get("buyLocation"));
                        bookPageNumber.add(hashMap.get("bookPageNumber"));
                    } catch (Exception e) {
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
