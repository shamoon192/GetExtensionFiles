package com.futuristicdevelopers.getextensionfiles;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,  DataAdapter.RecyclerViewItemClickListener{
    private final String TAG = MainActivity.class.getSimpleName();
    EditText et_extension;
    Button btn_find;
    private List<ExtensionFile> fileList = new ArrayList<ExtensionFile>();
    private List<String> folderList = new ArrayList<String>();
    private int REQUEST_CODE = 101;
    CustomListViewDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        et_extension = findViewById(R.id.et_extension);
        btn_find = findViewById(R.id.btn_find);

        btn_find.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_find:
                String extension = et_extension.getText().toString();
                if (!extension.equalsIgnoreCase("")) {
                    //first step is to check permission
                    //if we have permission then proceed to find the files with given extensions
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Permission is not granted
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
                    } else {
                        // Permission is granted
                        //we got the text ... so now need to find files in the extensions
                        readFiles(extension);
                    }


                }
                break;
            default:
                break;
        }
    }

    private void readFiles(String extension) {
        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        File[] files = root.listFiles();
        folderList.clear();
        for (File file : files) {
            folderList.add(file.getPath());
        }
        File downloadFolder = new File(root.getAbsolutePath()+"/Download");
        if (folderList.contains(downloadFolder.getAbsolutePath())){
            files = downloadFolder.listFiles();
            fileList.clear();
            for (File file : files) {
                if (file.getName().contains(extension))
                    fileList.add(new ExtensionFile(file.getName(), file.getPath()));
            }
            Log.d(TAG, fileList.size() + "");
        }
        if (fileList.size() <= 0)
            fileList.add(new ExtensionFile("Not found", "Not Found"));

        DataAdapter dataAdapter = new DataAdapter(fileList, this);
        customDialog = new CustomListViewDialog(MainActivity.this, dataAdapter);
        customDialog.show();
        customDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                //resume tasks needing this permission
                String extension = et_extension.getText().toString();
                if (!extension.equalsIgnoreCase("")) {
                    //we got the text ... so now need to find files in the extensions
                    readFiles(extension);
                }

            } else {
                Toast.makeText(this,
                        "Oops you just denied the permission",
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void clickOnItem(String data) {
        Toast.makeText(MainActivity.this, "Path: "+data, Toast.LENGTH_SHORT).show();
    }
}
