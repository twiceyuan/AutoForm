package com.twiceyuan.autoform.util;

import com.twiceyuan.autoform.provider.HintProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 管理全局实例
 */
public class Instances {

    private static Map<Class<? extends HintProvider>, HintProvider> mHintProviderMap = new HashMap<>();

    public static <T> T newInstance(Class<T> instanceClass) {
        try {
            return instanceClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static <T extends HintProvider> T getHintProvider(Class<T> providerClass) {
        HintProvider provider = mHintProviderMap.get(providerClass);
        if (provider == null) {
            try {
                provider = providerClass.newInstance();
                mHintProviderMap.put(providerClass, provider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //noinspection unchecked
        return (T) provider;
    }
}
