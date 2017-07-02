package com.twiceyuan.autoform.annotations;

import android.support.annotation.NonNull;

import com.twiceyuan.autoform.provider.FormItemProvider;
import com.twiceyuan.autoform.provider.FormItemValidator;
import com.twiceyuan.autoform.provider.NonNullValidator;
import com.twiceyuan.autoform.provider.SimpleFormItemProvider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by twiceYuan on 2017/7/2.
 *
 * 表单列
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormField {

    /**
     * 字段名称
     *
     * @return 不能为空
     */
    @NonNull String label();

    /**
     * 排序，因为反射取到的变量集合是乱序的
     *
     * @return 数字越小代表越靠前，比如 0 会排在前面，而 100 会排在后面
     */
    int order();

    /**
     * 表单生成的 Key 值
     */
    @NonNull String key() default "";

    /**
     * 默认为 String
     *
     * @return 字段类型
     */
    Class type() default String.class;

    /**
     * 合法校验器
     */
    Class<? extends FormItemValidator> validator() default NonNullValidator.class;

    /**
     * 表单样式
     */
    Class<? extends FormItemProvider> itemProvider() default SimpleFormItemProvider.class;

    /**
     * 输入提示
     */
    String hint() default "";
}
