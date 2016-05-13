package cn.e23.shunpai.http;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Response;

/**
 * Created by jian on 2016/5/4.
 */
public abstract class JsonArrayCallBack<T> extends Callback {
    @Override
    public List parseNetworkResponse(Response response) throws Exception {
        List list = new ArrayList();
        String string = response.body().string();
        Gson gson = new Gson();
        JsonArray array = new JsonParser().parse(string).getAsJsonArray();
        for(final JsonElement elem : array) {
            list.add(gson.fromJson(elem, getType()));
        }
//        Type listType = new TypeToken<LinkedList<T>>(){}.getType();
//        LinkedList<T> list = new Gson().fromJson(string, listType);
        return list;
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
