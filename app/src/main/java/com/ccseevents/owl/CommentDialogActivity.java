package com.ccseevents.owl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class CommentDialogActivity extends AppCompatDialogFragment {

    private EditText editTextName;
    private EditText editTextComment;
    private CommentDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_comment_dialog, null);

        builder.setView(view).setTitle("Comment").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = editTextName.getText().toString();
                String comment = editTextComment.getText().toString();

                listener.applyTexts(name, comment);
            }
        })
        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        editTextName = view.findViewById(R.id.editText_name);
        editTextComment = view.findViewById(R.id.editText_comment);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CommentDialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "must implement CommentDialogListener");
        }

    }

    public interface CommentDialogListener {
        void applyTexts(String name, String comment);
    }
}
