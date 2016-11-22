import com.chaos.hades.core.Hades;
import com.chaos.hades.core.HadesProfile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcfrank1st on 21/11/2016.
 */
public class HadesTest {
    private Hades hades;

    @Before
    public void init () {
         hades = new Hades.HadesBuilder().connections("192.168.33.222:2181").build();
    }

    @Test
    public void addConfDefaultShouldAlsoGetTheConf () throws Exception {
        // default
        hades.deleteProject("demo");
        hades.initProject("demo");
        hades.addConf("demo", "key1", "hello world");
        Assert.assertEquals(hades.getConf("demo", "key1"), "hello world");
    }

    @Test
    public void addConfDevShouldAlsoGetTheConf () throws Exception {
        // dev
        hades.deleteProject("demo");
        hades.initProject("demo");
        hades.profile(HadesProfile.DEV).addConf("demo", "key1", "hello world");
        Assert.assertEquals(hades.profile(HadesProfile.DEV).getConf("demo", "key1"), "hello world");
    }

    @Test
    public void addConfPrdShouldAlsoGetTheConf () throws Exception {
        // prd
        hades.deleteProject("demo");
        hades.initProject("demo");
        hades.profile(HadesProfile.PRD).addConf("demo", "key1", "hello world");
        Assert.assertEquals(hades.profile(HadesProfile.PRD).getConf("demo", "key1"), "hello world");
    }

    @Test
    public void deleteConfShouldGetNoConf () throws Exception {
        hades.deleteProject("demo");
        hades.initProject("demo");
        hades.addConf("demo", "key1", "hahaha");
        hades.deleteConf("demo", "key1");

        Assert.assertFalse(hades.scanProjectConf("demo").keySet().contains("key1"));
    }

    @Test
    public void updateConfShouldRefreshValue () throws Exception {
        hades.deleteProject("demo");
        hades.initProject("demo");
        hades.addConf("demo", "key1", "hahaha");
        hades.updateConf("demo", "key1", "fuck");

        Assert.assertEquals(hades.getConf("demo", "key1"), "fuck");
    }

    @Test
    public void setConfsThenScanShouldGetAllKVs () throws Exception {
        hades.deleteProject("demo");
        hades.initProject("demo");
        hades.addConf("demo", "key1", "hahaha1");
        hades.addConf("demo", "key2", "hahaha2");
        hades.addConf("demo", "key3", "hahaha3");

        Map<String, String> map = new HashMap<>();
        map.put("key1", "hahaha1");
        map.put("key2", "hahaha2");
        map.put("key3", "hahaha3");

        Assert.assertEquals(hades.scanProjectConf("demo"), map);
    }

    @After
    public void finish () {
        hades.destroy();
    }
}
