import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
    Realizado por Cruz Roman Miguel Angel 20/02/2024
 */
public class Main {
    public static void main(String[] p) {
        if(p.length != 1){
            System.out.println("Debes de especificar un único valor entero que represente el puerto");
            System.exit(-1);
        }

        int puerto = Integer.parseInt(p[0]);

        try {
            ServerSocket server = new ServerSocket(puerto);


            while(true){
                Socket client = server.accept();
                new Hilo(client).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}