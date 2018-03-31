package chen.easyview.bean;

import java.io.IOException;

/**
 * Created by Chen on 2018/1/13.
 */

public class test {

    double time[] = {1,4,9};

    public static void main(String[] args) throws IOException, InterruptedException {


        Thread.currentThread().sleep(1000);//毫秒
        Runtime.getRuntime().exec("adb shell input tap 250 250");
        System.out.print(123);



    }
}
