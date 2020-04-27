package com.example.controlandmonitorlight.view.view.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private SharedRoomResponse room;
    private String userId;

    // UI
    Activity activity;
    Context mContext;
    AlertDialog dialog;
    private TextView textRoom;
    private ImageView imageView;
    private Button btnAccept;
    private Button btnCancel;
    private ProgressBar progressBar;

    private OnActionClick listener;

    public ConfirmRoomDialog(Activity activity, String userId, String token) {
        this.activity = activity;
        this.userId = userId;
        this.token = token;
        this.room = null;

    }

    public void showDialog() {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm_room, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // mapping
        textRoom = view.findViewById(R.id.tv_room_name);
        imageView = view.findViewById(R.id.img_room);
        btnAccept = view.findViewById(R.id.btn_accept);
        btnCancel = view.findViewById(R.id.btn_cancel);
        progressBar = view.findViewById(R.id.progress_bar);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNagetiveClick();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceClient.getInstance().addRoomByToken(userId, new SharedRoomRequest(token)).enqueue(new Callback<SimpleResponse>() {
                    @Override
                    public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
//                        MotionToast.Companion.createColorToast(activity,room.getName() + " has added successfully",
//                                MotionToast.Companion.getTOAST_SUCCESS(),
//                                MotionToast.Companion.getGRAVITY_BOTTOM(),
//                                MotionToast.Companion.getSHORT_DURATION(),
//                                ResourcesCompat.getFont(activity.getParent(), R.font.helvetica_regular));
                        dialog.dismiss();
                        Toast.makeText(activity, room.getName() + " has added successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<SimpleResponse> call, Throwable t) {
//                        MotionToast.Companion.createColorToast(activity,"Add room failed",
//                                MotionToast.Companion.getTOAST_ERROR(),
//                                MotionToast.Companion.getGRAVITY_BOTTOM(),
//                                MotionToast.Companion.getSHORT_DURATION(),
//                                ResourcesCompat.getFont(activity, R.font.helvetica_regular));
                        dialog.dismiss();
                        Toast.makeText(activity, "Add room failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setView(view);
        builder.setTitle("Add new room");
        builder.setCancelable(false);

        loadRoom();

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    private void loadRoom() {
        DeviceClient.getInstance().getRoomInfo(new SharedRoomRequest(token)).enqueue(new Callback<SharedRoomResponse>() {
            @Override
            public void onResponse(Call<SharedRoomResponse> call, Response<SharedRoomResponse> response) {
                room = response.body();
                Log.d("CONFIRM_ROOM", "Room: " + room.getName());
                progressBar.setVisibility(View.GONE);

                if(room.getSuccess() == SharedRoomResponse.SUCCESS_OK) {
                    textRoom.setText(room.getName());
                    Log.d("CONFIRM_ROOM", room.toString());
                    Glide.with(activity)
                            .load(room.getImageUrl())
                            .into(imageView);
                    btnAccept.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(activity, "QR is invalid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SharedRoomResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public interface OnActionClick {
        void onPositiveClick();
        void onNagetiveClick();
    }

    public void setOnActionClick(OnActionClick listener) {
        this.listener = listener;
    }
}
