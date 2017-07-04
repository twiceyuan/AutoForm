package com.twiceyuan.autoform.provider;

/**
 * Created by twiceYuan on 2017/7/4.
 *
 * 动态表单元素，在静态配置无法满足需要或者元素没有复用价值时，可以使用动态表单元素来创建表单元素并交给表单管理器管理，业务逻辑将和普通
 * 表单元素相同。
 */
public abstract class DynamicFormItem {

    /**
     * 字段名称
     */
    public abstract String label();

    /**
     * 排序，因为反射取到的变量集合是乱序的
     */
    public abstract float order();

    /**
     * 表单生成的 Key 值
     */
    public abstract String key();

    /**
     * 默认为 String
     */
    public Class type() {
        return String.class;
    }

    /**
     * 表单样式
     */
    public abstract LayoutProvider layoutProvider();

    /**
     * 合法校验器
     */
    public abstract Validator validator();

    /**
     * 提示
     */
    public abstract String hint();
}
