package com.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.widget.TextView;

import com.utils.TextUtils;

import chen.lib_base.R;


public class CommonProgressDialog extends AppCompatDialog {

    protected String content = "";

    public CommonProgressDialog(Context context) {
        super(context);

    }

    public CommonProgressDialog(Context context, String content) {
        super(context);
        this.content = content;
    }


    public CommonProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public CommonProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_progress_dialog);

        if (TextUtils.isValidate(content)) {
            TextView mTvContent = (TextView) findViewById(R.id.message);
            if (mTvContent != null) {
                mTvContent.setText(content);
            }
        }
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }



}
