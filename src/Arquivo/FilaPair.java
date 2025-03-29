package Arquivo;

public class FilaPair {

    private NoPilha ini;

    public FilaPair() {
        this.ini = null;
    }

    public void enqueue(int first, int second) {
        Pair pair = new Pair(first, second);

        if (ini == null)
            ini = new NoPilha(pair, null);

        else {
            NoPilha no = ini;

            while (no.getProx() != null)
                no = no.getProx();

            no.setProx(new NoPilha(pair, null));
        }
    }

    public Pair dequeue() {
        Pair retorno = this.ini.getPair();

        this.ini = this.ini.getProx();

        return retorno;
    }

    public NoPilha getIni() {
        return ini;
    }

    public void setIni(NoPilha ini) {
        this.ini = ini;
    }
}
