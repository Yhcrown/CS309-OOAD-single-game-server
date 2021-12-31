package com.pacman.game.server.service;

/**
 * @author yhc
 * @create 2021-11-17-22:47
 */

import com.pacman.game.server.cache.PlayerCache;
import com.pacman.game.server.messages.room.ExitRoomPush;
import com.pacman.game.server.messages.room.RefreshRoomInnerMessage;
import com.pacman.game.server.system.PlayerChannelService;
import com.pacman.game.server.pojo.PlayerWithHero;
import com.pacman.game.server.pojo.Room;
import com.pacman.game.server.pojo.RoomInfo;
import com.pacman.game.server.pojo.cache.PlayerInfo;
import com.pacman.network.codec.IGameMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.nio.channels.Channel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomService {
    ConcurrentHashMap<Long, Room> rooms = new ConcurrentHashMap<>();
    public static Long roomId = 1001L;

    @Autowired
    PlayerCache playerCache;

    @Autowired
    PlayerChannelService playerChannelService;

    public Long createRoom(String name, Integer type, Integer map, Integer capacity, Long playerId) {
        RoomInfo info = new RoomInfo();
        info.setCapacity(capacity % 10);
        info.setName(name);
        info.setType(type);
        info.setRoomId(roomId);
        info.setPeopleNum(0);
        info.setMapId(map);
        info.setPacmanNum(capacity / 10);
        Room room = new Room();
        room.setInfo(info);
        room.setStart(false);
        room.setManagerId(playerId);
        rooms.put(roomId, room);
//        System.out.println("room info: "+room);
        return roomId++;
    }


    public List<RoomInfo> getRoomInfos() {
        List<RoomInfo> infos = new ArrayList<>();
        for (Room value : rooms.values()) {
            infos.add(value.getInfo());
        }
        return infos;
    }

    public int enterRoom(Long roomId, Long playerId) {
        Room room = rooms.get(roomId);
        if (room == null)
            return 1;
        RoomInfo info = room.getInfo();
        if (info.getPeopleNum() + 1 > info.getCapacity())
            return 2;
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        if (playerInfo.getInRoom() == Boolean.TRUE)
            return 3;
        if (room.getStart())
            return 5;
        info.setPeopleNum(info.getPeopleNum() + 1);
        playerInfo.setInRoom(Boolean.TRUE);
        playerInfo.setRoomId(roomId);
        LinkedHashMap<Long, PlayerWithHero> playerMap = room.getPlayerMap();
        playerMap.put(playerId, new PlayerWithHero(playerCache.getPlayerInfo(playerId).getBaseInfo(), playerCache.getPlayerInfo(playerId).getChoose(), Boolean.FALSE));

        return 4;
    }

    public List<PlayerWithHero> innerInfo(Long id) {
        Room room = rooms.get(id);

        return new ArrayList<>(room.getPlayerMap().values());
    }


    public Room getRoom(Long roomId) {
        return rooms.get(roomId);
    }

    public void exitRoom(Long roomId, Long playerId) {
        Room room = rooms.get(roomId);
        PlayerInfo playerInfo = playerCache.getPlayerInfo(playerId);
        playerInfo.setInRoom(false);
        playerInfo.setRoomId(null);
        LinkedHashMap<Long, PlayerWithHero> playerMap = room.getPlayerMap();

        RoomInfo info = room.getInfo();
        info.setPeopleNum(info.getPeopleNum() - 1);
        playerMap.remove(playerId);
        if (info.getPeopleNum() == 0)
            destoryRoom(roomId);
        else {
            if (Objects.equals(room.getManagerId(), playerId)) {
                for (Long aLong : playerMap.keySet()) {
                    room.setManagerId(aLong);
                    break;
                }
            }

            RefreshRoomInnerMessage refreshRoomInner = new RefreshRoomInnerMessage();
            refreshRoomInner.setPlayerList(new ArrayList<>(playerMap.values()));
            refreshRoomInner.setManager(room.getManagerId());
            info.setPeopleNum(refreshRoomInner.getPlayerList().size());
            refreshRoomInner.setRoomInfo(room.getInfo());
            sendPushMessage(roomId, refreshRoomInner);
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (Objects.equals(room.getManagerId(), playerId))
//                destoryRoom(roomId);
        }

    }

    public void destoryRoom(Long roomId) {
        Room room = rooms.get(roomId);
//        LinkedHashMap<Long, PlayerWithHero> playerMap = room.getPlayerMap();
//        for (Player value : playerMap.values()) {
//            value.setInRoom(false);
//            value.setRoomId(null);
//        }
//        sendPushMessage(roomId, new ExitRoomPush());
        rooms.remove(roomId);
    }

    public void sendPushMessage(Long roomId, IGameMessage message) {

        Room room = rooms.get(roomId);
        LinkedHashMap<Long, PlayerWithHero> playerMap = room.getPlayerMap();
        List<Long> players = new ArrayList<>(playerMap.keySet());
        System.out.println("send " + message + "to " + players);
        playerChannelService.broadcast(message, players);
    }

    public void ready(Long roomId, Long playerId) {
        Room room = rooms.get(roomId);
        LinkedHashMap<Long, PlayerWithHero> playerMap = room.getPlayerMap();
        PlayerWithHero playerWithHero = playerMap.get(playerId);
        playerWithHero.setIsReady(Boolean.TRUE);
        RefreshRoomInnerMessage refreshRoomInner = new RefreshRoomInnerMessage();
        refreshRoomInner.setPlayerList(new ArrayList<>(playerMap.values()));
        refreshRoomInner.setManager(room.getManagerId());
        RoomInfo info = room.getInfo();
        info.setPeopleNum(refreshRoomInner.getPlayerList().size());
        refreshRoomInner.setRoomInfo(room.getInfo());
        sendPushMessage(roomId, refreshRoomInner);

    }

    public void cancelReady(Long roomId, Long playerId) {
        Room room = rooms.get(roomId);
        LinkedHashMap<Long, PlayerWithHero> playerMap = room.getPlayerMap();
        PlayerWithHero playerWithHero = playerMap.get(playerId);
        playerWithHero.setIsReady(Boolean.FALSE);
        RefreshRoomInnerMessage refreshRoomInner = new RefreshRoomInnerMessage();
        refreshRoomInner.setPlayerList(new ArrayList<>(playerMap.values()));
        refreshRoomInner.setManager(room.getManagerId());
        RoomInfo info = room.getInfo();
        info.setPeopleNum(refreshRoomInner.getPlayerList().size());
        refreshRoomInner.setRoomInfo(room.getInfo());
        sendPushMessage(roomId, refreshRoomInner);
    }

}
