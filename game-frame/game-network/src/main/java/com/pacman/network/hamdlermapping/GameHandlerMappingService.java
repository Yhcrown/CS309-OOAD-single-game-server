package com.pacman.network.hamdlermapping;

import com.pacman.network.codec.GameCodecFactory;
import com.pacman.network.codec.GameMessageHeader;
import com.pacman.network.codec.IGameMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王广帅
 * @date 2021年01月26日 4:35 下午
 */
public class GameHandlerMappingService {
    private Logger logger = LoggerFactory.getLogger(GameHandlerMappingService.class);

    private Map<MessageClassKey, GameMappingMethod> gameMappingMethodMap = new HashMap<>();

    /**
     * 从Sring的容器中读取GameHandlerComponent标记的实例
     *
     * @param context
     */
    public void scanGameHandler(ApplicationContext context) {
        String[] handlerBeanNames = context.getBeanNamesForAnnotation(GameHandlerComponent.class);
        logger.debug("----->加载GameHandlerComponent的bean数量:{}", handlerBeanNames.length);
        for (String beanName : handlerBeanNames) {
            Object beanObj = context.getBean(beanName);
            //查找处理请求的方法
            Method[] methods = beanObj.getClass().getMethods();
            for (Method method : methods) {
                GameMapping gameMapping = method.getAnnotation(GameMapping.class);
                if (gameMapping != null) {
                    Class<? extends IGameMessage> gameMessageClass = gameMapping.value();
                    MessageClassKey classKey = GameCodecFactory.addMessageClass(gameMessageClass);
                    GameMappingMethod gameMappingMethod = new GameMappingMethod(beanObj, method);
                    gameMappingMethodMap.put(classKey, gameMappingMethod);
                }
            }
        }
    }

    public void callMethod(GameChannelContext ctx, GameMessageHeader header, IGameMessage gameMessage) throws Exception {
        int messageId = header.getMessageId();
        int messageType = header.getMessageType();
        MessageClassKey classKey = new MessageClassKey(messageId, messageType);
        GameMappingMethod gameMappingMethod = gameMappingMethodMap.get(classKey);
        if (gameMappingMethod == null) {
            throw new IllegalArgumentException("不存在此消息的处理方法,messageId:" + messageId + ", messageType:" + messageType);
        }
//        logger.info("message:{}",gameMappingMethod);
        Method handlerMethod = gameMappingMethod.getHandlerMethod();
        handlerMethod.invoke(gameMappingMethod.getHandlerObj(), ctx, gameMessage,header.getSeqId());
    }
}
