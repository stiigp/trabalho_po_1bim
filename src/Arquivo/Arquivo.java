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

    public void insercaoDireta() {
        initComp(); initMov();
        Registro pointer_i = new Registro(0), pointer_j = new Registro(0);
        int i = 0, j, valor_i;

        while (i < filesize()) {
            seekArq(i);
            pointer_i.leDoArq(this.arquivo);
            valor_i = pointer_i.getNumero();
            j = i - 1;

            if (j >= 0) {
                seekArq(j);
                pointer_j.leDoArq(this.arquivo);
            }

            while (j >= 0 && pointer_j.getNumero() > valor_i) {
                comp ++;
                pointer_i.setNumero(pointer_j.getNumero());
//                seekArq(j + 1);
                pointer_i.gravaNoArq(this.arquivo);
                mov ++;

                j --;
                if (j >= 0) {
                    pointer_i = pointer_j;

                    seekArq(j);
                    pointer_j.leDoArq(this.arquivo);
                }
            }

            seekArq(j + 1);
            pointer_i.setNumero(valor_i);
            pointer_i.gravaNoArq(this.arquivo);
            mov ++;
            i ++;
        }
    }
    //demais metodos de ordenacao

    public void bubbleSort() {
        Registro pointer_i = new Registro(0);
        int i, len = filesize() - 1, valor_ant;
        boolean trocou = true;

        initComp(); initMov();

        while (trocou) {
            trocou = false;

            for (i = 0; i < len - 1; i ++) {
                seekArq(i);
                pointer_i.leDoArq(this.arquivo);
                valor_ant = pointer_i.getNumero();
                pointer_i.leDoArq(this.arquivo);

                if (valor_ant > pointer_i.getNumero()) {
                    comp ++;
                    trocou = true;
                    mov += 2;
                    seekArq(i);
                    pointer_i.gravaNoArq(this.arquivo);
                    pointer_i.setNumero(valor_ant);
                    pointer_i.gravaNoArq(this.arquivo);
                }

            }
            len --;
        }
    }


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
