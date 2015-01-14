import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by serb on 13.01.15.
 */
public class Client {
    private static final String MASTER_IP = "127.0.0.1";
    private static final int MASTER_PORT = 3333;


    private MetaResponse getMetaData(int hash) {
        try(Socket socket = new Socket(MASTER_IP, MASTER_PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());){
            out.writeObject(new MetaRequest(hash));
            while (true) {
                Object o = oin.readObject();
                if (null != o)
                    return (MetaResponse) o;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Response doAction(Command command, SomeObject obj) {
        MetaResponse meta = getMetaData(obj.hashCode());
        try (Socket socket = new Socket(meta.getIpAddress(), meta.getPort());
            ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());){
            ous.writeObject(new Request(command, obj));
            while (true) {
                Object o = oin.readObject();
                if (null != o)
                    return (Response) o;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Response write(SomeObject obj){
        return doAction(Command.CREATE, obj);
    }

    public Response delete(SomeObject obj){
        return doAction(Command.DELETE, obj);
    }

    public Response getByHash(int hash){
        MetaResponse meta = getMetaData(hash);
        try (Socket socket = new Socket(meta.getIpAddress(), meta.getPort());
             ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());){
            ous.writeObject(new Request(Command.READ, hash));
            while (true) {
                Object o = oin.readObject();
                if (null != o)
                    return (Response) o;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



}
