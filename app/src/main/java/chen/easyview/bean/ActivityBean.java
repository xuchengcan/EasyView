package chen.easyview.bean;

/**
 * Created by Chen on 2017/1/15.
 */

public class ActivityBean {

    public Class mAClass;
    public String name;

    public ActivityBean(Class mAClass) {
        this.mAClass = mAClass;
        this.name = mAClass.getSimpleName();
    }
}
