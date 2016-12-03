import com.chaos.hades.client.HadesClient;
import com.chaos.hades.client.HotSwapHadesClient;
import com.chaos.hades.core.HadesProfile;

/**
 * Created by zcfrank1st on 03/12/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        HadesClient client = new HotSwapHadesClient("192.168.33.213:2181", HadesProfile.DEV, "hades");

        while (true) {
            System.out.println(client.getOrElse("hello", ""));
            Thread.sleep(3000L);
        }
    }
}
