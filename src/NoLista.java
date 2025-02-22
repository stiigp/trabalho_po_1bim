public class NoLista {
    private double valor;
    private NoLista ant, prox;

    public NoLista(double valor, NoLista ant, NoLista prox) {
        this.valor = valor;
        this.ant = ant;
        this.prox = prox;
    }

    public NoLista(double valor, NoLista ant) {
        this(valor, ant, null);
    }

    public NoLista(double valor) {
        this(valor, null, null);
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
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
