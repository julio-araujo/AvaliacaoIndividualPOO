/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processos;

import ObjetoProcesso.*;
import java.io.IOException;


/**
 *
 * @author pedro
 */
public class Main {
    public static void main(String[] Args) throws ClassNotFoundException{
        try {
            Arquivos.inicializaControle();
            PessoaFisica inicial = new PessoaFisica("","","","","","");
            Arquivos.InicializaArquivo(inicial, Arquivos.tamanhoArquivo());
            
            PessoaFisica processo = new PessoaFisica("Alfredo", "Pedro", "12333-3212-2332-322", "Gabriel","Jorge", "12312312332");
            PessoaJuridica processo1 = new PessoaJuridica("Marcos", "CocaCola", "12342-242424-2333-3", "Hugo","João", "11209832098");
            Arquivos.AdicionaProcesso(processo, Arquivos.tamanhoArquivo());
            Arquivos.AdicionaProcesso(processo1, Arquivos.tamanhoArquivo());
            
            Arquivos.imprimeArquivoDeProcessos(Arquivos.tamanhoArquivo());
            Arquivos.buscaProcessoCpf("12312312332", Arquivos.tamanhoArquivo());
            Arquivos.buscaProcessoCnpj("11209832098",Arquivos.tamanhoArquivo());
            Arquivos.buscaProcessoNumero("12342-242424-2333-3", Arquivos.tamanhoArquivo());
        } catch (IOException ex) {
            System.out.println("Erro ao executar programa principal");
        }
    }
}
    