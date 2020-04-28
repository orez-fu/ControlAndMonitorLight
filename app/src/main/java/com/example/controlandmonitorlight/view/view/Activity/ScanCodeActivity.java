package com.example.controlandmonitorlight.view.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.controlandmonitorlight.R;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public static final String EXTRA_TOKEN = "com.example.controlandmonitorlight.view.view.Activity.EXTRA_TOKEN";

    private ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        scannerView = findViewById(R.id.zx_scan);
        scannerView.setAutoFocus(true);
        scannerView.startCamera();

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
//                PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(ScanCodeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//        }

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.setResultHandler(ScanCodeActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(ScanCodeActivity.this, "You must accept this permission", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(ScanCodeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                })
                .check();
    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
        finish();
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent intent = new Intent();
        if(rawResult.getText().startsWith("ZETTABYTES")) {
            Log.d("SCAN_CODE_ACTIVITY", rawResult.getText().substring(11));
            intent.putExtra(EXTRA_TOKEN, rawResult.getText().substring(11));
        }
        scannerView.stopCamera();

        setResult(RESULT_OK, intent);
        finish();
    }
}
