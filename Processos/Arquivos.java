package Processos;

import java.io.IOException;
import ObjetoProcesso.*;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pedro
 */

public class Arquivos {
    
    //private static String arquivoDeProcessos = "C:/Users/pedro/OneDrive/Documentos/NetBeansProjects/Projeto/src/main/java/Processos/entrada.dat";
    //private static String arquivoDeControle = "C:/Users/pedro/OneDrive/Documentos/NetBeansProjects/Projeto/src/main/java/Processos/tamanho.txt";
    
    private static String arquivoDeProcessos = "C:\\Users\\julio\\Desktop\\testePOO\\testePOOcompleto\\src\\Processos\\entrada.dat";
    private static String arquivoDeControle = "C:\\Users\\julio\\Desktop\\testePOO\\testePOOcompleto\\src\\Processos\\tamanho.txt";
    
    
    public static void inicializaControle() throws IOException{
        //Inicializa um arquivo .txt com 0;Falso; se o arquivo estiver vazio. Caso contrário, deixa o valor que já estava lá.
        //Abre o arquivo para leitura.
        File arquivo = new File(arquivoDeControle);
        FileReader arq = new FileReader(arquivo);
        BufferedReader br = new BufferedReader(arq);
        String linha = br.readLine();   //Lê uma linha do arquivo
        br.close(); // Fecha o arquivo;
        arq.close();
        //Se a linha lida for igual a null, o arquivo esta vazio e deve ser iniciado com o valor 0;Falso;.
        if(linha == null){
            //Abre o arquivo para escrita.
            File arquivo1 = new File(arquivoDeControle);
            FileWriter arq1 = new FileWriter(arquivo1);
            BufferedWriter bw = new BufferedWriter(arq1);
            bw.write("0;Falso;");  //Escreve 0;Falso; no arquivo.
            bw.close(); //Fecha o arquivo.
            arq.close();
        }
    }
    
    public static int tamanhoArquivo() throws FileNotFoundException, IOException{
        //Lê o arquivo .txt que contém o tamanho do arquivo de processos e o retorna como um inteiro.
        int tamanho = 0;
            //Abre o arquivo para leitura.
            File arquivo = new File(arquivoDeControle);
            FileReader arq = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(arq);
            String linha;
            linha = br.readLine(); //Lê uma linha do arquivo.
            String[] partes = linha.split(";"); //Faz um split para recuperar o primeiro valor do arquivo, que é referente ao tamanho do arquivo de processos.
            tamanho = Integer.parseInt(partes[0]);  //Transforma a string lida em um inteiro.
            br.close(); //Fecha o arquivo.
            arq.close();
        return tamanho; //Retorna o tamanho com inteiro.
    }
    
    public static <T> void InicializaArquivo(T processo, int tamanho) throws IOException{
        /*
        Inicializa o arquivo com um processo nulo. É  necessário pois o ObjectOutputStream não funciona para multiplas chamadas, appends. 
        Para isso, criamos uma classe chamada de AppendObjectOutputStream que sobrescreve alguns métodos e possibilita futuros appends.
        Assim, após essa inicialização, podemos fazer vários appends sem dar problema.
        */
        //Abre o arquivo para leitura.
        File arquivo0 = new File(arquivoDeControle);
        FileReader arq0 = new FileReader(arquivo0);
        BufferedReader br = new BufferedReader(arq0);
        String linha = br.readLine();   //Lê uma linha do arquivo. 
        String[] partes = linha.split(";"); // Faz um split na linha e guarda os dois valores existentes em um vetor de strings.
        br.close(); //Fecha o arquivo.
        arq0.close();
        if(partes[1].equals("Falso")){  //Se a posição 1 do vetor de strings for igual a Falso, o arquivo de processos ainda não foi inicializado.
            //Abre o arquivo para a escrita.
            FileOutputStream arq;
            arq = new FileOutputStream(arquivoDeProcessos);
            ObjectOutputStream os = new ObjectOutputStream(arq);
            os.writeObject(processo);   //Escreve o objeto inicial, com parâmetros vazios, no arquivo.
            os.flush(); 
            os.close(); //Fecha o arquivo.
        }
        //Abre o arquivo de controle para a escrita afim de atualizar o tamanho do arquivo de processos e colocar a flag como Verdadeiro para indicar qu o arquivo de processos foi inicializado.
        File arquivo1 = new File(arquivoDeControle);
        FileWriter arq1 = new FileWriter(arquivo1);
        BufferedWriter bw = new BufferedWriter(arq1);
        String tam = String.valueOf(tamanho + 1); //Tranforma o tamanho+1 para string.
        String frase = tam.concat(";Verdadeiro;");  //Concatena a string obtida na linha anterior com ;Verdadeiro;.
        bw.write(frase);  //Escreve a frase no arquivo de controle.
        bw.close(); //Fecha o arquivo.
        arq1.close();
        }

