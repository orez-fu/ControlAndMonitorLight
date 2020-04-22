package com.example.controlandmonitorlight.view.view.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.controlandmonitorlight.adapter.RecyclerRoomShareAdapter;
import com.example.controlandmonitorlight.model.Room;
import com.example.controlandmonitorlight.repositories.RealtimeFirebaseRepository;
import com.example.controlandmonitorlight.view.view.Activity.LoginActivity;
import com.example.controlandmonitorlight.view.view.Activity.ScanCodeActivity;
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
import www.sanju.motiontoast.MotionToast;

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

    // Data variables
    private int expandableValue;
    private FirebaseUser mUser;
    private String profileImageUrl;
    private List<Room> mListRoom;
    private int numberDevices;

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
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();
            mUser.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                MotionToast.Companion.createColorToast(getActivity(),"Profile updating completed!",
                                        MotionToast.Companion.getTOAST_SUCCESS(),
                                        MotionToast.Companion.getGRAVITY_BOTTOM(),
                                        MotionToast.Companion.getLONG_DURATION(),
                                        ResourcesCompat.getFont(getContext(), R.font.helvetica_regular));
                                mUser = FirebaseAuth.getInstance().getCurrentUser();
                                edtName.setText(mUser.getDisplayName());
                            } else {
                                MotionToast.Companion.createColorToast(getActivity(),"Profile updating failed!",
                                        MotionToast.Companion.getTOAST_ERROR(),
                                        MotionToast.Companion.getGRAVITY_BOTTOM(),
                                        MotionToast.Companion.getLONG_DURATION(),
                                        ResourcesCompat.getFont(getContext(), R.font.helvetica_regular));
                            }
                        }
                    });
        } else {
            MotionToast.Companion.createColorToast(getActivity(),"No information is changed",
                    MotionToast.Companion.getTOAST_INFO(),
                    MotionToast.Companion.getGRAVITY_BOTTOM(),
                    MotionToast.Companion.getLONG_DURATION(),
                    ResourcesCompat.getFont(getContext(), R.font.helvetica_regular));
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
}
