/*
   
    Trabalho 2 de Programaçao Orientada a Objetos
    Professor: Adenilso Simao
    Alunos: 
        Luiz Massao Miyazaki    8937080
        Rafael Kenji Nissi      8937013

    Primeiro semestre de 2015
    

*/
package Server;

/**
 *
 * @author 8937013
 */
class Historico 
{
    private String nomeProduto;
    private String ID;
    private float quantidade;
    private int dia;
    private int mes;
    private int ano;
    
    /*
        Construtor
    */
    public Historico(String nome, String ID, float quant, int dia, int mes, int ano)
    {
        this.nomeProduto = nome;
        this.ID = ID;
        this.quantidade = quant;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }
    
    public String getNomeProduto()
    {
        return this.nomeProduto;
    }
    
    public String getID()
    {
        return this.ID;
    }
    
    public float getQuantidade()
    {
        return this.quantidade;
    }
    
    public int getDia()
    {
        return this.dia;
    }
    
    public int getMes()
    {
        return this.mes;
    }
    
    public int getAno()
    {
        return this.ano;
    }
    
    
    
            
}
