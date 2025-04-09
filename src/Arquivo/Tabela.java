package Arquivo;

import Arquivo.Arquivo;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Tabela {
    RandomAccessFile tabela, backup;

    public Tabela() {
        try {
            tabela = new RandomAccessFile("tabela.txt", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zeraTabela() {
        try {
            tabela = new RandomAccessFile("tabela.txt", "w");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void geraTabela() {
        Arquivo arqRand = new Arquivo("arquivoRand");
        Arquivo auxRand = new Arquivo("arquivoAuxRand");
        Arquivo arqRev = new Arquivo("arquivoReverso");
        Arquivo auxRev = new Arquivo("arquivoAuxReverso");
        Arquivo arqOrd = new Arquivo("arquivoOrdenado");





    }

}
