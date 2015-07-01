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

/**
 *
 * @author 8937080
 */
public class Main 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        FuncionalidadesCliente func = new FuncionalidadesCliente();
        int escolha;
        
        try
        {
            Socket client = new Socket("127.0.0.1", 12345);
            System.out.println("Conectado!");
            
            PrintStream outp = new PrintStream(client.getOutputStream()); // manda para o servidor
            Scanner inp = new Scanner(client.getInputStream()); // recebe do servidor
            
            while(1 == 1)
            {
                //System.out.println(func.getLogged());
                escolha = func.menuCriaOuLogin();
                
                
                
                if(func.getLogged() == true)
                {
                    switch(escolha)
                    {
                        case 1: //cria novo usuario
                            outp.println(escolha);
                            Cliente c = new Cliente();
                            func.cadastraUsuario(c, client);
                            break;


                        case 2: //desloga
                            func.logoff();
                            break;

                        case 3: //lista produtos
                            outp.println(escolha);
                            func.receberProdutos(client);
                            break;
                            
                        case 4: //compras
                            outp.println(escolha);
                            func.comprarProduto(client);
                            break;

                    }
                }
                
                
                
                else
                {
                    switch(escolha)
                    {
                        case 1: //cadastra novo usuario
                            outp.println(escolha);
                            Cliente c = new Cliente();
                            func.cadastraUsuario(c, client);
                            break;


                        case 2: //tenta logar
                            outp.println(escolha);
                            func.tentaLogar(client);
                            break;

                        case 3: //lista produtos
                            outp.println(escolha);
                            func.receberProdutos(client);
                            break;
                            
                        case 4:
                            break;

                    }
                }
                
                
                
                
                
                
                
            }
            
            //outp.println("oioioioioioi");
            
            
        }
        catch(IOException e)
        {
            System.out.println("Nao conseguiu conectar com o servidor!");
            System.out.println("Erro: " + e.getMessage());
        }
        
        
        
    }
        
}
