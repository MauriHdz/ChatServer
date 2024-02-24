import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Hilo extends Thread{

    private Socket socket;
    private BufferedReader entrada ;
    private BufferedWriter salida;
    private String nombreCliente; // Agrega un campo para el nombre del cliente

    private static ArrayList<Hilo> hilos = new ArrayList<Hilo>();
    private static int contadorClientes = 0; // Agrega un contador para los clientes

    public Hilo(Socket socket){ // Elimina el parámetro para el nombre del cliente
        this.socket = socket;
        this.nombreCliente = "Cliente" + (++contadorClientes); // Asigna un nombre único a cada cliente
        try {
            entrada = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            salida = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hilos.add(this);
    }

    public void recibirMensajes(){
        String lectura;
        while(true){
            try {
                lectura = entrada.readLine();
                System.out.println(lectura);
                enviarMensajes(lectura);
            } catch (IOException e) {

            }
        }
    }

    public void enviarMensajes(String mensaje){
        for(Hilo h : hilos){
            if(h != this){
                try {
                    h.salida.write("Usuario "+ this.nombreCliente + ": "+ mensaje); // Usa el nombre del cliente en lugar de la dirección IP
                    h.salida.newLine();
                    h.salida.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void run(){
        recibirMensajes();
    }

    public void start() {
        recibirMensajes();
    }
}
