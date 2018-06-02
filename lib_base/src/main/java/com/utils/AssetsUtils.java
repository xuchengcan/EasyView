package com.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chen on 2017/2/15.
 */

public class AssetsUtils {

    private static final String DIR_NAME = "EasyView";

    /**
     * @param isCover 是否覆盖已存在的目标文件
     * @param source  Assets/中的文件名称
     * @param dest    eaayview/中的文件名称
     */
    public static void copyFromAssetsToSdcard(Context context, boolean isCover, String source, String dest) {

        String sdcardPath = Environment.getExternalStorageDirectory().toString();
        File dir = new File(sdcardPath + "/" + DIR_NAME);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(DIR_NAME + "/" + dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = context.getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
