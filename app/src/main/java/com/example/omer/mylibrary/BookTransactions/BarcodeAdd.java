package com.example.omer.mylibrary.BookTransactions;

import android.app.Activity;
import android.widget.Toast;

import com.example.omer.mylibrary.BarcodeActivities.BarcodeAddActivity;
import com.example.omer.mylibrary.GoogleBooks.FindBook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class BarcodeAdd implements IBookAdd {

    private DatabaseReference userid;
    private DatabaseReference bid;
    private DatabaseReference bookname;
    private DatabaseReference buydate;
    private DatabaseReference buylocation;
    private DatabaseReference booknote;
    private DatabaseReference bookImage;
    private DatabaseReference authorName;
    private DatabaseReference bookPageNumber;
    private FirebaseDatabase database;
    private Activity context;

    public BarcodeAdd(FirebaseDatabase database,Activity context) {
        this.database = database;
        this.context=context;
    }

    @Override
    public void Add() {

        UUID uuid = UUID.randomUUID();
        String stringUUID = uuid.toString();

        userid = database.getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("books");
        bid = userid.child(stringUUID);
        bookname = bid.child("bookName");
        bookImage = bid.child("bookImage");
        authorName = bid.child("authorName");
        bookPageNumber = bid.child("bookPageNumber");
        buydate = bid.child("buyDate");
        buylocation = bid.child("buyLocation");
        booknote = bid.child("bookNote");

        bid.setValue(stringUUID);
        bookname.setValue(FindBook.getTitle());
        buydate.setValue(BarcodeAddActivity.getTxtDate());
        buylocation.setValue(BarcodeAddActivity.getTxtLocation());
        booknote.setValue(BarcodeAddActivity.getTxtNote());
        if (FindBook.getAuthors()!=null)
            authorName.setValue(FindBook.getAuthors().substring(2,FindBook.getAuthors().length()-2));
        else
            authorName.setValue("Sonuç Yok");
        bookPageNumber.setValue(FindBook.getPage());
        bookImage.setValue(FindBook.getImages());

        Toast.makeText(context.getApplicationContext(), "Book Added", Toast.LENGTH_LONG).show();
    }
}
