package com.pacman.network.hamdlermapping;

import com.pacman.network.codec.IGameMessage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王广帅
 * @date 2021年01月26日 3:36 下午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GameMapping {
    Class<? extends IGameMessage> value();
}

