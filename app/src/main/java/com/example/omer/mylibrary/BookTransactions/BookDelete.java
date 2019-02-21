package com.example.omer.mylibrary.BookTransactions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.example.omer.mylibrary.Adapter.FillAdapter;
import com.example.omer.mylibrary.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Math.toIntExact;

public class BookDelete implements IBookDelete {
    private int position = 0;
    private String[] arr;
    private Context context;
    private FillAdapter adapter;
    private ListView listView;

    public BookDelete(int position, Activity context, FillAdapter adapter, ListView listView) {
        this.position = position;
        this.context = context;
        this.adapter = adapter;
        this.listView = listView;
    }


    //Kitapların keylerini dizide toplar dönen positionu metota yollar
    @Override
    public void Delete() {

        Query bookQuery = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("books");

        bookQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sayac = 0;
                if (dataSnapshot.getChildrenCount() > 0) {
                    arr = new String[toIntExact(dataSnapshot.getChildrenCount())];
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        arr[sayac++] = snapshot.getKey();

                    bookList(arr[position]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //Kitap keylerine göre arama yapar ve siler
    public void bookList(String key) {
        Query bookListQuery = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("books")
                .child(key);

        bookListQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("MyLibrary");
                alertDialog.setMessage("Silmek istediğinize Emin misiniz?");
                alertDialog.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataSnapshot.getRef().setValue(null);
                        MultiListing.getBookImage().remove(position);
                        MultiListing.getBookName().remove(position);
                        MultiListing.getAuthorName().remove(position);
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);

                        Intent intent=new Intent(context.getApplicationContext(),MainActivity.class);
                        intent.putExtra("mind","delete");
                        context.startActivity(intent);
                    }
                });
                alertDialog.show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
