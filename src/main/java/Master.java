import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by serb on 13.01.15.
 */
public class Master {

    private static final int PORT = 3333;

    private ExecutorService service = Executors.newFixedThreadPool(10);

    private static final MetaResponse[] META_RESPONSES =
            {new MetaResponse("127.0.0.1", 2001), new MetaResponse("127.0.0.1", 2002)};

    Master() {
        System.out.print("master created\n");
    }

    void spawnShards() {
        System.out.print("spawning shard-thread 0\n");

        new Thread((Runnable) () -> {
            try (ServerSocket serverSocket = new ServerSocket(2001)) {
                while (true) {
                    new Shard("/home/serb/shard0/", serverSocket.accept()).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        System.out.print("spawning shard-thread 1\n");

        new Thread((Runnable) () -> {
            try (ServerSocket serverSocket = new ServerSocket(2002)) {
                while (true) {
                    new Shard("/home/serb/shard1/", serverSocket.accept()).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    void doWork() {
        new Thread((Runnable) () -> {
            try (final ServerSocket serverSocket = new ServerSocket(PORT)) {

                System.out.print("start listening for connections\n");

                while (true) {
                    new Handler(serverSocket.accept()).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ).start();

    }


    private static class Handler extends Thread {

        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {

            System.out.print("connection accepted\n");
            try (ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                while (true) {
                    Object o = oin.readObject();
                    if (null != o) {
                        MetaRequest metaRequest = (MetaRequest) o;
                        System.out.print("meta req: hash = " + metaRequest.getHash() + "\n");
                        MetaResponse metaResponse = META_RESPONSES[metaRequest.getHash() % META_RESPONSES.length];
                        out.writeObject(metaResponse);
                        System.out.print("meta resp sent " + metaResponse.getIpAddress() + ":" + metaResponse.getPort() + "\n");
                        return;
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

