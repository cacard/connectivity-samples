package com.example.android.bluetoothlegatt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int REQUEST_CODE_PERMISSIONS = 111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();
    }

    private void checkPermission() {
        int checkResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkResult != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, getPermissions(), REQUEST_CODE_PERMISSIONS);
        } else {
            Toast.makeText(this, "已获得权限", Toast.LENGTH_SHORT).show();
            gotoScan();
        }
    }

    private String[] getPermissions() {
        return new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    }

    private boolean isGrantSuccess(int[] grantResults) {
        if (grantResults == null || grantResults.length == 0) {
            return false;
        }

        for (int grant : grantResults) {
            if (grant == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (REQUEST_CODE_PERMISSIONS == requestCode) {
            if (isGrantSuccess(grantResults)) {
                // ok
                Toast.makeText(this, "获取权限成功", Toast.LENGTH_SHORT).show();
                gotoScan();
            } else {
                Toast.makeText(this, "没有获取到权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void gotoScan() {
        startActivity(new Intent(this, DeviceScanActivity.class));
    }
}
