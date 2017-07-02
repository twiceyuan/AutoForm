package com.twiceyuan.autoform.provider;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 默认的 HintProvider
 */
public class SimpleHintProvider implements HintProvider {
    @Override
    public String buildHint(String label) {
        return "请输入" + label;
    }
}
