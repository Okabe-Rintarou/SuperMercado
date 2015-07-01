/*
   
    Trabalho 2 de Programaçao Orientada a Objetos
    Professor: Adenilso Simao
    Alunos: 
        Luiz Massao Miyazaki    8937080
        Rafael Kenji Nissi      8937013

    Primeiro semestre de 2015
    

*/
package Server;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.Scanner;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class Funcionalidades
{
    private Calendar calendario = Calendar.getInstance();
    
    private int dia = calendario.get(DATE);
    private int mes = calendario.get(MONTH) + 1;
    private int ano = calendario.get(YEAR);
    
    
    private static ArrayList<Cliente> listClientes = new ArrayList<Cliente>();
    private static ArrayList<Produto> listProdutos = new ArrayList<Produto>();
    private static ArrayList<Historico> listHistoricos = new ArrayList<Historico>();
    
    /*
        Imprime na tela o dia mes e ano do servidor
    */
    public void mostraDia()
    {
        System.out.println(this.dia);
        
        System.out.println(this.mes);
        
        System.out.println(this.ano);
    }
    
    /*
        Realiza a venda dos produtos para um determinado cliente
    */
    public synchronized void vendeProduto(String nome, Socket cliente)
    {
        
        PrintStream saida = null;
        Scanner entrada = null;
        int escolha;
        String id;
        
        try
        {
            saida = new PrintStream(cliente.getOutputStream());
            entrada = new Scanner(cliente.getInputStream());
        }
        catch (IOException ex)
        {
            System.out.println("Falhou para tentar mandar mensagens!");
        }
        
        
        
        for(Produto aux : listProdutos)
        {
            if(aux.getNome().equals(nome))
            {
                
                saida.println(aux.getNome());
                saida.println(aux.getPreco());
                saida.println(aux.getDiaV());
                saida.println(aux.getMesV());
                saida.println(aux.getAnoV());
                saida.println(aux.getFornecedor());
                saida.println(aux.getQuantidade());
                saida.println(aux.getEsgotado());
                
                id = entrada.nextLine(); /////////////////////////////////////////////////////
                
                if(aux.getEsgotado() == true)
                {
                    saida.println("Produto esgotado! Você nao pode compra-lo! Gostaria de ser avisado quando chegar mais produtos? (1 - sim | 2 - nao)");
                    escolha = Integer.parseInt(entrada.nextLine());
                    if(escolha == 1)
                    {
                        for (Cliente aux2 : listClientes) 
                        {
                            if(aux2.getID().equals(id))
                            {
                                aux.insereDMail(aux2.getEmail());
                                //aux.imprimeDMail();
                                break;
                            }
                        }
                        
                    }
                }
                else
                {
                    saida.println("Gostaria de comprar quantos desse produto? ");
                    escolha = Integer.parseInt(entrada.nextLine());
                    
                    if(escolha > 0 && escolha <= aux.getQuantidade())
                    {
                        saida.println("Obrigado pela compra! ");
                        aux.setQuantidade(aux.getQuantidade() - escolha);
                        
                        Funcionalidades.listHistoricos.add(new Historico(aux.getNome(), id, (escolha * aux.getPreco()), this.dia, this.mes, this.ano));
                        if(aux.getQuantidade() == 0)
                        {
                            aux.setEsgotado(true);
                        }
                    }
                    else
                    {
                        saida.println("Compra nao foi efetuada pois excedeu o numero de produtos ");
                    }
                    
                }
                
                break;
                
            }
        }
    }
    
    /* 
        Envia a lista de produtos para o cliente
    */
    public void enviaProdutos(Socket cliente)
    {
        PrintStream saida = null;
        
        try
        {
            saida = new PrintStream(cliente.getOutputStream());
        }
        catch (IOException ex)
        {
            System.out.println("Falhou para tentar mandar mensagens!");
        }
        
        saida.println(listProdutos.size());
        
        for(Produto aux : listProdutos)
        {
            saida.println(aux.getNome());
            saida.println(aux.getPreco());
            saida.println(aux.getDiaV());
            saida.println(aux.getMesV());
            saida.println(aux.getAnoV());
            saida.println(aux.getFornecedor());
            saida.println(aux.getQuantidade());
          
        }
        
        
    }
    
    /*
        Adiciona um novo cliente na lista de clientes
    */
    public void adicionaListaClientes(Cliente c)
    {
        Funcionalidades.listClientes.add(c);
    }
    
    /*  
        Verifica se determinado cliente existe
        True se existe
        False caso contrario
    */
    public boolean procuraCliente(String n, String s)
    {
        for(Cliente aux : Funcionalidades.listClientes)
        {
            if(aux.getID().equals(n))
            {
                if(aux.getSenha().equals(s))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /*  
        Pega os produtos de um arquivo de produtos antigos e joga em
    um array list
    */
    public void preparaListProdutos(String produtos) throws FileNotFoundException
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(produtos));
            String s;
            Produto p;
            int i = 0;
            
            while((s = in.readLine()) != null)  
            {
                String[] values = s.split(",");
                this.listProdutos.add(new Produto(values[0], Float.parseFloat(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), values[5], Integer.parseInt(values[6])));
                for (String value : values) 
                {
                    i++;
                    if(i > 7)
                    {
                        this.listProdutos.get(this.listProdutos.size() - 1).insereDMail(values[i-1]);
                    }
                }
                i = 0;
                //p.insereDMail(s);
            }
            
        }
        
        catch(FileNotFoundException e) 
        {
		System.out.println("File " + produtos + " was not found!");
	}
        
	catch(IOException e) 
        {
		System.out.println("Error reading the file!");
	}
        
    }
    
    /*
        Pega o arquivo de clientes antigos e joga em um array list
    */
    public void preparaListClientes(String clientes) throws FileNotFoundException
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(clientes));
            String s;
            
            while((s = in.readLine()) != null)  
            {
                Cliente c = new Cliente();
                String[] values = s.split(",");
                c.setNome(values[0]);
                c.setEndereço(values[1]);
                c.setTelefone(values[2]);
                c.setEmail(values[3]);
                c.setID(values[4]);
                c.setSenha(values[5]);
                Funcionalidades.listClientes.add(c);
                
                //this.listProdutos.add(new Produto(values[0], Float.parseFloat(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), values[5], Integer.parseInt(values[6])));
            }
        }
        
        catch(FileNotFoundException e) 
        {
		System.out.println("File " + clientes + " was not found!");
	}
        
	catch(IOException e) 
        {
		System.out.println("Error reading the file!");
	}
        
    }
    
    /*
        Pega o arquivo de historico antigo e joga em um array list
    */
    public void preparaHistorico(String historicos) throws FileNotFoundException
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(historicos));
            String s;
            
            while((s = in.readLine()) != null)  
            {
                String[] values = s.split(",");
                this.listHistoricos.add(new Historico(values[0], values[1], Float.parseFloat(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5])));
            }
        }
        
        catch(FileNotFoundException e) 
        {
		System.out.println("File " + historicos + " was not found!");
	}
        
	catch(IOException e) 
        {
		System.out.println("Error reading the file!");
	}
    }
    
    /*
        Salva as alteraçoes de produtos, clientes e historico em arquivos
    .csv e os fecha
    */
    public void fechaArquivo(String produto, String cliente, String historico) throws IOException
    {
        FileWriter arq = new FileWriter(produto);
        PrintWriter pw = new PrintWriter(arq);
        

        for(Produto aux : this.listProdutos)
        {
            ArrayList<String> a;
            a = aux.getDMailList();
                    
            pw.print(aux.getNome() + "," + aux.getPreco() + "," + aux.getDiaV() + "," + aux.getMesV() + "," + aux.getAnoV() + "," + aux.getFornecedor() + "," + aux.getQuantidade());
            for (String aux2 : a) 
            {
                pw.print("," + aux2);
            }
            pw.print("\n");
        }

        arq.close();
        
        FileWriter arq2 = new FileWriter(cliente);
        PrintWriter pw2 = new PrintWriter(arq2);

        for(Cliente aux : this.listClientes)
        {
            pw2.print(aux.getNome() + "," + aux.getEndereço() + "," + aux.getTelefone() + "," + aux.getEmail() + "," + aux.getID() + "," + aux.getSenha() + "\n");
        }

        arq2.close();
        
        FileWriter arq3 = new FileWriter(historico);
        PrintWriter pw3 = new PrintWriter(arq3);

        for(Historico aux : this.listHistoricos)
        {
            pw3.print(aux.getNomeProduto() + "," + aux.getID() + "," + aux.getQuantidade() + "," + aux.getDia() + "," + aux.getMes() + "," + aux.getAno() + "\n");
        }

        arq3.close();
    }
    
    /*
        Adiciona um novo produto
    */
    public void adicionaProduto()
    {
        Scanner s = new Scanner (System.in);
        Scanner t = new Scanner(System.in);
        String nome;
        String semUtilidade;
        float preco;
        int dia;
        int mes;
        int ano;
        String fornecedor;
        int quantidade;
        
        
        
        System.out.println("Digite o nome do produto: ");
        nome = s.nextLine();
        
        for(Produto aux : this.listProdutos)
        {
            if(nome.equals(aux.getNome()))
            {
                System.out.println("Nome ja existe!");
                return;
            }
        }
        
        System.out.println("Digite o preco do produto: ");
        while(!s.hasNextFloat())
        {
            semUtilidade = s.nextLine();
        }
        preco = s.nextFloat();
        
        System.out.println("Digite o dia de vencimento do produto: ");
        while(!s.hasNextInt())
        {
            semUtilidade = s.nextLine();
        }
        dia = s.nextInt();
        
        System.out.println("Digite o mes de vencimento do produto: ");
        while(!s.hasNextInt())
        {
            semUtilidade = s.nextLine();
        }
        mes = s.nextInt();
        
        System.out.println("Digite o ano de vencimento do produto: ");
        while(!s.hasNextInt())
        {
            semUtilidade = s.nextLine();
        }
        ano = s.nextInt();
        
        System.out.println("Digite o fornecedor do produto: ");
        fornecedor = t.nextLine();
        
        System.out.println("Digite a quantidade de produtos: ");
        while(!s.hasNextInt())
        {
            semUtilidade = s.nextLine();
        }
        quantidade = s.nextInt();
        
        this.listProdutos.add(new Produto(nome, preco, dia, mes, ano, fornecedor, quantidade));
        
    }
    
    /*
        Imprime todos os produtos, tantos os disponiveis quanto os esgotados
    */
    public void imprimeTudo()
    {
        for(Produto p : this.listProdutos)
        {
            System.out.println("Nome: " + p.getNome());
            System.out.println("Preco: " + p.getPreco());
            System.out.println("Data de validade: " + p.getDiaV() + "/" + p.getMesV() + "/" + p.getAnoV());
            System.out.println("Fornecedor: " + p.getFornecedor());
            System.out.println("Quantidades: " + p.getQuantidade());
            System.out.println("\n\n");
        }
        
    }
    
    /*
        Imprime o todo o historico de vendas
    e opcionalmente cria um PDF
    */
    public void imprimeTudoHistorico()
    {
        Scanner s = new Scanner (System.in);
        String semUtilidade;
        int escolha;
        int space;
        int page = 0;
        
        System.out.println("Data atual - " + this.dia + "/" + this.mes + "/" + this.ano);
        
        for(Historico p : this.listHistoricos)
        {
            System.out.println("Nome: " + p.getNomeProduto());
            System.out.println("ID: " + p.getID());
            System.out.println("Data de compra: " + p.getDia() + "/" + p.getMes() + "/" + p.getAno());
            //System.out.println("Fornecedor: " + p.getFornecedor());
            System.out.println("Lucro: " + p.getQuantidade());
            System.out.println("\n\n");
        }
        
        System.out.println("Deseja um .pdf de vendas?");
        System.out.println("1 - Sim, das vendas do dia");
        System.out.println("2 - Sim, das vendas do mes");
        System.out.println("3 - Nao");
        while(!s.hasNextInt())
        {
            semUtilidade = s.nextLine();
        }
        escolha = s.nextInt();
        
        if(escolha == 1)
        {
            PDDocument document = new PDDocument();

            PDPage[] blank = new PDPage[10];
            
            for (int i = 0; i < blank.length; i++) 
            {
                blank[i] = new PDPage();
            }
            
            document.addPage(blank[0]);
            PDFont font = PDType1Font.TIMES_ROMAN;

            try 
            {
                PDPageContentStream contentStream = new PDPageContentStream(document, blank[page]);
                contentStream.beginText();
                contentStream.setFont( font, 16 );
                contentStream.moveTextPositionByAmount( 250, 700 );
                contentStream.drawString( "Vendas diarias" );
                contentStream.endText();
                
                space = 650;
                
                for (Historico aux : listHistoricos) 
                {
                    if(aux.getDia() == this.dia && aux.getMes() == this.mes && aux.getAno() == this.ano)
                    {
                        //System.out.println("oioioioioi");
                        contentStream.beginText();
                        contentStream.setFont( font, 12 );
                        contentStream.moveTextPositionByAmount( 50, space );
                        contentStream.drawString("Nome do produto: " + aux.getNomeProduto());
                        contentStream.endText();
                        space -= 15;
                        
                        contentStream.beginText();
                        contentStream.setFont( font, 12 );
                        contentStream.moveTextPositionByAmount( 50, space );
                        contentStream.drawString("ID do comprador: " + aux.getID());
                        contentStream.endText();
                        space -= 15;
                        
                        contentStream.beginText();
                        contentStream.setFont( font, 12 );
                        contentStream.moveTextPositionByAmount( 50, space );
                        contentStream.drawString("Lucro: " + aux.getQuantidade());
                        contentStream.endText();
                        space -= 15;
                        
                        contentStream.beginText();
                        contentStream.setFont( font, 12 );
                        contentStream.moveTextPositionByAmount( 50, space );
                        contentStream.drawString("Data: " + aux.getDia() + "/" + aux.getMes() + "/" + aux.getAno());
                        contentStream.endText();
                        space -= 30;
                        
                        if(space < 0)
                        {
                            page++;
                            document.addPage(blank[page]);
                            contentStream.close();
                            contentStream = new PDPageContentStream(document, blank[page]);
                            
                            contentStream.beginText();
                            contentStream.setFont( font, 16 );
                            contentStream.moveTextPositionByAmount( 250, 700 );
                            contentStream.drawString( "Vendas diarias" );
                            contentStream.endText();
                            space = 650;
                        }
                        
                       // space -= 30;
                    }
                }
                
                //contentStream.endText();
                contentStream.close();
                
            } 
            catch (IOException ex) 
            {
                System.out.println("13421423124323");
            }

            try 
            {
                document.save("Vendas diarias.pdf");
            } 
            catch (COSVisitorException ex) 
            {
                System.out.println("Algo deu errado");
            } 
            catch (IOException ex) 
            {
                System.out.println("Algo deu errado 2");
            }

            try 
            {
                document.close();
            } 
            catch (IOException ex) 
            {
                System.out.println("Deu errado mesmo!");
            }   
        }
        
        if(escolha == 2)
        {
            PDDocument document = new PDDocument();

            PDPage[] blank = new PDPage[10];
            
            for (int i = 0; i < blank.length; i++) 
            {
                blank[i] = new PDPage();
            }
            
            document.addPage(blank[0]);
            PDFont font = PDType1Font.TIMES_ROMAN;

            try 
            {
                PDPageContentStream contentStream = new PDPageContentStream(document, blank[page]);
                contentStream.beginText();
                contentStream.setFont( font, 16 );
                contentStream.moveTextPositionByAmount( 250, 700 );
                contentStream.drawString( "Vendas mensais" );
                contentStream.endText();
                
                space = 650;
                
                for (Historico aux : listHistoricos) 
                {
                    if(aux.getMes() == this.mes && aux.getAno() == this.ano)
                    {
                        //System.out.println("oioioioioi");
                        contentStream.beginText();
                        contentStream.setFont( font, 12 );
                        contentStream.moveTextPositionByAmount( 50, space );
                        contentStream.drawString("Nome do produto: " + aux.getNomeProduto());
                        contentStream.endText();
                        space -= 15;
                        
                        contentStream.beginText();
                        contentStream.setFont( font, 12 );
                        contentStream.moveTextPositionByAmount( 50, space );
                        contentStream.drawString("ID do comprador: " + aux.getID());
                        contentStream.endText();
                        space -= 15;
                        
                        contentStream.beginText();
                        contentStream.setFont( font, 12 );
                        contentStream.moveTextPositionByAmount( 50, space );
                        contentStream.drawString("Lucro: " + aux.getQuantidade());
                        contentStream.endText();
                        space -= 15;
                        
                        contentStream.beginText();
                        contentStream.setFont( font, 12 );
                        contentStream.moveTextPositionByAmount( 50, space );
                        contentStream.drawString("Data: " + aux.getDia() + "/" + aux.getMes() + "/" + aux.getAno());
                        contentStream.endText();
                        space -= 30;
                        
                        if(space < 0)
                        {
                            page++;
                            document.addPage(blank[page]);
                            contentStream.close();
                            contentStream = new PDPageContentStream(document, blank[page]);
                            
                            contentStream.beginText();
                            contentStream.setFont( font, 16 );
                            contentStream.moveTextPositionByAmount( 250, 700 );
                            contentStream.drawString( "Vendas mensais" );
                            contentStream.endText();
                            space = 650;
                        }
                        
                        //System.out.println(space);
                        
                       // space -= 30;
                    }
                }
                
                //contentStream.endText();
                contentStream.close();
                
            } 
            catch (IOException ex) 
            {
                System.out.println("13421423124323");
            }

            try 
            {
                document.save("Vendas mensais.pdf");
            } 
            catch (COSVisitorException ex) 
            {
                System.out.println("Algo deu errado");
            } 
            catch (IOException ex) 
            {
                System.out.println("Algo deu errado 2");
            }

            try 
            {
                document.close();
            } 
            catch (IOException ex) 
            {
                System.out.println("Deu errado mesmo!");
            }   
        }
        
        
        
        
    }
    
    /*
        Imprime todos os produtos esgotados na tela
    */
    public void imprimeTudoEsg()
    {
        for(Produto p : this.listProdutos)
        {
            if(p.getEsgotado() == true)
            {
                //System.out.println(p.getEsgotado());
                System.out.println("Nome: " + p.getNome());
                System.out.println("Preco: " + p.getPreco());
                System.out.println("Data de validade: " + p.getDiaV() + "/" + p.getMesV() + "/" + p.getAnoV());
                System.out.println("Fornecedor: " + p.getFornecedor());
                System.out.println("Quantidades: " + p.getQuantidade());
                System.out.println("\n\n");
            }
            
        }
        
    }
    
    /*
        Imprime todos os produtos disponiveis na tela
    */
    public void imprimeTudoDisp()
    {
        for(Produto p : this.listProdutos)
        {
            if(p.getEsgotado() == false)
            {
                //System.out.println(p.getEsgotado());
                System.out.println("Nome: " + p.getNome());
                System.out.println("Preco: " + p.getPreco());
                System.out.println("Data de validade: " + p.getDiaV() + "/" + p.getMesV() + "/" + p.getAnoV());
                System.out.println("Fornecedor: " + p.getFornecedor());
                System.out.println("Quantidades: " + p.getQuantidade());
                System.out.println("\n\n");
            }
            
        }
        
    }
    
    /*
        Modifica uma informaçao do produto
    */
    public void modificaProduto()
    {
        Scanner s = new Scanner(System.in);
        Scanner t = new Scanner(System.in);
        String nome;
        int escolha = 0;
        int data;
        String semUtilidade;
        
        System.out.println("Digite o nome do produto: ");
        nome = s.nextLine();
        
        for(Produto aux : this.listProdutos)
        {
            if(nome.equals(aux.getNome()))
            {
                System.out.println("O que voce deseja fazer?");
                
                System.out.println("1 - Mudar o nome");
                System.out.println("2 - Mudar o preco");
                System.out.println("3 - Mudar a data de validade");
                System.out.println("4 - Mudar o fornecedor");
                System.out.println("5 - Mudar a quantidade");
                System.out.println("6 - Voltar");
                
                while(!s.hasNextInt())
                {
                    semUtilidade = s.nextLine();
                }
                escolha = s.nextInt();
                
                
                switch(escolha)
                {
                    case 1:
                        System.out.println("Digite o novo nome: ");
                        aux.setNome(t.nextLine());
                        break;
                        
                    case 2:
                        System.out.println("Digite o novo preco: ");
                        while(!t.hasNextInt())
                        {
                            semUtilidade = t.nextLine();
                        }
                        aux.setPreco(t.nextFloat());
                        break;
                        
                        
                    case 3:
                        System.out.println("Digite o novo dia: ");
                        while(!t.hasNextInt())
                        {
                            semUtilidade = t.nextLine();
                        }
                        aux.setDiaV(t.nextInt());
                        
                        System.out.println("Digite o novo mes: ");
                        while(!t.hasNextInt())
                        {
                            semUtilidade = t.nextLine();
                        }
                        aux.setMesV(t.nextInt());
                        
                        System.out.println("Digite o novo ano: ");
                        while(!t.hasNextInt())
                        {
                            semUtilidade = t.nextLine();
                        }
                        aux.setAnoV(t.nextInt());
                        break;
                        
                    case 4:
                        System.out.println("Digite o novo fornecedor: ");
                        aux.setFornecedor(t.nextLine());
                        break;
                        
                    case 5:
                        System.out.println("Digite a nova quantidade: ");
                        while(!t.hasNextInt())
                        {
                            semUtilidade = t.nextLine();
                        }
                        aux.setQuantidade(t.nextInt());
                        if(aux.getQuantidade() == 0)
                        {
                            aux.setEsgotado(true);
                        }
                        else
                        {
                            aux.setEsgotado(false);
                            ArrayList<String> al = aux.getDMailList();
                            for (String a : al) 
                            {
                                Mail.sendMail(a, aux.getNome());
                            }
                            aux.cleanList();
                        }
                        break;
                        
                    default:
                        break;
                }
                
                return;
                
                //break;
            }
        }
        
    }
    
    /*
        Remove um determinado produto do servidor
    */
    public void removerProduto()
    {
        Scanner s = new Scanner(System.in);
        String nome;
        System.out.println("Digite o nome do produto: ");
        
        nome = s.nextLine();
        
        for(Produto aux : this.listProdutos)
        {
            if(nome.equals(aux.getNome()))
            {
                this.listProdutos.remove(aux);
                break;
            }
        }
    }           
    
    /*
        Imprime no servidor todos os clientes
    */
    public void listaClientes()
    {
        for(Cliente aux : Funcionalidades.listClientes)
        {
            System.out.println("Nome: " + aux.getNome());
            System.out.println("Endereco: " + aux.getEndereço());
            System.out.println("Telefone: " + aux.getTelefone());
            System.out.println("E-mail: " + aux.getEmail());
            System.out.println("ID: " + aux.getID());
            System.out.println("Senha: " + aux.getSenha());
            System.out.println("\n\n");
        }
    }
    
    
    
}
