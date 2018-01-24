package com.view.DialogBox;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.view.percentlayout.PercentLinearLayout;

import chen.easyview.R;


/**
 * Created by baicheng on 2016/11/12.
 */

public class Dialogbox_permission extends Dialog {

    private Context context;

    private TextView tv_title, tv_content, tv_cancel, tv_ok, tv_set;
    private View v;
    private PercentLinearLayout pll_choose, pll_set;

    private OnCallback onCallback = null;

    public interface OnCallback {
        void callback(DialogObject dialogObject);
    }

    public class DialogObject {
        public Dialogbox_permission dialog;
    }

    public static Dialogbox_permission newInstance(Context context) {
        return new Dialogbox_permission(context);
    }

    public Dialogbox_permission(Context context) {
        this(context, R.style.dialog);
    }

    public Dialogbox_permission(Context context, int theme) {
        super(context, theme);
        this.context = context;
        init();
    }

    private void init() {
        setContentView(R.layout.dialogbox_permission);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        v = (View) findViewById(R.id.v);
        tv_set = (TextView) findViewById(R.id.tv_set);
        pll_choose = (PercentLinearLayout) findViewById(R.id.pll_choose);
        pll_set = (PercentLinearLayout) findViewById(R.id.pll_set);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogbox_permission.this.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogbox_permission.this.dismiss();
            }
        });
    }

    public Dialogbox_permission setCancel(String cancel) {
        tv_cancel.setText(cancel);
        return this;
    }


    public Dialogbox_permission setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public Dialogbox_permission setOk(String ok) {
        tv_ok.setText(ok);
        return this;
    }

    public Dialogbox_permission setContent(CharSequence content) {
        tv_content.setText(content);
        return this;
    }

    public Dialogbox_permission setStartPermissonCallback(final OnCallback onCallback) {
        pll_choose.setVisibility(View.GONE);
        pll_set.setVisibility(View.VISIBLE);
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogObject dialogObject = new DialogObject();
                dialogObject.dialog = Dialogbox_permission.this;
                onCallback.callback(dialogObject);
            }
        });
        return this;
    }

    public Dialogbox_permission setOnCallback(final OnCallback onCallback) {
        pll_choose.setVisibility(View.VISIBLE);
        pll_set.setVisibility(View.GONE);
        tv_cancel.setVisibility(View.GONE);
        v.setVisibility(View.GONE);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogObject dialogObject = new DialogObject();
                dialogObject.dialog = Dialogbox_permission.this;
                onCallback.callback(dialogObject);
            }
        });
        return this;
    }

    public Dialogbox_permission setCancelOnCallback(final OnCallback onCallback) {
        pll_choose.setVisibility(View.VISIBLE);
        pll_set.setVisibility(View.GONE);
        tv_cancel.setVisibility(View.VISIBLE);
        v.setVisibility(View.VISIBLE);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogObject dialogObject = new DialogObject();
                dialogObject.dialog = Dialogbox_permission.this;
                onCallback.callback(dialogObject);
            }
        });
        return this;
    }
}
