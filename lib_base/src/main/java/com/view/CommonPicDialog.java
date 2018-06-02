package com.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.PicBean;
import com.bumptech.glide.Glide;
import com.utils.DisplayUtils;
import com.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import chen.lib_base.R;


public class CommonPicDialog extends AppCompatDialog {

    Context context;
    ViewPager vp;
    TextView tv_count;
    ArrayList<PicBean> mPaths;
    ArrayList<View> mViews = new ArrayList<>();
    int position;

    public CommonPicDialog(Context context) {
        super(context);

    }

    public CommonPicDialog(Context context, ArrayList<PicBean> mPaths, int position) {
        super(context, R.style.dialog);
        this.context = context;
        this.mPaths = mPaths;
        this.position = position;
    }


    public CommonPicDialog(Context context, int theme) {
        super(context, theme);
    }

    public CommonPicDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.common_pic_review);

        /**
         * 这里的设置顺序不能改变，需先改变外部状态栏导航栏的位置，再对 dialog 进行高度适配
         */
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 全屏模式，布局延伸至状态栏
                        //                View.SYSTEM_UI_FLAG_FULLSCREEN //隐藏状态栏
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 布局延伸至导航栏
                        //                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏导航栏
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE //保持布局稳定
                        //                View.SYSTEM_UI_FLAG_IMMERSIVE // 沉浸式模式
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // 沉浸式模式，有粘黏效果
        );
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.height = DisplayUtils.SCREEN_REAL_HEIGHT_PIXELS;
        wl.width = DisplayUtils.SCREEN_REAL_WIDTH_PIXELS; //屏幕宽高的属性由DisplayMetrics类获得
        //        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);


        vp = findViewById(R.id.vp);
        tv_count = findViewById(R.id.tv_count);

        for (PicBean bean : mPaths) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.common_pic, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_pic);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });

            if (TextUtils.isValidate(bean.path)) {
                Glide.with(imageView)
                        .load(bean.path)
//                        .fitCenter()
                        .into(imageView);
            } else {
                Glide.with(imageView)
                        .load(bean.url)
//                        .fitCenter()
                        .into(imageView);
            }
            mViews.add(view);

        }

        if (mViews.size() > 1) {
            tv_count.setText((position + 1) + "/" + mViews.size());
        }

        vp.setAdapter(new NoticeAdapter(mViews));

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_count.setText((position + 1) + "/" + mViews.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vp.setCurrentItem(position);

    }

    @Override
    public void show() {
        super.show();


    }

    class NoticeAdapter extends PagerAdapter {

        private List<View> mListViews;

        public NoticeAdapter(List<View> mListViews) {
            this.mListViews = mListViews;//构造方法，参数是我们的页卡，这样比较方便。
        }

        @Override
        public int getCount() {
            return mListViews.size();//返回页卡的数量
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mListViews.get(position));//删除页卡
        }
    }

}
