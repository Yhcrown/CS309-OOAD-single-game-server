package com.pacman.game.server.gamehandler;

//import com.sun.net.httpserver.Authenticator;

import com.pacman.game.server.messages.SuccessMessage;
import com.pacman.game.server.messages.player.LoginRequest;
import com.pacman.game.server.messages.player.LoginResponse;
import com.pacman.game.server.messages.player.RegisterRequest;
import com.pacman.game.server.service.LoginService;
import com.pacman.game.server.system.PlayerChannelService;
import com.pacman.network.codec.IGameMessage;
import com.pacman.network.hamdlermapping.GameChannelContext;
import com.pacman.network.hamdlermapping.GameHandlerComponent;
import com.pacman.network.hamdlermapping.GameMapping;

import org.springframework.beans.factory.annotation.Autowired;
//import sun.jvm.hotspot.asm.Register;

/**
 * 处理玩家的登陆功能
 *
 * @author 王广帅
 * @date 2021年01月26日 8:28 下午
 */
@GameHandlerComponent
public class LoginHandler extends AbstractGameHandler {
    //临时定义一个playerId
    private long nowPlayerId = 1000;
    @Autowired
    private PlayerChannelService playerChannelService;

    @Autowired
    private LoginService loginService;

    @GameMapping(LoginRequest.class)
    public void login(GameChannelContext ctx, LoginRequest request, int seqId) {
        IGameMessage result = loginService.login(request.getAccount(), request.getPassword());

        if (result instanceof LoginResponse) {
            LoginResponse response = (LoginResponse) result;
//            long playerId = ++ nowPlayerId;
//            LoginResponse response = new LoginResponse();
//            response.setPlayId(playerId);
            //登陆成功之后，将此用户对应的连接加入到连接管理器之中
//            System.out.println("response ID:" + response.getPlayId());
//            if (playerChannelService.findChannel(response.getPlayId()) == null)
            playerChannelService.addChannel(response.getPlayId(), ctx.getChannel());
            logger.debug("{} 登陆成功,PlayerID {} ", request.getAccount(), ctx.getPlayerId());

            ctx.sendGameMessage(response, seqId);
        } else {
            ctx.sendGameMessage(result, seqId);
        }

    }

    @GameMapping(RegisterRequest.class)
    public void register(GameChannelContext ctx, RegisterRequest request, int seqId) {
        IGameMessage result = loginService.register(request.getAccount(), request.getPassword());
        if (result instanceof SuccessMessage)
            logger.debug("{} 注册成功", request.getAccount());
        else
//            logger.debug("{} 注册失败，已有该账号", request.getAccount());
        ctx.sendGameMessage(result, seqId);
    }

}
