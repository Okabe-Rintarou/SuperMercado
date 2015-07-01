/*
   
    Trabalho 2 de Programaçao Orientada a Objetos
    Professor: Adenilso Simao
    Alunos: 
        Luiz Massao Miyazaki    8937080
        Rafael Kenji Nissi      8937013

    Primeiro semestre de 2015
    

*/
package Server;

import java.util.ArrayList;

class Produto 
{
    private String nome;
    private float preco;
    private int diaV;
    private int mesV;
    private int anoV;
    private String fornecedor;
    private int quantidade;
    private boolean esgotado;
    private ArrayList<String> listaDMail = new ArrayList<String>();
    
    /*
        Esvazia a lista
    */
    public void cleanList()
    {
        this.listaDMail.clear();
    }
    
    
    public ArrayList getDMailList()
    {
        return this.listaDMail;
    }
    
    /*
        Imprime a lista de email que podem ser
    notificados caso chegue mais produtos.
    */
    public void imprimeDMail()
    {
        for (String listaDMail1 : listaDMail) 
        {
            System.out.println(listaDMail1);
        }
    }
    
    /*
        Insere novo email na lista de emails
    */
    public void insereDMail(String carinha)
    {
        this.listaDMail.add(carinha);
    }
    
    /*
        Construtor
    */
    public Produto(String nome, float preco, int diaV, int mesV, int anoV, String fornecedor, int quantidade)
    {
        this.nome = nome; 
        this.preco = preco;
        this.diaV = diaV;
        this.mesV = mesV;
        this.anoV = anoV;
        this.fornecedor = fornecedor;
        this.quantidade = quantidade;
        
        if(this.quantidade > 0)
        {
            this.esgotado = false;
        }
        else
        {
            this.esgotado = true;
        }
        
        //System.out.println(this.getEsgotado());

    }
    
    public String getNome()
    {
        return this.nome;
    }
    
    public float getPreco()
    {
        return this.preco;
    }
    
    public int getDiaV()
    {
        return this.diaV;
    }
    
    public int getMesV()
    {
        return this.mesV;
    }
    
    public int getAnoV()
    {
        return this.anoV;
    }
    
    public String getFornecedor()
    {
        return this.fornecedor;
    }
    
    public int getQuantidade()
    {
        return this.quantidade;
    }
    
    public boolean getEsgotado()
    {
        return this.esgotado;
    }
     
    
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public void setPreco(float Preco)
    {
        this.preco = Preco;
    }
    
    public void setDiaV(int dia)
    {
        this.diaV = dia;
    }
    
    public void setMesV(int mes)
    {
        this.mesV = mes;
    }
    
    public void setAnoV(int ano)
    {
        this.anoV = ano;
    }
    
    public void setFornecedor(String f)
    {
        this.fornecedor = f;
    }
    
    public void setQuantidade(int q)
    {
        this.quantidade = q;
    }
    
    public void setEsgotado(boolean x)
    {
        this.esgotado = x;
    } 
}
