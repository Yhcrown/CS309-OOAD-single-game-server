package com.pacman.network.codec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GameMessageMeta {

    int messageId();

    //消息类型：1 request, 2 response , 3 push
    int messageType();

    /**
     * 设置此消息是否需要加密，默认是不加密
     *
     * @return
     */
    boolean isEncrypt() default false;

}
