package com.pacman.game.server.beanconfig;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yhc
 * @create 2021-11-03-11:53
 */
@Configuration
@MapperScan("com.pacman.game.server.mapper")
public class MybatisPlusConfig {
    // 配置逻辑删除插件
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }
}
