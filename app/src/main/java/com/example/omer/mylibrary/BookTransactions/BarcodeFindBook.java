package com.example.omer.mylibrary.BookTransactions;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.omer.mylibrary.BarcodeActivities.BarcodeAddActivity;
import com.example.omer.mylibrary.BarcodeActivities.BarcodeScanActivity;
import com.example.omer.mylibrary.GoogleBooks.FindBook;

public class BarcodeFindBook extends AsyncTask<String,Void,String> {

    Activity context;
    public BarcodeFindBook(Activity context) {
        this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        new FindBook(BarcodeScanActivity.getBarcodeNumber(),"").execute(BarcodeScanActivity.getBarcodeNumber());

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Intent intent=new Intent(context,BarcodeAddActivity.class);
        context.startActivity(intent);
        context.finish();
    }
}
