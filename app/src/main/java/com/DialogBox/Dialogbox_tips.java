package com.DialogBox;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import chen.easyview.R;

public class Dialogbox_tips extends Dialog {

    private Context context;

    private TextView tv_title, tv_content, tv_cancel, tv_ok;
    private View v;

    private OnCallback onCallback = null;

    public interface OnCallback {
        void callback(DialogObject dialogObject);
    }

    public class DialogObject {
        public Dialogbox_tips dialog;
    }

    public static Dialogbox_tips newInstance(Context context) {
        return new Dialogbox_tips(context);
    }

    public Dialogbox_tips(Context context) {
        this(context, R.style.dialog);
    }

    public Dialogbox_tips(Context context, int theme) {
        super(context, theme);
        this.context = context;
        init();
    }

    private void init() {
        setContentView(R.layout.dialogbox_textview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        v = (View) findViewById(R.id.v);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogbox_tips.this.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogbox_tips.this.dismiss();
            }
        });
    }

    public Dialogbox_tips setCancel(String cancel) {
        tv_cancel.setText(cancel);
        return this;
    }


    public Dialogbox_tips setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public Dialogbox_tips setOk(String ok) {
        tv_ok.setText(ok);
        return this;
    }

    public Dialogbox_tips setContent(String content) {
        tv_content.setText(content);
        return this;
    }

    public Dialogbox_tips setOnCallback(final OnCallback onCallback) {
        tv_cancel.setVisibility(View.VISIBLE);
        v.setVisibility(View.VISIBLE);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogObject dialogObject = new DialogObject();
                dialogObject.dialog = Dialogbox_tips.this;
                onCallback.callback(dialogObject);
            }
        });
        return this;
    }
}
