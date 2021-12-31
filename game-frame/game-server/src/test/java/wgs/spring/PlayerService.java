package wgs.spring;

/**
 * @author 王广帅
 * @date 2021年02月18日 2:50 下午
 */
public class PlayerService {

    private static PlayerService instance = new PlayerService();

    private PlayerService() {
    }

    public static PlayerService getInstance() {
        return instance;
    }

    public void addExp(Long playerId, Long addExp) {
        
    }
}
