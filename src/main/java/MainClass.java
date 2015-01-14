import java.util.ArrayList;
import java.util.List;

/**
 * Created by serb on 14.01.15.
 */
public class MainClass {

    public static void main(String[] args) {
        ((Runnable) () -> {
            Master master = new Master();
            master.spawnShards();
            master.doWork();
        }).run();


        List<SomeObject> list = new ArrayList<>();

        for (int i = 1; i < 11; i++)
            list.add(new SomeObject(i, i * 2));

        System.out.println("creating cient");

        Client client = new Client();


        for (SomeObject ob : list) {
            new Thread((Runnable) () -> {
                System.out.println("writing " + ob.toString());
                Response response = client.write(ob);
                System.out.println(response.getStatus());
            }).start();
        }

        try {
            Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Response response = client.delete(list.get(0));
        System.out.println(response.getStatus());
        System.out.println("\n");

        response = client.getByHash(165);
        System.out.println(response.getStatus());
        System.out.println(response.getObj().toString());
        System.out.println("\n");

        response = client.getByHash(333165);
        System.out.println(response.getStatus());
        System.out.println("\n");


    }

}
