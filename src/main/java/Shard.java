import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Created by serb on 14.01.15.
 */
public class Shard extends Thread {

    private String rootFolder;
    private Socket socket;

    public Shard(String rootFolder, Socket socket) {
        this.rootFolder = rootFolder;
        File file = new File(rootFolder);
        if(!file.exists())
            file.mkdir();
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                Object o = oin.readObject();
                if (null != o) {
                    Request request = (Request) o;
                    switch (request.getCommand()) {
                        case CREATE:
                            if (writeObj(request.getObj())) {
                                out.writeObject(new Response(Status.CREATED));
                            } else
                                out.writeObject(new Response(Status.FAILED));
                            break;
                        case READ:
                            try (ObjectInputStream obin = new ObjectInputStream(new FileInputStream(rootFolder + request.getHash()));) {
                                out.writeObject(new Response(Status.READ, (SomeObject) obin.readObject()));
                            }catch (Exception e){
                                out.writeObject(new Response(Status.FAILED));
                            }
                            break;
                        case DELETE:
                            if (deleteObj(request.getObj())) {
                                out.writeObject(new Response(Status.DELETED));
                            } else
                                out.writeObject(new Response(Status.FAILED));
                            break;
                    }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean deleteObj(SomeObject obj) {
        File file = new File(rootFolder + obj.hashCode());
        if (!file.exists())
            return false;
        else {
            file.delete();
            return true;
        }
    }

    private boolean writeObj(SomeObject obj) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rootFolder + obj.hashCode()));) {
            out.writeObject(obj);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}