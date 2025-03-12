package Arquivo;

public class PilhaPair {
    private NoPilha inicio;

    public PilhaPair() {
        this.inicio = null;
    }

    public void inicializa() {
        this.inicio = null;
    }

    public NoPilha getInicio() {
        return inicio;
    }

    public void setInicio(NoPilha inicio) {
        this.inicio = inicio;
    }

    public void push(int first, int second) {
        this.inicio = new NoPilha(new Pair(first, second), this.inicio);
    }

    public NoPilha pop() {
        NoPilha retorno = this.inicio;
        this.inicio = this.inicio.getProx();

        return retorno;
    }

    public NoPilha top() {
        return this.inicio;
    }
}
