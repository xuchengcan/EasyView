package chen.easyview.bean;

import java.io.Serializable;

/**
 * Created by chennuo on 2018/3/7.
 */

public class PicBean implements Serializable {

    public int position;
    public String path;
    public String url;

    public PicBean(String path) {
        this.path = path;
    }

    public PicBean(String path, String url) {
        this.path = path;
        this.url = url;
    }
}
