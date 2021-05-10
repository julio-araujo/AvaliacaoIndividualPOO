/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processos;

/**
 *
 * @author pedro
 */
public class ProcessoNaoEncontradoException extends RuntimeException{
    
    public ProcessoNaoEncontradoException(){
        super("Processo não encontrado.");
    }
}
