# AutoForm

高度可复用的 Android 表单界面生成器。通过定义表单元素的界面样式、校验逻辑，然后注解在数据实体上来生成表单提交页。

# 使用方式

1. 定义一个表单类 `DemoForm.java`，用来说明表单里的输入项、样式、提示、校验

    ```java
    // layout 用来注解对应的表单样式提供者，可以定义表单全局和对应字段的
    @Form(layout = NormalLayoutProvider.class)
    public class DemoForm {
        
        // order 来控制表单字段在表单页面中的位置
        @FormField(label = "工单内容", order = 0, layout = TextAreaLayoutProvider.class)
        public String name;
    
        @FormField(label = "工单类型", order = 1.5f, layout = SelectorLayoutProvider.class)
        public String type;
    
        @FormField(label = "地址", order = 1)
        public String address;
    
        // validator 定义表单的校验器，以及校验过程中的界面动作
        @FormField(label = "联系方式", order = 2, validator = PhoneValidator.class, layout = PhoneLayoutProvider.class)
        public String phone;
    
        @FormField(label = "申请人", order = 3)
        public String company;
    }
    ```

2. 用 `FormManager.build(DemoForm.class)` 来获取 manager 对象，然后通过 inject 方法将表单注入到对应的布局容器中：
    ```java
    mForm = (FrameLayout) findViewById(R.id.form);
    final FormManager formManager = FormManager
                    .build(DemoForm.class)
                    .inject(mForm, true);
    ```
    
3. 获取输入结果：
    ```java
    mFormManager.getResult() // 返回 Map<String, Object> 对象
    ```

4. 大多数情况需要定制表单样式。如果需要整个表单使用统一样式，可以在 @Form 注解中标注 layout 的值为一个 LayoutProvider 子类的 Class。

   然后通过继承 LayoutProvider 来实现样式定制。还有一些其他的属性也提供了类似的定制方式，例如编辑框的 hint 提示等，使用方式可以参考默认值 SimpleFormItemProvider 和 SimpleHintProvider 的代码。

## 效果示意

1. 默认生成的输入界面
 
![输入界面](art/form.png)

2. 获取输入结果

![获取结果](art/form-result.png)

## License

```
Copyright 2017 twiceYuan.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```