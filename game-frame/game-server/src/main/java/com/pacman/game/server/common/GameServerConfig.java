package com.pacman.game.server.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "xinyue.server.config")
public class GameServerConfig {

    private int port = 8088;
    private int bossThreads = 1;
    /**
     * 工作线程的数量
     **/
    private int workThreads = Runtime.getRuntime().availableProcessors();
    private int sendBuffSize = 65535;
    private int receiveBuffSize = 65535;
    /**
     * 客户端请求QPS限制
     */
    private int requestQps = 50;
    /**
     * 连接最大空闲时间,单位秒
     */
    private int maxIdleTime = 60;
    /**
     * 连接认证超时时间，单位秒
     */
    private int confirmTimeout = 10;
    private String rsaSalt = "sdfsdfsdfsdfdsfds";

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWorkThreads() {
        return workThreads;
    }

    public void setWorkThreads(int workThreads) {
        this.workThreads = workThreads;
    }

    public int getSendBuffSize() {
        return sendBuffSize;
    }

    public void setSendBuffSize(int sendBuffSize) {
        this.sendBuffSize = sendBuffSize;
    }

    public int getReceiveBuffSize() {
        return receiveBuffSize;
    }

    public void setReceiveBuffSize(int receiveBuffSize) {
        this.receiveBuffSize = receiveBuffSize;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getBossThreads() {
        return bossThreads;
    }

    public void setBossThreads(int bossThreads) {
        this.bossThreads = bossThreads;
    }

    public int getConfirmTimeout() {
        return confirmTimeout;
    }

    public void setConfirmTimeout(int confirmTimeout) {
        this.confirmTimeout = confirmTimeout;
    }

    public String getRsaSalt() {
        return rsaSalt;
    }

    public void setRsaSalt(String rsaSalt) {
        this.rsaSalt = rsaSalt;
    }

    public int getRequestQps() {
        return requestQps;
    }

    public void setRequestQps(int requestQps) {
        this.requestQps = requestQps;
    }
}
