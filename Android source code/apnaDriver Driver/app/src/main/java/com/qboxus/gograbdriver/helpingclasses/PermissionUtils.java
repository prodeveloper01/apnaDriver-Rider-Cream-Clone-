package com.qboxus.gograbdriver.helpingclasses;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.qboxus.gograbdriver.R;
import com.qboxus.gograbdriver.appinterfaces.FragmentCallback;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {
    Activity activity;
    ActivityResultLauncher<String[]> mPermissionResult;

    public PermissionUtils(Activity activity, ActivityResultLauncher<String[]> mPermissionResult) {
        this.activity = activity;
        this.mPermissionResult=mPermissionResult;
    }

    public void takeStoragePermission()
    {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.P)
        {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            mPermissionResult.launch(permissions);
        }
        else
        {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            mPermissionResult.launch(permissions);
        }
    }

    public void takeCameraPermission()
    {
        String[] permissions = {Manifest.permission.CAMERA};
        mPermissionResult.launch(permissions);
    }


    public void showCameraPermissionDailog(String message)
    {
        List<String> permissionStatusList=new ArrayList<>();
        String[] permissions = {Manifest.permission.CAMERA};
        for (String keyStr:permissions)
        {
            permissionStatusList.add(Functions.getPermissionStatus(activity,keyStr));
        }

        if (permissionStatusList.contains("denied"))
        {
            Functions.showDoubleButtonAlert(activity, activity.getString(R.string.permission_alert),message,
                    activity.getString(R.string.cancel), activity.getString(R.string.permission), false, new FragmentCallback() {
                        @Override
                        public void Responce(Bundle bundle) {
                            if (bundle.getBoolean("isShow",false))
                            {
                                takeCameraPermission();
                            }
                        }
                    });
            return;
        }
        takeCameraPermission();

    }

    public boolean isLocationPermissionGranted()
    {
        int accessCoarsePermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFinePermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
       // int accessBackPermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        if(accessCoarsePermission!= PackageManager.PERMISSION_GRANTED)
            return false;
        if(accessFinePermission!= PackageManager.PERMISSION_GRANTED)
            return false;
//        if(accessBackPermission!= PackageManager.PERMISSION_GRANTED)
//            return false;
        else
            return true;
    }

    public void showLocationPermissionDailog(String message)
    {
        List<String> permissionStatusList=new ArrayList<>();
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
        for (String keyStr:permissions)
        {
            permissionStatusList.add(Functions.getPermissionStatus(activity,keyStr));
        }

        if (permissionStatusList.contains("denied"))
        {
            Functions.showDoubleButtonAlert(activity, activity.getString(R.string.permission_alert),message,
                    activity.getString(R.string.cancel), activity.getString(R.string.permission), false, new FragmentCallback() {
                        @Override
                        public void Responce(Bundle bundle) {
                            if (bundle.getBoolean("isShow",false))
                            {
                                takeLocationPermission();
                            }
                        }
                    });
            return;
        }
        takeLocationPermission();

    }

    public void takeLocationPermission()
    {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION
                ,Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION};
        mPermissionResult.launch(permissions);
    }


    public boolean isCameraPermissionGranted()
    {
        int cameraPermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        return (cameraPermission== PackageManager.PERMISSION_GRANTED);
    }

    public void showStoragePermissionDailog(String message)
    {
        List<String> permissionStatusList=new ArrayList<>();
        String[] permissions ;
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.P)
        {
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        }
        else
        {
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
        for (String keyStr:permissions)
        {
            permissionStatusList.add(Functions.getPermissionStatus(activity,keyStr));
        }

        if (permissionStatusList.contains("denied"))
        {
            Functions.showDoubleButtonAlert(activity, activity.getString(R.string.permission_alert),message,
                    activity.getString(R.string.cancel), activity.getString(R.string.permission), false, new FragmentCallback() {
                        @Override
                        public void Responce(Bundle bundle) {
                            if (bundle.getBoolean("isShow",false))
                            {
                                takeStoragePermission();
                            }
                        }
                    });
            return;
        }
        takeStoragePermission();

    }

    public boolean isStoragePermissionGranted()
    {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.P)
        {
            int readExternalStoragePermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            return (readExternalStoragePermission== PackageManager.PERMISSION_GRANTED);
        }
        else
        {
            int readExternalStoragePermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int writeExternalStoragePermission= ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return (readExternalStoragePermission== PackageManager.PERMISSION_GRANTED && writeExternalStoragePermission== PackageManager.PERMISSION_GRANTED );
        }
    }

}
