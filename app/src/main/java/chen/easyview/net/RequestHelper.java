package chen.easyview.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chen.easyview.bean.JsonResultBean;
import chen.easyview.utils.TextUtil;

public class RequestHelper {


    /**
     * 获取请求参数
     */
    public static Map<String, String> getRequestMaps(String... keysValues) {
        List<String> keys = new ArrayList<String>();
        List<String> values = new ArrayList<String>();

        for (int i = 0; i < keysValues.length; i += 2) {
            keys.add(keysValues[i]);
        }
        for (int i = 1; i < keysValues.length; i += 2) {
            values.add(keysValues[i]);
        }

        Map<String, String> params = new HashMap<String, String>();
        for (int i = 0; i < keys.size(); i++) {
            params.put(keys.get(i), values.get(i));
        }
        return params;
    }


    /**
     * 获取url
     */
    public static String getRequestUrl(String url, Map<String, String> params) {
        try {
            if (!TextUtil.isValidate(params)) {
                return url;
            }
            StringBuilder sb = new StringBuilder(url);
            sb.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {

                sb.append(entry.getKey())
                        .append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                        .append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

    }


    public static <T> boolean isSuccessStatusCode(JsonResultBean<T> result) {
        if (result != null && result.Code == 0) {
            return true;
        } else {
            //TODO getCode==102 发广播退出登录，并提示账号错误；103  提示token出错；
//            if (result != null && result.Code == 103) {
//                BaseApplication.getUser().setUserToken("");
//                BaseApplication.setToken("");
//            }
            return false;
        }
    }


    /**
     * 对象是否为空
     *
     * @param result
     * @param <E>
     * @return
     */
    public static <E> boolean isAobj(E result) {
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断集合是否为空
     *
     * @param result
     * @param <E>
     * @return
     */
    public static <E> boolean isAList(List<E> result) {
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 集合的数量是否大于 0
     *
     * @param result
     * @param <E>
     * @return
     */
    public static <E> boolean isNoZero(List<E> result) {
        if (result.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

}
