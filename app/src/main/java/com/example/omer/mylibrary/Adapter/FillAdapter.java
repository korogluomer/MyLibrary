package com.example.omer.mylibrary.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omer.mylibrary.BookTransactions.MultiListing;
import com.example.omer.mylibrary.R;
import com.squareup.picasso.Picasso;

public class FillAdapter extends ArrayAdapter<String> {

    private final Activity context;
    TextView txtbookName;
    TextView txtauthorName;
    ImageView txtbookImage;

    //Her listview bulunan aktivity e Kitapları doldurur
    public FillAdapter(Activity context) {
        super(context, R.layout.booklist_fragment,MultiListing.getBookName());
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.booklist_fragment, null, true);

        txtbookName = view.findViewById(R.id.bookListName);
        txtauthorName = view.findViewById(R.id.bookListAuthor);
        txtbookImage = view.findViewById(R.id.bookListImage);

        txtbookName.setText(MultiListing.getAuthorName().get(position));
        txtauthorName.setText(MultiListing.getBookName().get(position));

        if (MultiListing.getBookImage().get(position) == null)
            Picasso.get().load(R.drawable.noimage).into(txtbookImage);
        else
            Picasso.get().load(MultiListing.getBookImage().get(position)).into(txtbookImage);

        return view;
    }
}
