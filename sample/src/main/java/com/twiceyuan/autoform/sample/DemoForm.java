package com.twiceyuan.autoform.sample;

import com.twiceyuan.autoform.annotations.Form;
import com.twiceyuan.autoform.annotations.FormField;
import com.twiceyuan.autoform.provider.PassValidator;
import com.twiceyuan.autoform.provider.SimpleFormItemProvider;

/**
 * Created by twiceYuan on 2017/7/2.
 * <p>
 * 表单示例
 */
@Form(itemProvider = SimpleFormItemProvider.class)
public class DemoForm {

    @FormField(label = "姓名", order = 0)
    public String name;

    @FormField(label = "手机", order = 1, validator = PhoneValidator.class, itemProvider = PhoneFormItemProvider.class)
    public String phone;

    @FormField(label = "地址", order = 2)
    public String address;

    @FormField(label = "公司", order = 3)
    public String company;

    @FormField(label = "备注", order = 4, validator = PassValidator.class)
    public String remark;
}