    public static <T> void AdicionaProcesso(T processo, int tamanho) throws IOException{
        /*
        Método genérico para fazer append dos processo no arquivo .dat através da classe criada AppendObjectOutputStream.
        Também atualiza o valor do tamanho do arquivo no arquivo no arquivo de controle.
        */
        //Abre o arquivo de processos para append.
        FileOutputStream arq = new FileOutputStream(arquivoDeProcessos, true);
        ObjectOutputStream os1 = new AppendObjectOutputStream(arq);
        os1.writeObject(processo);    //Realiza o append do processo no arquivo.
        os1.flush();
        os1.close();//Fecha o arquivo.
        //Abre o arquivo de controle para a escrita, para atualizar o tamanho do arquivo de processos.
        File arquivo1 = new File(arquivoDeControle);
        FileWriter arq1 = new FileWriter(arquivo1);
        BufferedWriter bw = new BufferedWriter(arq1);
        String tam = String.valueOf(tamanho+1); //Tranforma o tamanho para string,
        String frase = tam.concat(";Verdadeiro;");  //Concatena com ;Verdadeiro;.
        bw.write(frase); //Escreve a frase obtida no arquivo de texto.
        bw.close(); //Fecha o arquivo.
        arq1.close();
    }
    
    public static <T extends Processo> void buscaProcessoNumero (String numero, int tam) throws IOException, ClassNotFoundException{
        //Método estático e genérico para buscar um processo no arquivo de processos através do número do processo.
        //Abre o arquivo de processos para leitura.
        FileInputStream arquivo = new FileInputStream(arquivoDeProcessos);
        ObjectInputStream is = new ObjectInputStream(arquivo);
        int achou = 0;  //Flag para saber se achou o processo procurado.
        for(int i = 0; i < tam; i++){   //Percorre o arquivo.
            T processo = (T) is.readObject();   //Lê objetos nesse arquivo.
            if(processo.getNumeroProcesso().equals(numero)){    //Se o número do processo do objeto lido for igual ao procurado.
                //Printa as informações do processo.
                System.out.printf("Registro %d: ", i+1);
                System.out.println("\nNome do Réu: "+ processo.getReu());
                System.out.println("Nome do Autor do Processo: "+ processo.getAutor());
                System.out.println("Número do Processo: "+ processo.getNumeroProcesso());
                System.out.println("Nome do Advogado do Autor do Processo: "+ processo.getAdvogadoAutor());  
                System.out.println("Nome do Advogado do Réu: "+ processo.getAdvogadoReu());
                if(processo instanceof PessoaFisica)    //Verifica se é um processo de uma pessoa física ou de uma pessoa jurídica.
                    System.out.println("CPF: "+ ((PessoaFisica) processo).getCpf() +"\n");
                else
                    System.out.println("CNPJ: "+ ((PessoaJuridica) processo).getCnpj() +"\n");
                achou = 1; //Altera a flag para indicar que foi encontrado o processo procurado.
                break;  //Sai do loop.
            }
        }
        if(achou == 0){ //Se sair do loop e a flag for igual a 0, indica que chegou ao final do arquivo e o processo procurado não foi encontrado.
            throw new ProcessoNaoEncontradoException(); //Lança excessão de processo não encontrado.
        }
        is.close(); //Fecha o arquivo.
    }
    
    public static <T extends Processo> void buscaProcessoCpf (String cpf, int tam) throws IOException, ClassNotFoundException{
        //Método estático e genérico para buscar um processo no arquivo de processos através do número do CPF.
        //Abre o arquivo de processos para a leitura.
        FileInputStream arquivo = new FileInputStream(arquivoDeProcessos);
        ObjectInputStream is = new ObjectInputStream(arquivo);
        int achou = 0;  //Flag para saber se achou o processo procurado.
        for(int i = 0; i < tam; i++){   //Percorre o arquivo de processos.
            T processo = (T) is.readObject();   //Lê um objeto no arquivo.
            if(processo instanceof PessoaFisica){   //Se o objeto lido for um processo de pessoa físca entra.
                if(((PessoaFisica)processo).getCpf().equals(cpf)){  //Verifica se o CPF do objeto analisa é igual ao procurado.
                    //Printa as informações do processo.
                    System.out.printf("Registro %d: ", i+1);
                    System.out.println("\nNome do Réu: "+ processo.getReu());
                    System.out.println("Nome do Autor do Processo: "+ processo.getAutor());
                    System.out.println("Número do Processo: "+ processo.getNumeroProcesso());
                    System.out.println("Nome do Advogado do Autor do Processo: "+ processo.getAdvogadoAutor());  
                    System.out.println("Nome do Advogado do Réu: "+ processo.getAdvogadoReu());
                    System.out.println("CPF: "+ ((PessoaFisica) processo).getCpf() +"\n");
                    achou = 1;  //Altera a flag para indicar que o processo procura foi encotrado.
                    break;  //Sai do loop.
                }
            }                
        }
        if(achou == 0)  //Se sair do loop e a flag for igual a 0, o arquivo chegou ao fim e o processo não foi encontrado.
            throw new ProcessoNaoEncontradoException(); //Lança excessão de processo não encontrado.
        is.close(); //Fecha o arquivo de processos.
    }
    
