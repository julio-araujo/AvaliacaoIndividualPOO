package chatComSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GerenciadorDeClientes extends Thread{

    private Socket cliente;
    private String nomeCliente;
    private static final Map<String, GerenciadorDeClientes> clientes = new HashMap<String, GerenciadorDeClientes>();
    private BufferedReader leitor;
    private PrintWriter escritor;

    public GerenciadorDeClientes(Socket cliente){
        this.cliente = cliente;
        new Thread(this).start();
    }



    @Override
    public void run(){
        try {
            leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            escritor = new PrintWriter(cliente.getOutputStream(), true);
            escritor.println("Escreva seu nome");
            String menssagem = leitor.readLine();
            this.nomeCliente = menssagem.toLowerCase().replace(",","");
            escritor.println(" ola "+this.nomeCliente);
            clientes.put(this.nomeCliente, this);

            while(true) {
                menssagem = leitor.readLine();

                if (menssagem.equalsIgnoreCase("::SAIR")) {
                    this.cliente.close();
                }else if(menssagem.toLowerCase().startsWith("::msg")){
                    String nomeDestinatario = menssagem.substring(5, menssagem.length());
                    GerenciadorDeClientes destinatario = clientes.get(nomeDestinatario);

                    if (destinatario==null){
                        escritor.println("O cliente informado não existe");
                    }else{
                        escritor.println("Digite uma menssagem para "+destinatario.getNomeCliente());
                        destinatario.getEscritor().println(this.nomeCliente+" disse "+ leitor.readLine());

                    }
                //lista os clientes logados
                }else if(menssagem.equals("::listar-clientes")){
                    StringBuffer str = new StringBuffer();
                    for(String c : clientes.keySet()){
                        str.append(c);
                        str.append(",");
                    }
                    str.delete(str.length()-1, str.length());
                    escritor.println(str.toString());
                }

                else {
                    escritor.println("vc disse" + menssagem);
                }
            }

        } catch (IOException e) {
            System.err.println("Conexão cortada");
            e.printStackTrace();
        }

    }


    public PrintWriter getEscritor() {
        return escritor;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }
}
