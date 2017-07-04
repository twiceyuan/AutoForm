package com.twiceyuan.autoform.provider;

import com.twiceyuan.autoform.FormItemEntity;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 表单输入验证器
 */
public interface Validator<ItemProvider extends LayoutProvider> {

    /**
     * 检查失败时回调的方法
     *
     * @param entity   表单元素
     * @param provider 表单布局提供者
     */
    void onValidateFailed(FormItemEntity entity, ItemProvider provider);

    /**
     * 校验方法
     *
     * @param result 供校验的结果
     * @return 是否校验成功
     */
    boolean validate(Object result);
}
