package com.example.controlandmonitorlight.view.view.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.Repository.DeviceClient;
import com.example.controlandmonitorlight.model.SharedRoomRequest;
import com.example.controlandmonitorlight.model.SharedRoomResponse;
import com.example.controlandmonitorlight.model.SimpleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.sanju.motiontoast.MotionToast;

public class ConfirmRoomDialog {
    // Data
    private String token;
    private String userId;
    private SharedRoomResponse room;

    // UI
    Activity activity;
    AlertDialog dialog;
    private TextView textRoom;
    private ImageView imageView;
    private Button btnAccept;
    private Button btnCancel;
    private ProgressBar progressBar;

    private OnActionClick listener;

    public ConfirmRoomDialog(Activity activity, String userId, String token, SharedRoomResponse room) {
        this.activity = activity;
        this.userId = userId;
        this.token = token;
        this.room = room;

    }

    public void showDialog() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm_room, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity,  R.style.AlertDialogTheme);

        // mapping
        textRoom = view.findViewById(R.id.tv_room_name);
        imageView = view.findViewById(R.id.img_room);
        btnAccept = view.findViewById(R.id.btn_accept);
        btnCancel = view.findViewById(R.id.btn_cancel);
        progressBar = view.findViewById(R.id.progress_bar);

        textRoom.setText(room.getName());
        Glide.with(activity)
                .load(room.getImageUrl())
                .into(imageView);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNegativeClick();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPositiveClick();
            }
        });

        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public interface OnActionClick {
        void onPositiveClick();
        void onNegativeClick();
    }

    public void setOnActionClick(OnActionClick listener) {
        this.listener = listener;
    }
}
