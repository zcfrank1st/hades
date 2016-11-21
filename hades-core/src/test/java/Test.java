import com.chaos.hades.core.Hades;

/**
 * Created by zcfrank1st on 21/11/2016.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Hades hades = new Hades.HadesBuilder().connections("").build();
        hades.initProject("demo");
        hades.addConf("demo", "hahah", "1");
    }
}
