package chatComSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;

        try {
            servidor = new ServerSocket(9999);
            System.out.println("Servidor startado");

            while(true){
                Socket cliente = servidor.accept();
                new GerenciadorDeClientes(cliente);
            }


        } catch (IOException e) {
            System.err.println("A porta esta indisponivel");
            try {
                if(servidor != null) {
                    servidor.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }











    }
}
