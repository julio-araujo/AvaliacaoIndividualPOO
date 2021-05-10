package chatComSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.attribute.UserPrincipal;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            final Socket cliente = new Socket("127.0.0.1", 9999);

            new Thread(){
                @Override
                public void run(){
                    try {
                        BufferedReader leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

                        while (true){
                            String menssage = leitor.readLine();
                            if(menssage == null || menssage.isEmpty()){
                                continue;
                            }
                            System.out.println("O servidor disse: "+menssage);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            PrintWriter escritor = new PrintWriter(cliente.getOutputStream(), true);
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            String menssagemServidor;
            while( (menssagemServidor = teclado.readLine()) != null){
                if(menssagemServidor == null || menssagemServidor.length() == 0){
                    continue;
                }
                escritor.println(menssagemServidor);
                if(menssagemServidor.equalsIgnoreCase("::SAIR")){
                    System.exit(0);
                }

            }

        } catch (IOException e) {
            System.err.println("Não foi possivel conectar");
            e.printStackTrace();
        }
    }
}
