package chen.easyview.net;

import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

public abstract class JsonCallback<T> extends Callback<T> {

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String body = response.body().string();
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (type == String.class) {
            return (T) body;
        }
        return JsonParser.deserializeFromJson(body, type);

    }

}
