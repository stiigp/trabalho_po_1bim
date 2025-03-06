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
            System.out.print(reg.getNumero() + " ");
            reg.leDoArq(this.arquivo);
        }
        System.out.println();
    }

    public void insercaoDireta() {
        initComp(); initMov();
        Registro reg_i = new Registro(0), reg_j = new Registro(0);
        int i = 0, j;

        while (i < filesize()) {
            seekArq(i);
            reg_i.leDoArq(this.arquivo);
            j = i - 1;

            if (j >= 0) {
                seekArq(j);
                reg_j.leDoArq(this.arquivo);
            }

            while (j >= 0 && reg_j.getNumero() > reg_i.getNumero()) {
                comp ++; mov ++;
//                reg_i.setNumero(reg_i.getNumero());
//                seekArq(j + 1);
                reg_j.gravaNoArq(this.arquivo);


                j --;
                if (j >= 0) {
                    seekArq(j);
                    reg_j.leDoArq(this.arquivo);
                }
            }

            seekArq(j + 1);
            reg_i.gravaNoArq(this.arquivo);
            mov ++;
            i ++;
        }
    }
    //demais metodos de ordenacao

    public void bubbleSort() {
        Registro reg_i = new Registro(0), reg_ant = new Registro(0);
        int i, len = filesize() - 1;
        boolean trocou = true;

        initComp(); initMov();

        while (trocou) {
            trocou = false;

            for (i = 0; i < len - 1; i ++) {
                seekArq(i);
                reg_ant.leDoArq(this.arquivo);
                reg_i.leDoArq(this.arquivo);

                if (reg_ant.getNumero() > reg_i.getNumero()) {
                    trocou = true;
                    mov += 2;
                    seekArq(i);
                    reg_i.gravaNoArq(this.arquivo);
                    reg_ant.gravaNoArq(this.arquivo);
                }
                comp ++;
            }
            len --;
        }
    }

    public void selectionSort() {
        Registro reg_i = new Registro(0), pointer_j = new Registro(0), pointer_menor = new Registro(0);
        int menor, pos_menor, i, j;

        initMov(); initComp();

        for (i = 0; i < filesize(); i ++) {
            seekArq(i);
            reg_i.leDoArq(this.arquivo);
            menor = reg_i.getNumero();
            pos_menor = i;

            j = i + 1;

            while (j < filesize()) {
                pointer_j.leDoArq(this.arquivo);

                if (pointer_j.getNumero() < menor) {
                    menor = pointer_j.getNumero();
                    pos_menor = j;
                }
                comp ++;
                j ++;
            }

            seekArq(pos_menor);
            pointer_menor.leDoArq(arquivo);
            seekArq(pos_menor);
            reg_i.gravaNoArq(arquivo);
            seekArq(i);
            pointer_menor.gravaNoArq(arquivo);

            mov += 2;
        }
    }

    public void shakerSort() {
        Registro reg_ant = new Registro(0), reg_prox = new Registro(0);
        int indice_comeco = 0, indice_fim = filesize() - 1, valor_ant, i, valor_prox;
        boolean trocou = true;

        initComp(); initMov();

        while (trocou && indice_comeco < indice_fim) {
            trocou = false;

            i = indice_comeco;
            while (i < indice_fim){
                seekArq(i);
                reg_ant.leDoArq(this.arquivo);
                reg_prox.leDoArq(this.arquivo);

                if (reg_ant.getNumero() > reg_prox.getNumero()) {
                    // realiza o swap
                    trocou = true;
                    mov += 2;
                    seekArq(i);
                    reg_prox.gravaNoArq(this.arquivo);
                    reg_ant.gravaNoArq(this.arquivo);
                }
                comp ++;

                i ++;
            }

            indice_fim --;

            if (trocou) {
                trocou = false;

                i = indice_fim;
                while (i > indice_comeco) {
                    seekArq(i - 1);
                    reg_prox.leDoArq(arquivo);
                    reg_ant.leDoArq(this.arquivo);

                    if (reg_ant.getNumero() < reg_prox.getNumero()) {
                        trocou = true;
                        mov += 2;
                        seekArq(i - 1);
                        reg_ant.gravaNoArq(this.arquivo);
                        reg_prox.gravaNoArq(this.arquivo);
                    }
                    comp ++;
                    i --;
                }
                indice_comeco ++;
            }
        }
    }

    public void heapSort() {
        int tl = filesize(), pai, fe, fd, maior_f;
        Registro reg_fe = new Registro(0), reg_fd = new Registro(0), reg_pai = new Registro(0), reg_maior_f = new Registro(0);


        while (tl > 1) {
            pai = tl / 2 - 1;

            while (pai >= 0) {
                fe = pai * 2 + 1;
                fd = fe + 1;

                seekArq(pai);
                reg_pai.leDoArq(arquivo);

                seekArq(fe);
                reg_fe.leDoArq(arquivo);
                reg_maior_f = reg_fe;
                maior_f = fe;

                if (fd < tl) {
                    reg_fd.leDoArq(arquivo);
                    if (reg_fd.getNumero() > reg_fe.getNumero()) {
                        reg_maior_f = reg_fd;
                        maior_f = fd;
                    }
                }

                if (reg_maior_f.getNumero() > reg_pai.getNumero()) {
                    seekArq(maior_f);
                    reg_pai.gravaNoArq(arquivo);
                    seekArq(pai);
                    reg_maior_f.gravaNoArq(arquivo);
                }

                pai --;
            }

            seekArq(0);
            reg_pai.leDoArq(arquivo);
            seekArq(tl - 1);
            reg_maior_f.leDoArq(arquivo);

            seekArq(0);
            reg_maior_f.gravaNoArq(arquivo);
            seekArq(tl - 1);
            reg_pai.gravaNoArq(arquivo);

            tl --;
        }

    }

    // tornar privado depois
    // partição *com pivô*, partição de lomuto, usa último elemento como pivô
    private int particiona(int ini, int fim) {
        int i = ini - 1, j = ini;
        Registro reg_i = new Registro(0), reg_j = new Registro(0), reg_pivo = new Registro(0);

        seekArq(fim - 1);
        reg_pivo.leDoArq(this.arquivo);

        while (j < fim - 1) {
            // j vai representar o elemento andando no arquivo, o que vamos usar para fazer as comparações com o pivô
            // enquanto isso, i vai representar o limiar da sublista de elementos menores que o pivô
            seekArq(j);
            reg_j.leDoArq(this.arquivo);

            if (reg_j.getNumero() < reg_pivo.getNumero()) {
                // vamos colocar o elemento de j como o último elemento da sublista de elementos menores que o pivô
                // e incrementar o i (que representa até onde essa sublista vai)

                i ++;

                seekArq(i);
                reg_i.leDoArq(this.arquivo);
                seekArq(i);
                reg_j.gravaNoArq(this.arquivo);
                seekArq(j);
                reg_i.gravaNoArq(this.arquivo);

            }
            j ++;
        }
        // i tem que ser incrementado, pois dentro do loop o i é incrementado antes da troca, então aqui ele tem que ser incrementado também
        i ++;

        seekArq(i);
        reg_i.leDoArq(this.arquivo);
        seekArq(i);
        reg_pivo.gravaNoArq(this.arquivo);
        seekArq(fim - 1);
        reg_i.gravaNoArq(this.arquivo);

        // retorna para usarmos como o meio nas chamadas recursivas
        return i;
    }

    public void quickSort(int ini, int fim) {
        int meio;

        if (ini < fim) {
            meio = particiona(ini, fim);
            quickSort(ini, meio);
            quickSort(meio + 1, fim);
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
