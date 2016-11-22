import com.chaos.hades.client.config.ConfigBasedHadesClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
public class ConfigBasedHadesClientTest {
    private ConfigBasedHadesClient configBasedHadesClient;
    @Before
    public void init () throws Exception {
        this.configBasedHadesClient = new ConfigBasedHadesClient();
    }

    @Test
    public void getConfig() {
        Assert.assertEquals(configBasedHadesClient.getOrElse("q", ""), "");

//        Assert.assertEquals(configBasedHadesClient.getOrElse("key1", ""), "hello world");
    }
}
