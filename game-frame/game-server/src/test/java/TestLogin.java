import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.testng.annotations.Test;

/**
 * @author yhc
 * @create 2021-11-03-15:41
 */
public class TestLogin {
    @Test
    public void test01(){
        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024 * 8, 0, 4, -4, 0));// 添加拆包
    }
}
