package com.twiceyuan.autoform.annotations;

import com.twiceyuan.autoform.provider.LayoutProvider;
import com.twiceyuan.autoform.provider.HintProvider;
import com.twiceyuan.autoform.provider.SimpleLayoutProvider;
import com.twiceyuan.autoform.provider.SimpleHintProvider;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 表单注解，用户标识一些表单中统一的约束，例如如何生成编辑框提示的等
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface Form {

    Class<? extends HintProvider> hintProvider() default SimpleHintProvider.class;

    Class<? extends LayoutProvider> itemProvider() default SimpleLayoutProvider.class;
}
