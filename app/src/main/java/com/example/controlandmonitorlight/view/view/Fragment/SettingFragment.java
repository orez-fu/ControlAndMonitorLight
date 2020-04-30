package com.example.controlandmonitorlight.view.view.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.Repository.DeviceClient;
import com.example.controlandmonitorlight.adapter.RecyclerRoomShareAdapter;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.model.SharedRoomRequest;
import com.example.controlandmonitorlight.model.SharedRoomResponse;
import com.example.controlandmonitorlight.model.SimpleResponse;
import com.example.controlandmonitorlight.repositories.RealtimeFirebaseRepository;
import com.example.controlandmonitorlight.utils.CustomAlertDialog;
import com.example.controlandmonitorlight.view.view.Activity.LoginActivity;
import com.example.controlandmonitorlight.view.view.Activity.ScanCodeActivity;
import com.example.controlandmonitorlight.view.view.Dialog.ConfirmRoomDialog;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;


public class SettingFragment extends Fragment {

    private final String TAG = "SETTING_ACTIVITY";



    private final int CHOOSE_IMAGE_ID = 101;
    public static final int REQUEST_CODE_SCAN = 102;

    // UI variables
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private CircleImageView circleImageView;
    private TextView textName;
    private TextView textDisplayName;
    private ConstraintLayout expandableLayout;
    private Button buttonShare;
    private EditText edtName;
    private EditText edtEmail;
    private ImageView imageProfile;
    private Button buttonChooseImage;
    private Button buttonUpdate;
    private Uri uriProfileImage;
    private ProgressBar progressBar;
    private Button buttonLogout;
    private Button buttonScan;
    private RecyclerView recyclerViewRoomShare;
    private RecyclerRoomShareAdapter roomShareAdapter;
    private TextView textNumberRoom;
    private TextView textNumberDevice;
    private Dialog dialogLoading;
    private ConfirmRoomDialog dialogInfo;

    // Data variables
    private int expandableValue;
    private FirebaseUser mUser;
    private String profileImageUrl;
    private List<Room> mListRoom;
    private int numberDevices;
    private SharedRoomResponse sharedRoom;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // init values
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        expandableValue = 0;
        numberDevices = 0;
        dialogLoading = new Dialog(getActivity());
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        // mapping UI
        appBarLayout = view.findViewById(R.id.appbar_layout);
        toolbar = view.findViewById(R.id.toolbar_minimum);
        imageProfile = view.findViewById(R.id.img_profile);
        textName = view.findViewById(R.id.tv_name);
        textDisplayName = view.findViewById(R.id.tv_name_display);
        circleImageView = view.findViewById(R.id.circle_profile);
        progressBar = view.findViewById(R.id.progress_bar);
        buttonChooseImage = view.findViewById(R.id.btn_choose_image);
        edtName = view.findViewById(R.id.edt_name);
        edtEmail = view.findViewById(R.id.edt_email);
        buttonUpdate = view.findViewById(R.id.btn_update);
        buttonShare = view.findViewById(R.id.btn_expand_share);
        expandableLayout = view.findViewById(R.id.layout_expandable);
        buttonLogout = view.findViewById(R.id.btn_logout);
        buttonScan = view.findViewById(R.id.btn_scan);
        recyclerViewRoomShare = view.findViewById(R.id.recycler_room_share);
        textNumberDevice = view.findViewById(R.id.tv_number_device);
        textNumberRoom = view.findViewById(R.id.tv_number_room);

        // set data
        mListRoom = new ArrayList<>();
        roomShareAdapter = new RecyclerRoomShareAdapter(mListRoom);

        loadUserInformation();
        loadRooms();

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                if (Math.abs(offset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                    toolbar.setVisibility(View.VISIBLE);
                } else if (offset == 0) {
                    // Expanded
                    toolbar.setVisibility(View.GONE);
                } else {
                    if(toolbar.getVisibility() == View.VISIBLE) {
                        toolbar.setVisibility(View.GONE);
                    }
                }
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableValue = (expandableValue == 1) ? 0 : 1;
                if(expandableValue == 0) {
                    expandableLayout.setVisibility(GONE);
                } else {
                    expandableLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ScanCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });

