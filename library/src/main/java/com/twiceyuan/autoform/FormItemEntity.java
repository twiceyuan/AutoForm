package com.twiceyuan.autoform;

import com.twiceyuan.autoform.provider.LayoutProvider;
import com.twiceyuan.autoform.provider.Validator;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 表单字段实体
 */
public class FormItemEntity {

    /**
     * 字段名称
     */
    public String label;

    /**
     * 排序，因为反射取到的变量集合是乱序的
     */
    public float order;

    /**
     * 表单生成的 Key 值
     */
    public String key;

    /**
     * 映射的属性名称，注意：如果是动态字段讲不会反射到实体类上，必须使用 Map 获取，因为实体中无法动态创建属性
     */
    public String fieldName;

    /**
     * 表单样式
     */
    public LayoutProvider layout;

    /**
     * 合法校验器
     */
    public Validator validator;

    /**
     * 提示
     */
    public String hint;

    /**
     * 最终用户的输入，进行数据绑定时设置观察用户输入
     */
    @SuppressWarnings("WeakerAccess")
    protected Object result;
}
