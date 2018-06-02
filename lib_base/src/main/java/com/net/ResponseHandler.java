package com.net;



/**
 * Created by lindan on 18-1-31.
 * 响应结果处理
 */
public class ResponseHandler {
    private ResponseHandler() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static <T> T handleResponse(JsonResultBean<T> baseResponse) throws Exception {
        if (baseResponse != null) {
            if (baseResponse.code.equals("0")) {
                return baseResponse.data;
            }
            throw new Exception("test");
        } else {
            throw new Exception("联网失败");
        }
    }

//    public static BaseResponse handleBaseResponse(BaseResponse baseResponse) throws Exception {
//        if (baseResponse != null) {
//            if (baseResponse.isSuccessful()) {
//                return baseResponse;
//            }
//            throw new ApiAccessException(baseResponse.code, baseResponse.msg);
//        } else {
//            throw new Exception(ContextHolder.getContext().getString(R.string.common_str_network_error));
//        }
//    }
}
