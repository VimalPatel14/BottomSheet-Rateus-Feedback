package com.vimal.rate.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.vimal.rate.BuildConfig;
import com.vimal.rate.R;
import com.vimal.rate.appupdate.AppUpdateChecker;
import com.vimal.rate.bottomsheet.BottomDialog;
import com.tedpark.tedpermission.rx2.TedRx2Permission;
import com.vimal.rate.creation.MyCreationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TedRx2Permission.with(MainActivity.this)
                .setRationaleTitle(R.string.read_storage)
                .setRationaleMessage(R.string.storage_message)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request()
                .subscribe(permissionResult -> {
                            if (permissionResult.isGranted()) {

                            } else {
                                Toast.makeText(getBaseContext(), "Permission Denied\n" + permissionResult.getDeniedPermissions().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );

        findViewById(R.id.feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback();
            }
        });

        findViewById(R.id.rateus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rateus();
            }
        });

        AppUpdate();
    }

    public void AppUpdate(){

        AppUpdateChecker appUpdateChecker = new AppUpdateChecker(MainActivity.this, BuildConfig.VERSION_NAME);
        appUpdateChecker.checkForUpdate(false);
    }


    public void Rateus( ) {

        new BottomDialog.Builder(this)
                .setTitle("Rate Us ⭐")
                .setContent("Please take a moment and rate us on Google Play Your feedback will help others find great learning experiences and it will help us build better and more apps for Android. And, we’ll be forever grateful to you.")
                .setPositiveText("Google Play")
                .setNegativeText("No Thanks")
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(MainActivity.this, "You don't have Google Play installed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();


    }


    public void feedback( ) {

        new BottomDialog.Builder(this)
                .setTitle("Feedback \uD83E\uDD14")
                .setContent("Your feedback will help others find great learning experiences and it will help us build better and more apps for Android. And, we’ll be forever grateful to you.")
                .setPositiveText("Send Feedback")
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        startActivity(new Intent(MainActivity.this, MyCreationActivity.class));
                    }
                }).show();


    }
}