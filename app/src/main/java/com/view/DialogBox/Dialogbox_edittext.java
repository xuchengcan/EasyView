package com.view.DialogBox;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.utils.TextUtils;

import chen.easyview.R;


/**
 * Created by baicheng on 2016/11/12.
 */

public class Dialogbox_edittext extends AppCompatDialog {

    private Context context;
    private String hint;

    private TextView tv_title, tv_cancel, tv_ok;
    private EditText et;

    // 外部窗口是否可以点击取消，false为禁止，true为允许
    private boolean isCanceledOnTouchOutside = false;

    public interface OnCallback {
        void callback(DialogObject dialogObject);
    }

    public class DialogObject {
        public Dialogbox_edittext dialog;
        public String text0, text1;
        public int position = -1;
    }

    public static Dialogbox_edittext newInstance(Context context) {
        return new Dialogbox_edittext(context);
    }

    public Dialogbox_edittext(Context context) {
        this(context, R.style.dialog);
    }

    public Dialogbox_edittext(Context context, int theme) {
        super(context, theme);
        this.context = context;
        init();
    }

    private void init() {
        setContentView(R.layout.dialogbox_edittext);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_ok = (TextView) findViewById(R.id.tv_ok);
        et = (EditText) findViewById(R.id.et);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogbox_edittext.this.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogbox_edittext.this.dismiss();
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    et.setHint("");
                } else {
                    et.setHint(hint);
                }
            }
        });
    }

    public Dialogbox_edittext setCancel(String cancel) {
        if (TextUtils.isValidate(cancel))
            tv_cancel.setText(cancel);
        return this;
    }


    public Dialogbox_edittext setTitle(String title) {
        if (TextUtils.isValidate(title))
            tv_title.setText(title);
        return this;
    }

    public Dialogbox_edittext setOk(String ok) {
        if (TextUtils.isValidate(ok))
            tv_ok.setText(ok);
        return this;
    }

    public Dialogbox_edittext setHint(String hint) {
        if (TextUtils.isValidate(hint)) {
            this.hint = hint;
        }
        return this;
    }


    public Dialogbox_edittext setMaxLength(int maxLength) {
        if (maxLength > 0)
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        return this;
    }

    public Dialogbox_edittext setMaxLine(int maxLine) {
        if (maxLine > 0)
            et.setLines(maxLine);
        return this;
    }


    private int inputtype = -1;

    public Dialogbox_edittext setInputType(int inputtype) {
        this.inputtype = inputtype;
        return this;
    }

    private String text = "";

    public Dialogbox_edittext setText(String text) {
        if (TextUtils.isValidate(text)) {
            this.text = text;
        }
        return this;
    }

    public Dialogbox_edittext setOnCallback(final Dialogbox_edittext.OnCallback onCallback) {
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogbox_edittext.DialogObject dialogObject = new Dialogbox_edittext.DialogObject();
                dialogObject.dialog = Dialogbox_edittext.this;
                dialogObject.text0 = et.getText().toString();
                onCallback.callback(dialogObject);
            }
        });
        return this;
    }

    public Dialogbox_edittext isCanceledOnTouchOutside(boolean can){
        isCanceledOnTouchOutside = can;
        return this;
    }

    @Override
    public void show() {
        super.show();
        //弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //设置是否 Window 外部可以点击
        setCanceledOnTouchOutside(isCanceledOnTouchOutside);

        if (TextUtils.isValidate(hint))
            et.setHint(hint);
        if (inputtype != -1)
            et.setInputType(inputtype);
        //该方法需放在setInputType后，setSelection光标才能跳到最后
        if (TextUtils.isValidate(text)) {
            et.setText(text);
            et.setSelection(text.length());//光标跳到最后
        }

    }
}