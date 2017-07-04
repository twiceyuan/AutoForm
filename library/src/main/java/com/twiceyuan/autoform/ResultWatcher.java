package com.twiceyuan.autoform;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 结果观察者，配置给表单以用于实时获取表单的输入结果
 */
public interface ResultWatcher {
    void updateResult(Object result);
}
