package com.pacman.game.server.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yhc
 * @create 2021-11-17-22:12
 */
@Data
@NoArgsConstructor
public class Element {
    /**
     * 物体的x坐标
     */
    private Integer x;
    /**
     * 物体的y坐标
     */
    private Integer y;
    /**
     * 物体的宽度
     */
    private Integer width;
    /**
     * 物体的高度
     */
    private Integer height;
    /**
     * 方向
     */
    private Integer direct;
    /**
     * 元素类型
     */
    private int type;

    public static final int NORTH = 0;
    /**
     * 方向南
     */
    public static final int SOUTH = 1;
    /**
     * 方向西
     */
    public static final int WEST = 2;
    /**
     * 方向东
     */
    public static final int EAST = 3;
}
