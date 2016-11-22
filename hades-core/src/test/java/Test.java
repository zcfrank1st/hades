import com.chaos.hades.core.Hades;

/**
 * Created by zcfrank1st on 21/11/2016.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections("192.168.33.222:2181").build();
        hades.deleteProject("demo");
        hades.initProject("demo");
        hades.addConf("demo", "kkk1", "cc");
        hades.addConf("demo", "kkk2", "cc");
        hades.addConf("demo", "kkk3", "cc");

        hades.scanProjectConf("demo");
        hades.destroy();
    }
}
