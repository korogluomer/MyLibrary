package com.example.omer.mylibrary.BarcodeActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.omer.mylibrary.BookTransactions.BarcodeFindBook;
import com.example.omer.mylibrary.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class BarcodeScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView zXingScannerView;

    public static String getBarcodeNumber() {
        return barcodeNumber;
    }

    public static void setBarcodeNumber(String barcodeNumber) {
        BarcodeScanActivity.barcodeNumber = barcodeNumber;
    }

    private static String barcodeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        setBarcodeNumber(result.getText().toString());
        BarcodeFindBook barcodeFindBook = new BarcodeFindBook(this);
        barcodeFindBook.execute("naber");
    }
}
