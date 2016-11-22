import com.chaos.hades.client.spring.SpringBasedHadesClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
public class SpringBasedHadesClientTest {
    private SpringBasedHadesClient springBasedHadesClient;

    @Before
    public void init() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        springBasedHadesClient = (SpringBasedHadesClient) context.getBean("client");
    }

    @Test
    public void getConfig () {
        Assert.assertEquals(springBasedHadesClient.getOrElse("qq", ""), "");

//        Assert.assertEquals(springBasedHadesClient.getOrElse("key1", ""), "hello world");
    }
}