        recyclerViewRoomShare.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewRoomShare.setAdapter(roomShareAdapter);

        final Dialog mDialog = new Dialog(getActivity());
        roomShareAdapter.setOnRoomClickListener(new RecyclerRoomShareAdapter.OnRoomClickListener() {
            @Override
            public void onItemClick(Room room) {
                if(!(room.getQrCodeUrl() == null)) {
                    mDialog.setContentView(R.layout.dialog_qr_code);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ImageView imageQR = mDialog.findViewById(R.id.img_qr_code);
                    TextView textRoomName = mDialog.findViewById(R.id.tv_room_name);

                    textRoomName.setText(room.getName());
                    Glide.with(getContext())
                            .load(room.getQrCodeUrl())
                            .into(imageQR);
                    mDialog.show();
                } else {
                    Toasty.success(getContext(), "This room is not setup QR Code", Toast.LENGTH_SHORT, true).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mUser == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void loadUserInformation() {
        if (mUser != null) {
            if (mUser.getPhotoUrl() != null) {
                profileImageUrl = mUser.getPhotoUrl().toString();
                Glide.with(this)
                        .load(profileImageUrl)
                        .load(mUser.getPhotoUrl().toString())
                        .into(imageProfile);
                Glide.with(this)
                        .load(profileImageUrl)
                        .load(mUser.getPhotoUrl().toString())
                        .into(circleImageView);
            }
            edtEmail.setText(mUser.getEmail());
            if (mUser.getDisplayName() != null) {
                textName.setText(mUser.getDisplayName());
                edtName.setText(mUser.getDisplayName());
                textDisplayName.setText(mUser.getDisplayName());
            }
        }
    }

    private void saveUserInformation() {
        String displayName = edtName.getText().toString();

        if (displayName.isEmpty()) {
            edtName.setError("Name required");
            edtName.requestFocus();
            return;
        }

        if (mUser != null && profileImageUrl != null) {
            if(!dialogLoading.isShowing()) {
                dialogLoading.show();
            }
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();
            mUser.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(dialogLoading.isShowing()) {
                                dialogLoading.dismiss();
                            }

                            if (task.isSuccessful()) {
                                CustomAlertDialog.showAlertDialog(getActivity(),
                                        CustomAlertDialog.SUCCESS_DIALOG,
                                        "Update profile",
                                        "Your profile has updated successful");
                                mUser = FirebaseAuth.getInstance().getCurrentUser();
                                edtName.setText(mUser.getDisplayName());
                            } else {
                                CustomAlertDialog.showAlertDialog(getActivity(),
                                        CustomAlertDialog.ERROR_DIALOG,
                                        "Update profile",
                                        "Your profile has updated failed");
                            }

                        }
                    });
        } else {
            CustomAlertDialog.showAlertDialog(getActivity(),
                    CustomAlertDialog.WARNING_DIALOG,
                    "Update profile",
                    "Can't not update profile");
        }
    }

    private void showImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE_ID);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE_ID && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriProfileImage);
                Glide.with(this)
                        .load(bitmap)
                        .into(imageProfile);

                uploadImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {

            if(data.hasExtra(ScanCodeActivity.EXTRA_TOKEN)) {
                final String token = data.getStringExtra(ScanCodeActivity.EXTRA_TOKEN);
                dialogLoading.show();
                loadRoomFromToken(token);

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_warning, null);
                builder.setView(view);
                ((TextView) view.findViewById(R.id.tv_title)).setText("QR Code reader");
                ((TextView) view.findViewById(R.id.tv_content)).setText("QR code is invalid");

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
    }

    private void uploadImageToFirebaseStorage() {
        final StorageReference profileImageRef = FirebaseStorage.getInstance()
                .getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            progressBar.setVisibility(View.VISIBLE);
            UploadTask uploadTask = profileImageRef.putFile(uriProfileImage);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return profileImageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        progressBar.setVisibility(GONE);
                        profileImageUrl = downloadUri.toString();
                        Log.d("Main Activity", "Profile URL: " + profileImageUrl);
                    } else {
                        progressBar.setVisibility(GONE);
                        //       Toast.makeText(SettingActivity.this, "Load failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(GONE);
                    //  Toast.makeText(SettingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void loadRooms() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid()).child("rooms");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String roomId = snapshot.getKey();
                    DatabaseReference referenceRooms = FirebaseDatabase.getInstance()
                            .getReference("rooms").child(roomId);
                    referenceRooms.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Room room = dataSnapshot.getValue(Room.class);
                            mListRoom.add(room);

                            numberDevices += room.getDevices().size();
                            textNumberRoom.setText(String.valueOf(mListRoom.size()));
                            textNumberDevice.setText(String.valueOf(numberDevices));
                            roomShareAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "Cancel");
                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("INTRODUCTION", "Cancel With no error");
            }
        });
    }

    private void loadRoomFromToken(final String token) {
        DeviceClient.getInstance().getRoomInfo(new SharedRoomRequest(token)).enqueue(new Callback<SharedRoomResponse>() {
            @Override
            public void onResponse(Call<SharedRoomResponse> call, Response<SharedRoomResponse> response) {
                sharedRoom = response.body();
                if(sharedRoom.getSuccess() == SharedRoomResponse.SUCCESS_OK) {
                    dialogInfo = new ConfirmRoomDialog(getActivity(), mUser.getUid(), token, sharedRoom);

                    dialogInfo.setOnActionClick(new ConfirmRoomDialog.OnActionClick() {
                        @Override
                        public void onPositiveClick() {
                            dialogInfo.dismissDialog();
                            if (!dialogLoading.isShowing()) {
                                dialogLoading.show();
                            }
                            DeviceClient.getInstance().addRoomByToken(mUser.getUid(), new SharedRoomRequest(token)).enqueue(new Callback<SimpleResponse>() {
                                @Override
                                public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                                    if (dialogInfo.isShowing()) {
                                        dialogInfo.dismissDialog();
                                    }
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                                    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_success, null);
                                    builder.setView(view);
                                    ((TextView) view.findViewById(R.id.tv_title)).setText("Add new room successful");
                                    ((TextView) view.findViewById(R.id.tv_content)).setText(sharedRoom.getName() + " has already to use");

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
                                    if (dialogLoading.isShowing()) {
                                        dialogLoading.dismiss();
                                    }
                                    alertDialog.show();
                                }

                                @Override
                                public void onFailure(Call<SimpleResponse> call, Throwable t) {
                                    if (dialogInfo.isShowing()) {
                                        dialogInfo.dismissDialog();
                                    }
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                                    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_error, null);
                                    builder.setView(view);
                                    ((TextView) view.findViewById(R.id.tv_title)).setText("Add new room failed");
                                    ((TextView) view.findViewById(R.id.tv_content)).setText("Has error while adding room");

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
                                    if (dialogLoading.isShowing()) {
                                        dialogLoading.dismiss();
                                    }
                                    alertDialog.show();
                                }
                            });
                        }

                        @Override
                        public void onNegativeClick() {
                            dialogInfo.dismissDialog();
                        }
                    });
                    if(dialogLoading.isShowing()) {
                        dialogLoading.dismiss();
                    }
                    dialogInfo.showDialog();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_warning, null);
                    builder.setView(view);
                    ((TextView) view.findViewById(R.id.tv_title)).setText("QR Code reader");
                    ((TextView) view.findViewById(R.id.tv_content)).setText("QR code is invalid");

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
                    if (dialogLoading.isShowing()) {
                        dialogLoading.dismiss();
                    }
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<SharedRoomResponse> call, Throwable t) {
                sharedRoom = null;
                if(dialogLoading.isShowing()) {
                    dialogLoading.dismiss();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_error, null);
                builder.setView(view);
                ((TextView) view.findViewById(R.id.tv_title)).setText("Add new room failed");
                ((TextView) view.findViewById(R.id.tv_content)).setText("Has error while adding room");

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
        });
    }

}
