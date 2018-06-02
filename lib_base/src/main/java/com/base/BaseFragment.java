package com.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utils.ShowUtils;

public abstract class BaseFragment extends Fragment {

    protected View mContentView;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getArguments() != null) {
            getFragmentArguments(getArguments());
        }
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    protected abstract void getFragmentArguments(Bundle args);

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView(View view, @Nullable Bundle savedInstanceState);

    protected View findViewById(@IdRes int id) {
        return mContentView.findViewById(id);
    }

    public void showToast(String content) {
        ShowUtils.showToast(content);
    }

    /**
     * 显示是否正在加载中
     */
    public void showLoading(boolean toggle) {
        if (toggle) {
            ShowUtils.showProgressDialog(mContext);
        } else {
            ShowUtils.dismissProgressDialog();
        }
    }

}
