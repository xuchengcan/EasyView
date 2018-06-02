package com.net;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;
import com.utils.ShowUtils;
import com.utils.TextUtils;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chennuo on 2018/3/2.
 */

public class LoadingManager<T> {

    private static final int MIN_REFRESH_TIME = 1000;//最短刷新时间
    private CountDownTimer mDownTimer;
    private Random rand = new Random();
    private Context mContext;
    private BaseQuickAdapter adapter;
    private SwipeRefreshLayout srf;
    private View emptyView;
    private int loadAnimation = BaseQuickAdapter.ALPHAIN;
    private pageChangeListener mPageChangeListener;
    private loadingListener mLoadingListener;
    private loadingMoreListener mLoadingMoreListener;
    private RefreshListener mRefreshListener;
    private boolean openAnimation = true;
    private boolean isCloseByTimer = false;
    private boolean isCloseByNet = false;
    private boolean isChangeData = false;

    public LoadingManager(BaseQuickAdapter adapter) {
        this.adapter = adapter;
    }

    public void setSwipeRefresh(SwipeRefreshLayout swipeRefresh) {
        srf = swipeRefresh;
    }

    public void setEmptyView(View view) {
        emptyView = view;
    }

    public LoadingManager setOnPageChangeListener(pageChangeListener listener) {
        mPageChangeListener = listener;
        return this;
    }

    public LoadingManager setOnLoadingListener(loadingListener listener) {
        mLoadingListener = listener;
        return this;
    }

    public LoadingManager setOnLoadingMoreListener(loadingMoreListener listener) {
        mLoadingMoreListener = listener;
        return this;
    }

    public LoadingManager setOnRefreshListener(RefreshListener listener) {
        mRefreshListener = listener;
        return this;
    }

    public LoadingManager addLoadAnimation(@BaseQuickAdapter.AnimationType int animation) {
        loadAnimation = animation;
        return this;
    }

    public LoadingManager setOpenAnimation(boolean isOpen) {
        openAnimation = isOpen;
        return this;
    }

    public LoadingManager setChangeData(boolean changeData) {
        isChangeData = changeData;
        return this;
    }

    public void loadData(final int Page, @LoadingState final int state, Observable<JsonResultBean<ArrayList<T>>> server) {

        int refresh_time = rand.nextInt(800) + MIN_REFRESH_TIME;
        KLog.e("refresh time is " + refresh_time);

        if (state == LoadingState.refresh) {
            if (null == srf) {
                throw new RuntimeException("please set SwipeRefreshLayout first!");
            }
            mDownTimer = new CountDownTimer(refresh_time, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    isCloseByTimer = false;
                }

                @Override
                public void onFinish() {
                    isCloseByTimer = true;
                    closeRefresh();
                    mDownTimer.cancel();
                }
            }.start();
            isCloseByTimer = false;
            isCloseByNet = false;
        }
        if (state == LoadingState.loading) {
            if (srf != null) {
                srf.post(new Runnable() {
                    @Override
                    public void run() {
                        srf.setRefreshing(true);
                    }
                });
                mDownTimer = new CountDownTimer(refresh_time, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        isCloseByTimer = false;
                    }

                    @Override
                    public void onFinish() {
                        isCloseByTimer = true;
                        closeRefresh();
                        mDownTimer.cancel();
                    }
                }.start();
                isCloseByTimer = false;
                isCloseByNet = false;
            }
        }
        server.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonResultBean<ArrayList<T>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        KLog.e();
                    }

                    @Override
                    public void onNext(JsonResultBean<ArrayList<T>> resultBean) {

                        if (RetrofitUtils.isSuccessStatusCode(resultBean)) {
                            if (RetrofitUtils.isSuccessData(resultBean)) {
                                int mRequestPage = Page;
                                if (state == LoadingState.loadMore) {
                                    if (TextUtils.isValidate(resultBean.data)) {
                                        if (!isChangeData) {
                                            adapter.addData(resultBean.data);
                                        }
                                        if (mLoadingMoreListener != null) {
                                            mLoadingMoreListener.onLoadingMore(resultBean);
                                        }
                                        adapter.loadMoreComplete();
                                        mRequestPage++;
                                        if (mPageChangeListener != null) {
                                            mPageChangeListener.onPageChange(mRequestPage);
                                        }
                                    } else {
                                        adapter.loadMoreEnd();
                                    }
                                    return;
                                }
                                if (state == LoadingState.refresh) {
                                    if (!isChangeData) {
                                        adapter.setNewData(resultBean.data);
                                    }
                                    //                                    adapter.disableLoadMoreIfNotFullPage();
                                    if (mRefreshListener != null) {
                                        mRefreshListener.onRefresh(resultBean);
                                    }
                                    mRequestPage++;
                                    if (mPageChangeListener != null) {
                                        mPageChangeListener.onPageChange(mRequestPage);
                                    }
                                    isCloseByNet = true;
                                    closeRefresh();
                                    return;
                                }
                                if (state == LoadingState.loading) {
                                    if (openAnimation) {
                                        adapter.openLoadAnimation(loadAnimation);
                                    }
                                    if (!isChangeData) {
                                        adapter.setNewData(resultBean.data);
                                    }
                                    if (mLoadingListener != null) {
                                        mLoadingListener.onLoading(resultBean);
                                    }
                                    mRequestPage++;
                                    if (mPageChangeListener != null) {
                                        mPageChangeListener.onPageChange(mRequestPage);
                                    }
                                    //                                    adapter.disableLoadMoreIfNotFullPage();
                                    isCloseByNet = true;
                                    closeRefresh();
                                }

                            } else {

                                if (state == LoadingState.loadMore) {
                                    adapter.loadMoreEnd();
                                    return;
                                }

                                if (state == LoadingState.refresh) {
                                    isCloseByNet = true;
                                    closeRefresh();
                                }
                                if (state == LoadingState.loading) {
                                    isCloseByNet = true;
                                    closeRefresh();
                                }
                                ShowUtils.showToast("数据加载失败");
                            }

                        } else {

                            if (state == LoadingState.loadMore) {
                                adapter.loadMoreFail();
                                return;
                            }

                            if (state == LoadingState.refresh) {
                                isCloseByNet = true;
                                closeRefresh();
                            }

                            if (state == LoadingState.loading) {
                                isCloseByNet = true;
                                closeRefresh();
                            }
                            RetrofitUtils.showErrorToast();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        RetrofitUtils.reportError(e);
                        if (state == LoadingState.loadMore) {
                            adapter.loadMoreFail();
                        }
                        if (state == LoadingState.refresh) {
                            isCloseByNet = true;
                            closeRefresh();
                        }
                        if (state == LoadingState.loading) {
                            isCloseByNet = true;
                            closeRefresh();
                        }
                    }

                    @Override
                    public void onComplete() {
                        KLog.e();
                    }
                });
    }

    private void closeRefresh() {
        if (isCloseByTimer && isCloseByNet) {
            if (null != srf) {
                srf.setRefreshing(false);
            }
        }
    }

    public interface pageChangeListener<T> {
        /**
         * @param page 请求成功下更新页数 page
         */
        void onPageChange(int page);
    }

    public interface loadingListener<T> {
        void onLoading(JsonResultBean<ArrayList<T>> resultBean);
    }

    public interface loadingMoreListener<T> {
        void onLoadingMore(JsonResultBean<ArrayList<T>> resultBean);
    }

    public interface RefreshListener<T> {
        void onRefresh(JsonResultBean<ArrayList<T>> resultBean);
    }

}
