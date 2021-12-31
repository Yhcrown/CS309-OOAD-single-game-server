package com.pacman.game.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pacman.game.server.messages.FailMessage;
import com.pacman.game.server.messages.player.LoginResponse;
import com.pacman.game.server.messages.SuccessMessage;
import com.pacman.game.server.messages.status.ErrorStatus;
import com.pacman.game.server.mapper.AccountMapper;
import com.pacman.game.server.pojo.database.Account;
import com.pacman.network.codec.IGameMessage;
//import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
//import sun.jvm.hotspot.asm.Register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yhc
 * @create 2021-11-03-12:23
 */
@Service
public class LoginService {
    @Autowired
    AccountMapper accountMapper;

    @Autowired
    GlobalService globalService;

    public IGameMessage register(String account, String password) {
        if (globalService.isClose) {
            return new FailMessage(403, globalService.getReason());
        }
        if (!checkAccount(account))
            return new FailMessage(ErrorStatus.REG_ACCOUNT_FORM_ERROR.getCode(), ErrorStatus.REG_ACCOUNT_FORM_ERROR.getMsg());

        if (!checkPassword(password))
            return new FailMessage(ErrorStatus.REG_PASSWORD_FORM_ERROR.getCode(), ErrorStatus.REG_PASSWORD_FORM_ERROR.getMsg());
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        Account newAccount = new Account();
        newAccount.setAccount(account);
        newAccount.setPassword(md5Password);

        try {
            accountMapper.insert(newAccount);
        } catch (Exception e) {
            return new FailMessage(ErrorStatus.REG_DUPLICATE_ACCOUNT_ERROR.getCode(), ErrorStatus.REG_DUPLICATE_ACCOUNT_ERROR.getMsg());
        }
        return new SuccessMessage();
    }

    public IGameMessage login(String account, String password) {

        if (globalService.isClose) {
            return new FailMessage(403, globalService.getReason());
        }
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("account", account);
        Account tempAccount = accountMapper.selectOne(accountQueryWrapper);
        if (tempAccount == null)
            return new FailMessage(ErrorStatus.LOGIN_FIND_ACCOUNT_ERROR.getCode(), ErrorStatus.LOGIN_FIND_ACCOUNT_ERROR.getMsg());
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (md5Password.equals(tempAccount.getPassword())) {
            LoginResponse result = new LoginResponse();
            result.setPlayId(tempAccount.getId());
            return result;
        } else {
            return new FailMessage(ErrorStatus.LOGIN_PASSWORD_ERROR.getCode(), ErrorStatus.LOGIN_PASSWORD_ERROR.getMsg());
        }

    }


    private boolean checkAccount(String account) {
        String regex = "^[\\w@\\$\\^!~,.\\*]{5,20}+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(account);
        return matcher.matches();
    }

    private boolean checkPassword(String password) {
        String regex = "^[\\w@\\$\\^!~,.\\*]{8,16}+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
