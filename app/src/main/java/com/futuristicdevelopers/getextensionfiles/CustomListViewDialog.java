package com.futuristicdevelopers.getextensionfiles;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CustomListViewDialog extends Dialog implements View.OnClickListener {
    public Activity activity;
    public Dialog dialog;
    public Button btn_cancel, btn_done;
    TextView title;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public CustomListViewDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomListViewDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CustomListViewDialog(Activity a, RecyclerView.Adapter adapter) {
        super(a);
        this.activity = a;
        this.adapter = adapter;
        setupLayout();
    }

    private void setupLayout() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_done = findViewById(R.id.btn_done);
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        btn_cancel.setOnClickListener(this);
        btn_done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                //Do Something
                break;
            case R.id.btn_cancel:
                //dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
