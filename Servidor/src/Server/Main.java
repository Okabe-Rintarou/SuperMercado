/*
   
    Trabalho 2 de Programaçao Orientada a Objetos
    Professor: Adenilso Simao
    Alunos: 
        Luiz Massao Miyazaki    8937080
        Rafael Kenji Nissi      8937013

    Primeiro semestre de 2015
    

*/

package Server;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException 
    {
        ServerSocket server = new ServerSocket(12345); //cria servidor
        ServidorMenu sm = new ServidorMenu(); //chama o menu principal
        Thread t = new Thread(sm);
        t.start();
                
        //e se  colocar td isso em uma thread?
        while(TrataCliente.getContinua())
        {
            Socket client = server.accept(); //espera de um novo cliente
            //System.out.println("Nova conexao com o cliente " + client.getInetAddress().getHostAddress());
            TrataCliente trata = new TrataCliente(client);
            Thread thread = new Thread(trata);
            thread.start();
        }
    }
}











/*
    Realiza as operaçoes com os clientes
*/
class TrataCliente implements Runnable
{
    private String recebe = null;
    private String nome;
    private String mail;
    private PrintStream saida = null;
    private Scanner entrada = null;
    private Funcionalidades f = new Funcionalidades();
    private static boolean continua = true;
    
    private Socket cliente;
    
    public static boolean getContinua()
    {
        return TrataCliente.continua;
    }
    
    public static void setContinua(boolean b)
    {
        TrataCliente.continua = b;
    }
    
    public TrataCliente(Socket client)
    {
        this.cliente = client;
    }
    
    public void run()
    {
        
        Cliente c;
        
        while(TrataCliente.continua)
        {
            try
            {
                saida = new PrintStream(cliente.getOutputStream());
            }
            catch (IOException ex)
            {
                System.out.println("Falhou para tentar mandar mensagens!");
            }


            try
            {
                entrada = new Scanner(cliente.getInputStream());
            }
            catch (IOException ex)
            {
                System.out.println("Falhou para receber mensagens!");
            }


            while(entrada.hasNextLine() && TrataCliente.continua)
            {
                this.recebe = entrada.nextLine();
                if(Integer.parseInt(this.recebe) == 1)
                {
                    //System.out.println("oi1");
                    c = recebeCliente();
                }
               // System.out.println(this.recebe);


                if(Integer.parseInt(this.recebe) == 2)
                {
                    boolean b;
                    String usuario;
                    String senha;

                    //System.out.println("oi2");
                    usuario = this.entrada.nextLine();
                    senha = this.entrada.nextLine();
                    b = tentarLogar(usuario, senha);
                    saida.println(b);
                    if(b == true)
                    {
                        saida.println(usuario);
                    }
                }
                //System.out.println(this.recebe);

                if(Integer.parseInt(this.recebe) == 3)
                {
                    //System.out.println("oi3");
                    f.enviaProdutos(cliente);
                }
                //System.out.println(this.recebe);

                if(Integer.parseInt(this.recebe) == 4)
                {
                    //System.out.println("oi4");
                    this.nome = entrada.nextLine();
                    //this.mail = entrada.nextLine();
                    f.vendeProduto(nome, cliente);
                }
                //System.out.println(this.recebe);

            }
        }
        
        
    }
    
    /*
        Confere se usuario e senha existem na lista de clientes do servidor
        True existe usuario e senha coincide
        False caso contrario
    */
    public boolean tentarLogar(String usuario, String senha)
    {           
        return (f.procuraCliente(usuario, senha));
        
    }
    
    /*
        Servidor recebe dados de um novo cliente
    */
    public Cliente recebeCliente()
    {
        Cliente c = new Cliente();
        Scanner entrada = null;
        String s;        
        try
        {
            entrada = new Scanner(cliente.getInputStream());
        }
        catch (IOException ex)
        {
            System.out.println("Falhou para receber mensagens!" + ex.getMessage());
        }
        

        c.setNome(entrada.nextLine());
        c.setEndereço(entrada.nextLine());
        c.setTelefone(entrada.nextLine());
        c.setEmail(entrada.nextLine());
        c.setID(entrada.nextLine());
        c.setSenha(entrada.nextLine());
        
        f.adicionaListaClientes(c);

        
        return c;
        
        
        
        
    }
    
    
}









/*
    Thread do menu principal do servidor
*/
class ServidorMenu implements Runnable
{   
    public void run()
    {
        Funcionalidades mercado = new Funcionalidades();
        int flag = 0;
        int escolha = 0;
        String semUtilidade;
        Scanner s = new Scanner(System.in);
        
        try 
        {
            mercado.preparaListProdutos("produtos.csv");
            mercado.preparaListClientes("clientes.csv");
            mercado.preparaHistorico("historicos.csv");
        } 
        catch (FileNotFoundException ex) 
        {
            System.out.println("Erro ao tentar abrir arquivo");
            return;
        }
        
        
        while(flag == 0)
        {           
            System.out.println("Menu do servidor");
            System.out.println("1 - Adicionar um novo produto");
            System.out.println("2 - Modificar informacoes de um produto");
            System.out.println("3 - Listar todos os produtos");
            System.out.println("4 - Listar os produtos disponiveis");
            System.out.println("5 - Listar os produtos esgotados");
            System.out.println("6 - Remover um produto");
            System.out.println("7 - Listar clientes");
            System.out.println("8 - Imprimir histórico");
            System.out.println("9 - Sair do programa");
            
            while(!s.hasNextInt())
            {
                semUtilidade = s.nextLine();
            }
            escolha = s.nextInt();
            
            switch (escolha)
            {
                case 1: //adiciona produto
                    mercado.adicionaProduto();
                    break;
                    
                case 2: //modifica produto
                    mercado.modificaProduto();
                    break;
                            
                case 3:  //imprime todos os produtos
                    mercado.imprimeTudo();
                    break;
                    
                case 4: //imprime produtos disponiveis
                    mercado.imprimeTudoDisp();
                    break;
                    
                case 5: //imprime produtos esgotados
                    mercado.imprimeTudoEsg();
                    break;
                    
                case 6: //remove um determinado produto
                    mercado.removerProduto();
                    break;
                    
                case 7: //imprime na tela todos os clientes
                    mercado.listaClientes();
                    break;
                    
                case 8: //imprime todo o historico
                    mercado.imprimeTudoHistorico();
                    break;
                    
                case 9: //desliga servidor
                    flag = 1;
            
                    try 
                    {
                        mercado.fechaArquivo("produtos.csv", "clientes.csv", "historicos.csv");
                    } 
                    catch (IOException ex) 
                    {
                        System.out.println("Erro ao tentar gravar o arquivo");
                        return;
                    }
                    finally
                    {
                        TrataCliente.setContinua(false);
                        //System.out.println(TrataCliente.getContinua());
                        System.exit(0);
                        return; 
                    }
                    
                    
            }
            
            
        }
        
        
        
    }
}
