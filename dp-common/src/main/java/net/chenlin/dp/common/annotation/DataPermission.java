package net.chenlin.dp.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 * @author ZhouChenglin
 * @date 2018/2/7
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * 表的别名，为别名默认为空
     * @return
     */
    String alias() default "";

    /**
     * 无数据权限，可查看用户自己的数据
     * @return
     */
    boolean user() default true;

    /**
     * 查看下级数据
     * @return
     */
    boolean sub() default false;

}
