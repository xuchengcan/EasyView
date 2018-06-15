package chen.easyview.bean;

/**
 * Created by Chen on 2017/1/15.
 */

public class ActivityBean {

    public boolean isModel;
    public boolean isFlutter;
    public String model_url;
    public Class mAClass;
    public String name;

    public ActivityBean(Class mAClass) {
        this.mAClass = mAClass;
        this.name = mAClass.getSimpleName();
        this.isModel = false;
    }

    public ActivityBean(String model_url) {
        this.isFlutter = false;
        this.isModel = true;
        this.model_url = model_url;
        this.name = model_url;
    }

    public ActivityBean(String model_url, boolean isFlutter) {
        this.isFlutter = isFlutter;
        this.isModel = true;
        this.model_url = model_url;
        this.name = model_url;
    }
}
