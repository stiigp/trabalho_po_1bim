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
        Arquivo arq = new Arquivo("arquivo");



    }

}
