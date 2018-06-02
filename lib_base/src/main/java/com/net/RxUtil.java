package com.net;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.support.annotation.NonNull;

import cn.nekocode.rxlifecycle.LifecycleEvent;
import cn.nekocode.rxlifecycle.RxLifecycle;
import cn.nekocode.rxlifecycle.compact.RxLifecycleCompact;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lindan on 18-1-30.
 * 将RxJava线程订阅通用代码提取出来
 */
public class RxUtil {
    /**
     * 为Observable应用默认的线程调度器
     * 默认subscribeOn为io，observeOn为mainThread
     *
     * @param <T> 原始Observable的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 绑定生命周期
     *
     * @param host 宿主，可以是Activity或Fragment或FragmentManager
     * @param <T>  Observable的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> bindLifeCycle(@NonNull Object host) {
        if (host instanceof Activity) {
            return upstream -> upstream.compose(RxLifecycle.bind((Activity) host).disposeObservableWhen(LifecycleEvent.DESTROY));
        } else if (host instanceof Fragment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return upstream -> upstream.compose(RxLifecycle.bind((Fragment) host).disposeObservableWhen(LifecycleEvent.DESTROY_VIEW));
            }
        } else if (host instanceof android.support.v4.app.Fragment) {
            return upstream -> upstream.compose(RxLifecycleCompact.bind((android.support.v4.app.Fragment) host).disposeObservableWhen(LifecycleEvent.DESTROY_VIEW));
        } else if (host instanceof FragmentManager) {
            return upstream -> upstream.compose(RxLifecycle.bind((FragmentManager) host).disposeObservableWhen(LifecycleEvent.DESTROY_VIEW));
        } else if (host instanceof android.support.v4.app.FragmentManager) {
            return upstream -> upstream.compose(RxLifecycleCompact.bind((android.support.v4.app.FragmentManager) host).disposeObservableWhen(LifecycleEvent.DESTROY_VIEW));
        }
        throw new IllegalArgumentException("host's type is Incorrect");
    }

    /**
     * 绑定自定义生命周期
     *
     * @param host 宿主，可以是Activity或Fragment或FragmentManager
     * @param <T>  Observable的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> bindLifeCycle(@NonNull Object host, LifecycleEvent event) {
        if (host instanceof Activity) {
            return upstream -> upstream.compose(RxLifecycle.bind((Activity) host).disposeObservableWhen(event));
        } else if (host instanceof Fragment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return upstream -> upstream.compose(RxLifecycle.bind((Fragment) host).disposeObservableWhen(event));
            }
        } else if (host instanceof android.support.v4.app.Fragment) {
            return upstream -> upstream.compose(RxLifecycleCompact.bind((android.support.v4.app.Fragment) host).disposeObservableWhen(event));
        } else if (host instanceof FragmentManager) {
            return upstream -> upstream.compose(RxLifecycle.bind((FragmentManager) host).disposeObservableWhen(event));
        } else if (host instanceof android.support.v4.app.FragmentManager) {
            return upstream -> upstream.compose(RxLifecycleCompact.bind((android.support.v4.app.FragmentManager) host).disposeObservableWhen(event));
        }
        throw new IllegalArgumentException("host's type is Incorrect");
    }

    /**
     * 应用默认的线程调度器及绑定生命周期
     *
     * @param host 宿主，可以是Activity或Fragment或FragmentManager(兼容v4包)
     * @param <T>  Observable的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> compose(@NonNull Object host) {
        return upstream -> {
            Observable<T> resultObservable = upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            if (host instanceof Activity) {
                return resultObservable
                        .compose(RxLifecycle.bind((Activity) host).disposeObservableWhen(LifecycleEvent.DESTROY));
            } else if (host instanceof Fragment) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    return resultObservable
                            .compose(RxLifecycle.bind((Fragment) host).disposeObservableWhen(LifecycleEvent.DESTROY_VIEW));
                }
                return resultObservable;
            } else if (host instanceof android.support.v4.app.Fragment) {
                return resultObservable
                        .compose(RxLifecycleCompact.bind((android.support.v4.app.Fragment) host).disposeObservableWhen(LifecycleEvent.DESTROY_VIEW));
            } else if (host instanceof FragmentManager) {
                return resultObservable
                        .compose(RxLifecycle.bind((FragmentManager) host).disposeObservableWhen(LifecycleEvent.DESTROY_VIEW));
            } else if (host instanceof android.support.v4.app.FragmentManager) {
                return resultObservable
                        .compose(RxLifecycleCompact.bind((android.support.v4.app.FragmentManager) host).disposeObservableWhen(LifecycleEvent.DESTROY_VIEW));
            } else {
                throw new IllegalArgumentException("host's type is Incorrect");
            }
        };
    }
}
