package com.twiceyuan.autoform.sample;

import com.twiceyuan.autoform.annotations.Form;
import com.twiceyuan.autoform.annotations.FormField;
import com.twiceyuan.autoform.sample.form.NormalLayoutProvider;
import com.twiceyuan.autoform.sample.form.PhoneLayoutProvider;
import com.twiceyuan.autoform.sample.form.PhoneValidator;
import com.twiceyuan.autoform.sample.form.SelectorLayoutProvider;
import com.twiceyuan.autoform.sample.form.TextAreaLayoutProvider;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 表单示例
 */
@SuppressWarnings("WeakerAccess")
@Form(layout = NormalLayoutProvider.class)
public class DemoForm {

    @FormField(label = "工单内容", order = 0, layout = TextAreaLayoutProvider.class)
    public String name;

    @FormField(label = "工单类型", order = 1, layout = SelectorLayoutProvider.class)
    public String type;

    @FormField(label = "地址", order = 2)
    public String address;

    @FormField(label = "联系方式", order = 3, validator = PhoneValidator.class, layout = PhoneLayoutProvider.class)
    public String phone;

    @FormField(label = "申请人", order = 4)
    public String company;
}
