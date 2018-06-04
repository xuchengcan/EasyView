package chen.easyview.bean;

/**
 * Created by Chen on 2017/1/15.
 */

public class ActivityBean {

    public boolean isModel;
    public String model_url;
    public Class mAClass;
    public String name;

    public ActivityBean(Class mAClass) {
        this.mAClass = mAClass;
        this.name = mAClass.getSimpleName();
        this.isModel = false;
    }

    public ActivityBean(String model_url) {
        this.isModel = true;
        this.model_url = model_url;
        this.name = model_url;
    }
}
