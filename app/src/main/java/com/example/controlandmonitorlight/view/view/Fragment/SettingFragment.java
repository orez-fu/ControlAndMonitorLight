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
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.view.view.Activity.LoginActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


import de.hdodenhof.circleimageview.CircleImageView;
import www.sanju.motiontoast.MotionToast;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private final String TAG = "SETTING_ACTIVITY";

    private final int CHOOSE_IMAGE_ID = 101;

    private EditText edtName;
    private CircleImageView imgView;
    private Button btnSave;
    private Uri uriProfileImage;
    private ProgressBar progressBar;
    private Button btnLogout;

    private String profileImageUrl;

    private FirebaseAuth mAuth;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        mAuth = FirebaseAuth.getInstance();

        edtName = view.findViewById(R.id.edt_display_name);
        imgView = view.findViewById(R.id.img_profile);
        btnSave = view.findViewById(R.id.btn_save);
        btnLogout = view.findViewById(R.id.btn_logout);
        progressBar = view.findViewById(R.id.progress_bar);

        loadUserInformation();

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }


    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                profileImageUrl = user.getPhotoUrl().toString();
                Glide.with(this)
                        .load(profileImageUrl)
                        .load(user.getPhotoUrl().toString())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgView);
            }
            if (user.getDisplayName() != null) {
                Log.d(TAG, "Name: " + user.getDisplayName());
                edtName.setText(user.getDisplayName());
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

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profileImageUrl != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                MotionToast.Companion.createColorToast(getActivity(),"Profile updating completed!",
                                        MotionToast.Companion.getTOAST_SUCCESS(),
                                        MotionToast.Companion.getGRAVITY_BOTTOM(),
                                        MotionToast.Companion.getLONG_DURATION(),
                                        ResourcesCompat.getFont(getContext(), R.font.helvetica_regular));
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
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgView);

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
                        progressBar.setVisibility(View.GONE);
                        profileImageUrl = downloadUri.toString();
                        Log.d("Main Activity", "Profile URL: " + profileImageUrl);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        //       Toast.makeText(SettingActivity.this, "Load failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    //  Toast.makeText(SettingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
