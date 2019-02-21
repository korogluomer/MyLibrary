package com.example.omer.mylibrary.GoogleBooks;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omer.mylibrary.GoogleBooks.NetworkConnection;
import com.example.omer.mylibrary.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FindBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> bookName;
    private WeakReference<TextView> authorName;
    private WeakReference<TextView> pageNumber;
    private ImageView bookImage;
    private static String title = null;
    private static String authors = null;
    private static String page = null;
    private static String images = null;
    boolean result = false;


    public static String getTitle() {
        return title;
    }

    public static String getAuthors() {
        return authors;
    }

    public static String getPage() {
        return page;
    }

    public static String getImages() {
        return images;
    }


    public FindBook(TextView bookName, TextView authorName, TextView pageNumber, ImageView bookImage) {
        this.bookName = new WeakReference<>(bookName);
        this.authorName = new WeakReference<>(authorName);
        this.bookImage = (bookImage);
        this.pageNumber = new WeakReference<>(pageNumber);
        result = false;
    }

    public FindBook(String title, String authors) {
        this.title = title;
        this.authors = authors;
        result = true;
    }


    @Override
    protected String doInBackground(String... strings) {
        return NetworkConnection.getBookInfo(strings[0]);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //result false ise object lere verileri direk çeker true ise sadece değer atar
        if (!result) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                JSONObject imageInfo = null;
                JSONObject book = itemsArray.getJSONObject(0);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    System.out.println(title);
                } catch (Exception e) {
                    title = null;
                }
                try {
                    authors = volumeInfo.getString("authors");

                } catch (Exception e) {
                    authors = null;
                }
                try {
                    page = volumeInfo.getString("pageCount");

                } catch (Exception e) {
                    page = null;
                }
                try {
                    imageInfo = volumeInfo.getJSONObject("imageLinks");
                    images = imageInfo.getString("thumbnail");
                    images = "https" + images.substring(4, images.length());

                } catch (Exception e) {
                    images = null;
                }
                if (title != null) {
                    if (authors == null)
                        authorName.get().setText("Sonuç yok");
                    if (page == null)
                        pageNumber.get().setText("Sonuç yok");
                    if (images != null)
                        Picasso.get().load(images).into(bookImage);
                    else
                        bookImage.setImageResource(R.drawable.book_default);

                } else {
                    bookName.get().setText("Sonuç yok");
                }

            } catch (JSONException e) {
                authorName.get().setText("Sonuç yok");
                bookImage.setImageResource(R.drawable.book_default);
                e.printStackTrace();
            }
        }
        else{

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                JSONObject imageInfo = null;
                JSONObject book = itemsArray.getJSONObject(0);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                } catch (Exception e) {
                    title = null;
                }
                try {
                    authors = volumeInfo.getString("authors");

                } catch (Exception e) {
                    authors = null;
                }
                try {
                    page = volumeInfo.getString("pageCount");

                } catch (Exception e) {
                    page = null;
                }
                try {
                    imageInfo = volumeInfo.getJSONObject("imageLinks");
                    images = imageInfo.getString("thumbnail");
                    images = "https" + images.substring(4, images.length());

                } catch (Exception e) {
                    images = null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}

