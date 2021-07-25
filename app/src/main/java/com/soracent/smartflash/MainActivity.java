package com.soracent.smartflash;

import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Switch flashControl;
    ImageView BulbImage;
    CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashControl = findViewById(R.id.flashControl);
        BulbImage = findViewById(R.id.BulbImage);
        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                flashControl.setEnabled(true);
            } else {
                Toast.makeText(MainActivity.this, "This device has no flash", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "This device has no camera", Toast.LENGTH_SHORT).show();
        }

        BulbImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flashControl.isChecked()) {
                    flashControl.setChecked(false);
                } else {
                    flashControl.setChecked(true);
                }
            }
        });

        flashControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    try {
                        cameraManager.setTorchMode("0", false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    flashControl.setText(R.string.flash_off);
                    BulbImage.setImageResource(R.drawable.lightbulb_off);
                } else {
                    try {
                        cameraManager.setTorchMode("0", true);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    flashControl.setText(R.string.flash_on);
                    BulbImage.setImageResource(R.drawable.lightbulb_on);
                }
            }
        });
    }
}