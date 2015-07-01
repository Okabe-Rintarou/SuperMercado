/*
   
    Trabalho 2 de Programaçao Orientada a Objetos
    Professor: Adenilso Simao
    Alunos: 
        Luiz Massao Miyazaki    8937080
        Rafael Kenji Nissi      8937013

    Primeiro semestre de 2015
    

*/
package T4_Cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 
 */
public class FuncionalidadesCliente 
{
    private Scanner s = new Scanner(System.in);
    private boolean logged = false;
    private String id;
    private static FuncionalidadesCliente func = null;
    
    public static synchronized FuncionalidadesCliente getInstance()
    {
        if(FuncionalidadesCliente .func == null)
        {
            FuncionalidadesCliente .func = new FuncionalidadesCliente();
        }
        return FuncionalidadesCliente.func;
    }
    
    
    /*
        Recebe os dados do teclado e armaneza em um cliente.
    */  
    
    public void printID()
    {
        System.out.println(this.id);
    }
    
    public void comprarProduto(Socket client)
    {
        String s;
        String naoInt;
        int quant;
        
        PrintStream outp = null;
        Scanner inp = null;
        Scanner sc = new Scanner(System.in);
        boolean b;
        int escolha;
        
        try 
        {
             outp = new PrintStream(client.getOutputStream());
             inp = new Scanner(client.getInputStream());
        } 
        catch (IOException ex) 
        {
        }
        
        System.out.println("Qual o nome do produto que você deseja comprar? ");
        s = sc.nextLine();
        
        outp.println(s);
        
        
        
        System.out.println("Nome: " + inp.nextLine());
        System.out.println("Preco: " + Float.parseFloat(inp.nextLine()));
        System.out.println("Data de validade: " + Integer.parseInt(inp.nextLine()) + "/" + Integer.parseInt(inp.nextLine()) + "/" + Integer.parseInt(inp.nextLine()));
        System.out.println("Fornecedor: " + inp.nextLine());
        quant = Integer.parseInt(inp.nextLine());
        System.out.println("Quantidade: " + quant);
        b = Boolean.parseBoolean(inp.nextLine());
        outp.println(this.id); //////////////////////////////////////////////////////////////////////
        System.out.println(inp.nextLine());
        if (b == false)
        {
            //System.out.println("1 - Sim");
            //System.out.println("2 - Nao");
            while(!sc.hasNextInt())
            {
                naoInt = sc.nextLine();
            }
            escolha = sc.nextInt();
            outp.println(escolha);
            
            
            System.out.println(inp.nextLine());
                //System.out.println("Obrigado pela compra!");
           
            //return s.nextInt();
            
        }
        else
        {
            while(!sc.hasNextInt())
            {
                naoInt = sc.nextLine();
            }
            escolha = sc.nextInt();
            outp.println(escolha);
        }
        
        
        
        
    }
    
    
    /*
           Desloga um determinado cliente
           Seta a variavel de logado para falso
    */
    public void logoff()
    {
        this.logged = false;
    }
    
    /*
        Retorna o estado de logado ou deslogado
        True logado
        False deslogado
    */
    public boolean getLogged()
    {
        return this.logged;
    }
    
    /*
        Cria um novo usuario e manda os dados para o servidor
    */
    public void cadastraUsuario(Cliente cliente, Socket client)
    {
        boolean antBug = true;
        String pegaLixo;
        int escolha = 0;
        Scanner t = new Scanner(System.in);
        PrintStream outp = null;
        try 
        {
             outp = new PrintStream(client.getOutputStream());
        } 
        catch (IOException ex) 
        {
        }
        
        
        System.out.println("Digite os dados do usuario:\n");
        
        System.out.println("Nome: ");
        cliente.setNome(t.nextLine());
        outp.println(cliente.getNome());
        
        
        
        System.out.println("Endereço: ");
        cliente.setEndereço(t.nextLine());
        outp.println(cliente.getEndereço());
        
        System.out.println("Telefone: ");
        cliente.setTelefone(t.nextLine());
        outp.println(cliente.getTelefone());
        
        System.out.println("Email: ");
        cliente.setEmail(t.nextLine());
        outp.println(cliente.getEmail());
        
        System.out.println("ID: ");
        cliente.setID(t.nextLine());
        outp.println(cliente.getID());
        
        System.out.println("Senha: ");
        cliente.setSenha(t.nextLine());
        outp.println(cliente.getSenha());
          
        System.out.println("\nCliente cadastrado com sucesso!\n");
    }
    
    /*
        Imprime um menu perguntando sobre criar novo usuario ou fazer login
        Retorna um inteiro indicando a opçao
    */
    public int menuCriaOuLogin()
    {
        String naoInt;
        
        if(this.logged == true)
        {
            System.out.println("O que deseja fazer: ");
            System.out.println("1. Criar novo usuario");
            System.out.println("2. Deslogar");
            System.out.println("3. Listar produtos");
            System.out.println("4. Fazer uma compra");
            
            while(!s.hasNextInt())
            {
                naoInt = s.nextLine();
            }
            
            return s.nextInt();
        }
        else
        {
            System.out.println("O que deseja fazer:");
            System.out.println("1. Criar novo usuario.");
            System.out.println("2. Fazer login");
            System.out.println("3. Listar produtos");

            while(!s.hasNextInt())
            {
                naoInt = s.nextLine();
            }

            return s.nextInt();
        }
        
        
    }
    
    /*
        Manda o ID do usuario e a senha, confere se existe no servidor 
        E caso ID e senha sejam iguais seta a variavel logged para true,
    caso contrario seta para false
    */
    public void tentaLogar(Socket client)
    {
        String usuario;
        String senha;
        Scanner t = new Scanner(System.in);
        boolean b;
        
        PrintStream outp = null;
        Scanner inp = null;
        try 
        {
             outp = new PrintStream(client.getOutputStream());
             inp = new Scanner(client.getInputStream());
        } 
        catch (IOException ex) 
        {
        }
        
        System.out.println("Digite o usuário: ");
        usuario = t.nextLine();
        outp.println(usuario);
        
        System.out.println("Digite a senha: ");
        senha = t.nextLine();
        outp.println(senha);
        
        b = Boolean.parseBoolean(inp.nextLine());
        //System.out.println(b);
        //System.out.println("\n\n");
        
        if(b == true)
        {
            //System.out.println("true");
            this.id = inp.nextLine();
            this.logged = true;
            //System.out.println(this.logged);
            //return true;
        }
        else
        {
            //System.out.println("false");
            this.logged = false;
            //System.out.println(this.logged);
            //return false;
        }

        
    }
    
    /*
        Recebe todos os produtos do servidor e imprime na tela.
    */
    public void receberProdutos(Socket client)
    {
        Scanner inp = null;
        int size = 0;
        int i;
        
        
        try 
        {
             inp = new Scanner(client.getInputStream());
        } 
        catch (IOException ex) 
        {
        }
        
        size = Integer.parseInt(inp.nextLine());
        
        System.out.println(size);
        
        for(i = 0; i < size; ++i)
        {
            System.out.println("Nome: " + inp.nextLine());
            System.out.println("Preco: " + Float.parseFloat(inp.nextLine()));
            System.out.println("Data de validade: " + Integer.parseInt(inp.nextLine()) + "/" + Integer.parseInt(inp.nextLine()) + "/" + Integer.parseInt(inp.nextLine()));
            System.out.println("Fornecedor: " + inp.nextLine());
            System.out.println("Quantidade: " + Integer.parseInt(inp.nextLine()));
            System.out.println("\n\n");
        }
        
    }
    
}
