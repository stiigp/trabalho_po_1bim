package Arquivo;

import java.io.IOException;
import java.io.RandomAccessFile;

class Registro
{
    public final int tf=1022;
    private int numero; //4 bytes
    private char lixo[] = new char[tf]; //2044 bytes

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Registro(int numero)
    {
        this.numero=numero;
        for (int i = 0; i < tf ;i++)
            lixo[i] = 'X';
    }

    public void gravaNoArq(RandomAccessFile arquivo)
    {
        try
        {
            arquivo.writeInt(numero);
            for(int i = 0 ; i < tf ; i++)
                arquivo.writeChar(lixo[i]);
        } catch (IOException e){}
    }

    public void leDoArq(RandomAccessFile arquivo)
    {
        try
        {
            numero = arquivo.readInt();
            for(int i=0 ; i < tf ; i++)
                lixo[i]=arquivo.readChar();
        }catch(IOException e){}
    }
    static int length()
    {
//int numero;
//        4 bytes
//char lixo[] = new char[tf]; 2044 bytes
//--------------------------------------
//
//        2048 bytes
        return(2048);
    }
}
