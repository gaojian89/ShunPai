package cn.e23.shunpai.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by jian on 2016/5/4.
 */
public abstract class JsonObjectCallBack<T> extends Callback<T>{
    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String string = response.body().string();
        T t = new Gson().fromJson(string, getType());
        return t;
    }

    Type getType(){
        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if(type instanceof Class){
            return type;
        }else{
            return new TypeToken<T>(){}.getType();
        }

    }
}
