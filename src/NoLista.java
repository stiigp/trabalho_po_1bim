public class NoLista {
    private int valor;
    private NoLista ant, prox;

    public NoLista(int valor, NoLista ant, NoLista prox) {
        this.valor = valor;
        this.ant = ant;
        this.prox = prox;
    }

    public NoLista(int valor) {
        this(valor, null, null);
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public NoLista getAnt() {
        return ant;
    }

    public void setAnt(NoLista ant) {
        this.ant = ant;
    }

    public NoLista getProx() {
        return prox;
    }

    public void setProx(NoLista prox) {
        this.prox = prox;
    }
}
