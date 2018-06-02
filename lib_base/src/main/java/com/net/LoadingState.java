package com.net;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.net.LoadingState.loadMore;
import static com.net.LoadingState.loading;
import static com.net.LoadingState.refresh;

/**
 * Created by chennuo on 2018/3/2.
 */

@IntDef({loading, loadMore, refresh})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadingState {

    public static final int loading = 0;
    public static final int loadMore = 1;
    public static final int refresh = -1;

    @LoadingState
    public int State = 0;

}
