package com.example.omer.mylibrary.UserTransactions;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.omer.mylibrary.ListingActivities.UserListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserFind extends AsyncTask<String, Void, String> {

    private static ArrayList<String> users = new ArrayList<>(), usersId = new ArrayList<>();
    private static String username;
    private static Activity context;
    int sayac=0;

    public UserFind(String username, Activity context) {
        this.username = username;
        this.context = context;
    }

    public static ArrayList<String> getUsers() {
        return users;
    }

    public static ArrayList<String> getUsersId() {
        return usersId;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        getUsers().clear();
        getUsersId().clear();
        Query usersQuery = FirebaseDatabase.getInstance().getReference("users");

        usersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Query usernameQuery = FirebaseDatabase.getInstance().getReference("users").child(snapshot.getKey()).child("username");
                    usernameQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue().toString().contains(username)) {
                                users.add(dataSnapshot.getValue().toString());
                                usersId.add(snapshot.getKey());

                                //Kullanıcılar arasında arama bitti
                                if (sayac==dataSnapshot.getChildrenCount()){
                                    Intent intent = new Intent(context.getApplicationContext(), UserListActivity.class);
                                    context.startActivity(intent);
                                }
                                sayac++;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


    }
}
