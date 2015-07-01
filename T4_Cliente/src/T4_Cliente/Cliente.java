/*
   
    Trabalho 2 de Programaçao Orientada a Objetos
    Professor: Adenilso Simao
    Alunos: 
        Luiz Massao Miyazaki    8937080
        Rafael Kenji Nissi      8937013

    Primeiro semestre de 2015
    

*/

package T4_Cliente;

/**
 *
 * @author 8937080
 */
public class Cliente 
{
    
    private String nome;
    private String endereço;
    private String telefone;
    private String email;
    private String ID;
    private String senha;
    //private boolean notificar;
   
    public void setNome(String a)
    {
        this.nome = a;
    }
    
    public void setEndereço(String a)
    {
        this.endereço = a;
    }
    
    public void setTelefone(String a)
    {
        this.telefone = a;
    }
    
    public void setEmail(String a)
    {
        this.email = a;
    }
    
    public void setID(String a)
    {
        this.ID = a;
    }
    
    public void setSenha(String a)
    {
        this.senha = a;
    }
    
//    public void setNotificar(boolean a)
//    {
//        this.notificar = a;
//    }
    
    
    /* --------------------------  */
    
    
    public String getNome()
    {
        return this.nome;
    }
    
    public String getEndereço()
    {
        return this.endereço;
    }
    
    public String getTelefone()
    {
        return this.telefone;
    }
    
    public String getEmail()
    {
        return this.email;
    }
    
    public String getID()
    {
        return this.ID;
    }

    public String getSenha()
    {
        return this.senha;
    }
    
//    public boolean getNotificar()
//    {
//        return this.notificar;
//    }
    
}
