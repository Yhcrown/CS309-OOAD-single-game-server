package com.pacman.network.hamdlermapping;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author 王广帅
 * @date 2021年01月26日 3:31 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Service //让此注解继承@Service注解，在项目启动时，自动扫描被GameMessageHandler注解的类
public @interface GameHandlerComponent {

    String value() default "";
}
