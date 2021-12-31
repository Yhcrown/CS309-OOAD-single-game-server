package wgs.spring;

/**
 * @author 王广帅
 * @date 2021年02月18日 2:51 下午
 */
public class BattleService {

    private static BattleService instance = new BattleService();

    public static BattleService getInstance() {
        return instance;
    }

    public void endBattle(Long playerId) {
        PlayerService playerService = PlayerService.getInstance();
        playerService.addExp(playerId, 1000L);
    }
}
