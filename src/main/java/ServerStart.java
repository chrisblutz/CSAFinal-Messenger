import com.github.chrisblutz.messenger.networking.MessengerServer;


/**
 * @author Christopher Lutz
 */
public class ServerStart {

    private static MessengerServer server;

    public static void main(String[] args){

        server = new MessengerServer();
        System.out.println("Starting messenger server...");

        try {

            server.start();

        }catch(Exception e){

            e.printStackTrace();
        }
    }
}
