package com.twiceyuan.autoform;

import com.twiceyuan.autoform.provider.FormItemProvider;
import com.twiceyuan.autoform.provider.FormItemValidator;

import java.io.Serializable;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 表单字段实体
 */
public class FormItemEntity implements Serializable {

    /**
     * 字段名称
     */
    public String label;

    /**
     * 排序，因为反射取到的变量集合是乱序的
     */
    public int order;

    /**
     * 表单生成的 Key 值
     */
    public String key;

    /**
     * 默认为 String
     */
    public Class type;

    /**
     * 表单样式
     */
    public Class<? extends FormItemProvider> itemProvider;

    /**
     * 合法校验器
     */
    public Class<? extends FormItemValidator> validator;

    /**
     * 提示
     */
    public String hint;

    /**
     * 最终用户的输入，进行数据绑定时设置观察用户输入
     */
    public Object result;

    /**
     * 反向引用
     */
    public FormItemProvider itemProviderInstance;
}
