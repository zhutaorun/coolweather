package com.zhutaorun.coolweather.util;

/**
 * Created by zhutaorun on 15/9/1.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
