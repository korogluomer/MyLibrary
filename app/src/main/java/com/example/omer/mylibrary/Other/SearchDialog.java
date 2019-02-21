package com.example.omer.mylibrary.Other;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.omer.mylibrary.R;

public class SearchDialog extends AppCompatDialogFragment {
    private EditText txtUsername;
    private ExampleDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.searchdialog_fragment,null);

        builder.setView(view)
                .setTitle("Search Profile")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username=txtUsername.getText().toString();
                        listener.applyText(username);

                    }
                });

        txtUsername=view.findViewById(R.id.txtSearchUsername);

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener=(ExampleDialogListener)context;
    }
}
