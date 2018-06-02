package com.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utils.ShowUtils;


public abstract class BaseLazyFragment extends Fragment {

    protected Context mContext;
    protected boolean isInit;//loadLazyData 之后要设为 true ,防止重复加载
    private boolean mIsPrepared;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getArguments() != null) {
            getFragmentArguments(getArguments());
        }
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && mIsPrepared && !isInit) {
            //异步初始化
            loadLazyData();
        }
    }

    protected abstract void getFragmentArguments(Bundle args);

    @LayoutRes
    protected abstract int getContentView();

    protected abstract void initView(View view, @Nullable Bundle savedInstanceState);

    protected abstract void loadLazyData();

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
