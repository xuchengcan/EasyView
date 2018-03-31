package com.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.utils.TextUtils;

import chen.easyview.R;

public class CommonProgressDialog extends AlertDialog {

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
