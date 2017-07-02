package com.twiceyuan.autoform.provider;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 *
 */
public interface HintProvider {

    /**
     * 生成编辑框提示，一般是根据 label 字段生成
     *
     * @param label 如果 label 字段是用户名
     * @return 就可以指定「请输入用户名」的 HindProvider
     */
    String buildHint(String label);
}
