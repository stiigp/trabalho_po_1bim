package Arquivo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Arquivo
{
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;

    public Arquivo(String nomearquivo) {
        try
        {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e)
        { }
    }

    public int getComp() {
        return this.comp;
    }

    public int getMov() {
        return this.mov;
    }

    public void copiaArquivo(RandomAccessFile arquivoOrigem){
        this.arquivo = arquivoOrigem;
    }

    public RandomAccessFile getFile() {
        return this.arquivo;
    }

    public void truncate(long pos) {
        try {
            this.arquivo.setLength(pos * Registro.length());
        } catch (IOException e) {

        }
    }

    public boolean eof() {
        boolean retorno = false;
        try {
            if (arquivo.getFilePointer() == arquivo.length()) {
                retorno = true;
            }
        } catch (IOException e) {

        }

        return retorno;
    }

    public void seekArq(int pos) {
        try {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e) {

        }
    }

    public int filesize() {
        try {
            return (int) (arquivo.length() / Registro.length());
        } catch (IOException e) {
            return -1;
        }
    }

    public void initComp() {
        this.comp = 0;
    }

    public void initMov() {
        this.mov = 0;
    }

    public void exibeArq() {
        Registro reg = new Registro(0);
        reg.leDoArq(this.arquivo);
        while (!this.eof()) {
            System.out.println(reg.getNumero());
            reg.leDoArq(this.arquivo);
        }
    }

    public void insercaoDireta() {}
    //demais metodos de ordenacao


    public void geraArquivoOrdenado(int nRegistros) {
        for (int i = 0; i < nRegistros; i ++) {
            Registro reg = new Registro(i);
            reg.gravaNoArq(this.arquivo);
        }
        this.truncate(nRegistros + 1);
    }

    public void geraArquivoReverso(int nRegistros) {
        for (int i = nRegistros - 1; i >= 0; i --) {
            Registro reg = new Registro(i);
            reg.gravaNoArq(this.arquivo);
        }
        this.truncate(nRegistros + 1);
    }

    public void geraArquivoRandomico(int nRegistros) {
        int[] vet = new int[nRegistros];
        int temp, random_int;
        Random rand = new Random();
        Registro reg = new Registro(0);

        for (int i = 0; i < nRegistros; i ++) {
            vet[i] = i;
        }

        for (int i = 0; i < nRegistros; i ++) {
            random_int = rand.nextInt(nRegistros);

            temp = vet[random_int];
            vet[random_int] = vet[i];
            vet[i] = temp;
        }

        for (int ele: vet) {
            reg.setNumero(ele);
            reg.gravaNoArq(this.arquivo);
        }
        this.truncate(nRegistros + 1);
    }
}