    public static <T extends Processo> void buscaProcessoCnpj (String cnpj, int tam) throws IOException, ClassNotFoundException{
        //Método estático e genérico para buscar um processo no arquivo de processos através do número do CNPJ.
        //Abre o arquivo de processos para a leitura.
        FileInputStream arquivo = new FileInputStream(arquivoDeProcessos);
        ObjectInputStream is = new ObjectInputStream(arquivo);
        int achou = 0;  //Flag para saber se o processo procurado foi encontrado.
        for(int i = 0; i < tam; i++){   //Percorre o arquivo.
            T processo = (T) is.readObject();   //Lê um objeto do arquivo.
            if(processo instanceof PessoaJuridica){ //Se o objeto lido for um processo de uma pessoa jurídica ele entra no if.
                if(((PessoaJuridica)processo).getCnpj().equals(cnpj)){  //Verifica se o CNPJ do processo analisado é igual ao procurado.
                    //Printa as informações do processo.
                    System.out.printf("Registro %d: ", i+1);
                    System.out.println("\nNome do Réu: "+ processo.getReu());
                    System.out.println("Nome do Autor do Processo: "+ processo.getAutor());
                    System.out.println("Número do Processo: "+ processo.getNumeroProcesso());
                    System.out.println("Nome do Advogado do Autor do Processo: "+ processo.getAdvogadoAutor());  
                    System.out.println("Nome do Advogado do Réu: "+ processo.getAdvogadoReu());
                    System.out.println("CNPJ: "+ ((PessoaJuridica) processo).getCnpj() +"\n");
                    achou = 1;  //Altera a flag para indicar que o processo procurado foi encontrado.
                    break;  //Sai do loop.
                }
            }                
        }
        if(achou == 0)  //Se saiu do loop e achou for igual a 0, o arquivo chegou ao fim e o processo não foi encontrado.
            throw new ProcessoNaoEncontradoException(); //Lança a excessão de processo não encontrado.
        is.close(); //Fecha o arquivo.
        }
    
    public static <T extends Processo> void imprimeArquivoDeProcessos(int tam) throws IOException, ClassNotFoundException{
        //Método estático e genérico para imprimir todo o arquivo de processos.
        //Abre o arquivo de processos para a leitura.
        FileInputStream arquivo = new FileInputStream(arquivoDeProcessos);
        ObjectInputStream is = new ObjectInputStream(arquivo);
        for(int i = 0; i < tam; i++){   //Percorre o arquivo.
            T processo = (T) is.readObject();   //Lê um objeto desse arquivo.
            if(!processo.getNumeroProcesso().equals("")){   //Se o número do processo não for vazio, para excluir o processo de inicialização.
                //Printa as informações do processo.
                System.out.printf("Registro %d: ", i);
                System.out.println("\nNome do Réu: "+ processo.getReu());
                System.out.println("Nome do Autor do Processo: "+ processo.getAutor());
                System.out.println("Número do Processo: "+ processo.getNumeroProcesso());
                System.out.println("Nome do Advogado do Autor do Processo: "+ processo.getAdvogadoAutor());  
                System.out.println("Nome do Advogado do Réu: "+ processo.getAdvogadoReu());
                if(processo instanceof PessoaFisica)
                    System.out.println("CPF: "+ ((PessoaFisica) processo).getCpf() +"\n");
                else
                    System.out.println("CNPJ: "+ ((PessoaJuridica) processo).getCnpj() +"\n");
            }
        }
        is.close(); //Fecha o arquivo.
    }
        //Trata as excessões possíveis do arquivo de processos.
    
    public static void vizualizar(String numeroProcesso) {

        String lin = ("https://eproc.jfrj.jus.br/eproc/externo_controlador.php?acao=processo_seleciona_publica&acao_origem=processo_consulta_publica&acao_retorno=processo_consulta_publica&num_processo="+numeroProcesso);
        
        try {
            URI link = new URI(lin);
            try {
                Desktop.getDesktop().browse(link);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }   
    }
}