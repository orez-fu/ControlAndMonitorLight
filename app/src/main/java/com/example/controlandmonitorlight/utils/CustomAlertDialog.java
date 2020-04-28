package com.example.controlandmonitorlight.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.controlandmonitorlight.R;

public class CustomAlertDialog {
    public static final String SUCCESS_DIALOG = "SUCCESS_DIALOG";
    public static final String WARNING_DIALOG = "WARNING_DIALOG";
    public static final String ERROR_DIALOG = "ERROR_DIALOG";

    public static void showAlertDialog(Context context, String type, String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(context).inflate(
                type.equals(SUCCESS_DIALOG) ? R.layout.dialog_success : type.equals(WARNING_DIALOG) ? R.layout.dialog_warning : R.layout.dialog_error, null);
        builder.setView(view);
        ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_content)).setText(content);

        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
