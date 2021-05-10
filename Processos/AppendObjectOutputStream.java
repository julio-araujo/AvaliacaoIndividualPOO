/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Processos;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
/**
 *
 * @author pedro
 */
public class AppendObjectOutputStream extends ObjectOutputStream{
    public AppendObjectOutputStream(FileOutputStream arg0) throws IOException {
        super(arg0);
    }
    @Override
    public void writeStreamHeader() throws IOException
    {
        reset();
    }

}
