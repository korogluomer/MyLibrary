package com.example.omer.mylibrary.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omer.mylibrary.BookTransactions.MultiListing;
import com.example.omer.mylibrary.R;
import com.squareup.picasso.Picasso;

@SuppressLint("ValidFragment")
public class BooksFillFragment extends Fragment {

    private int position = 0;

    @SuppressLint("ValidFragment")
    public BooksFillFragment(int position) {
        // Required empty public constructor
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.books_fragment, container, false);//fragmentleri doldur
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView bookName = view.findViewById(R.id.txtDenemeBookName);
        TextView authorName = view.findViewById(R.id.txtDenemeAuthorName);
        TextView bookCount = view.findViewById(R.id.txtBookCount);
        ImageView imgBook = view.findViewById(R.id.imgDenemeBook);
        Button btnShare = view.findViewById(R.id.btnDenemeShare);

        bookName.setText(MultiListing.getBookName().get(getPosition()));
        authorName.setText(MultiListing.getAuthorName().get(getPosition()));
        bookCount.setText((getPosition() + 1) + "/" + MultiListing.getBookName().size());
        if (MultiListing.getBookImage().get(getPosition()) == null)
            Picasso.get().load(R.drawable.noimage).into(imgBook);
        else
            Picasso.get().load(MultiListing.getBookImage().get(getPosition())).into(imgBook);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = MultiListing.getBookName().get(getPosition()) + " - " + MultiListing.getAuthorName().get(getPosition()) + " kitabına sahibim bence sen de Almalısın !!";
                String shareSubject = "Kitap Öneri";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);

                startActivity(Intent.createChooser(sharingIntent, "Sharing Using"));

            }
        });
    }

}
