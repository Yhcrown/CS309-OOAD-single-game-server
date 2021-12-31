package com.pacman.network.hamdlermapping;

import java.lang.reflect.Method;

/**
 * @author 王广帅
 * @date 2021年01月26日 3:48 下午
 */
public class GameMappingMethod {
    private Object handlerObj;
    private Method handlerMethod;

    public GameMappingMethod(Object handlerObj, Method handlerMethod) {
        this.handlerObj = handlerObj;
        this.handlerMethod = handlerMethod;
    }

    public Object getHandlerObj() {
        return handlerObj;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }
}
