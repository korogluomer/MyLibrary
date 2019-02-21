package com.example.omer.mylibrary.BookTransactions;

import android.support.annotation.NonNull;

import com.example.omer.mylibrary.GoogleBooks.FindBook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BookFind implements IBookFind {
    private static String bookName;
    private static String buyLocation;
    private static String bookNote;
    private static String buyDate;
    private static String bookId;

    public static String getBookName() {
        return bookName;
    }

    public static String getBuyLocation() {
        return buyLocation;
    }

    public static String getBookNote() {
        return bookNote;
    }

    public static String getBuyDate() {
        return buyDate;
    }

    public static String getBookId() {
        return bookId;
    }


    //Kullanıcı id ve kitap id si verilen kitabı listeler
    @Override
    public void Finding(String bookId,String uId) {
        this.bookId=bookId;
        Query bookListQuery = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(uId)
                .child("books")
                .child(bookId);

        bookListQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookName = dataSnapshot.child("bookName").getValue().toString();
                buyLocation = dataSnapshot.child("buyLocation").getValue().toString();
                buyDate = dataSnapshot.child("buyDate").getValue().toString();
                bookNote = dataSnapshot.child("bookNote").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
