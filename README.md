#Trabalho da matéria SSC0103 - Programação Orientada a objetos.

#Introdução 

Nesse trabalho, foi implementado um supermercado com várias funções, como login de usuários, compra de produtos, etc.

#Funcionamento
##O programa inclui 2 modos:

##1 - Servidor 
No qual onde clientes podem se conectar, e onde a pessoa que está com o servidor pode fazer várias coisas, como:

- Adicionar produtos
- Modificar informações (como preço) de um produto
- Listar todos os produtos (todos, os esgotados, e os não esgotados)
- Remover um produto
- Listar os clientes
- Imprimir o histórico (opcionalmente, criar um .pdf com as vendas do dia ou mês).


##2 - Cliente 
O que as pessoas usariam para se conectar ao servidor e fazer várias coisas, como:

- Criar um novo usuário, no qual o servidor irá receber as informações para criar um novo cadastro
- Fazer login (com uma conta já existente)
- Listar todos os produtos da loja
- Fazer uma compra (só possível se a pessoa estiver logada em uma conta)

#Como usar o programa?

##Servidor

- Inicie o servidor
- Escolha um dos items na tela, e aperte o número do item e aperte enter.
- Alguns items irão pedir o nome do produto. Nesses casos, escreva exatamente os mesmos nomes (incluindo letras maiúsculas e acentos).
- Lembre-se de no final fechar o programa (escolha 9 do menu principal), para atualizar os arquivos e impedir que algum cliente conecte.

##Cliente

- Inicie a plicação do cliente
- Se desejar fazer alguma compra, deve se logar primeiro com seu ID e senha. Caso ainda não tenha um login, você pode criar um novo usuário (escolha 1 do menu principal).
- Caso queira apenas ver os produtos, não é necessário fazer login.


#Arquivos de dados

##produtos.csv

Usado para salvar os  produtos do supermercado. Nele é guardado os seguintes items:
- Nome do produto
- Preço
- Data de validade
- Fornecedor
- Quantidade em estoque
- Lista de observers


##clientes.csv

Usado para guardar cadastros de usuários do supermercado. Nele é guardado os seguintes items:
- Nome
- Endereço
- Telefone
- E-mail
- ID
- Senha


##historicos.csv

Usado para salvar o histórico de compras. Nele é guardado os seguintes items:
- Nome do produto comprado
- ID do comprador
- Lucro obtido da venda
- Data da venda


#Extras do trabalho

##Observer - Design Pattern

Usado em conjunto com o sistema de e-mails. Cada produto tem uma lista de observers (portanto Produto é um observable), que quando um produto não tem mais estoque, o cliente pode, opcionalmente, pedir para o site enviar um e-mail para o cliente ser avisado.

##Sistema de enviar e-mail

Como dito acima, o usuário pode receber um e-mail automático quando o servidor ter estoque de um certo produto.

##Criação de .pdf - PDFBox

Na aplicação do servidor, é possível que o usuário tenha um .pdf das vendas do dia ou mês. Para que isso aconteça:

- Aperte 8 no menu principal do servidor para que o histórico de vendas seja mostrado na tela.
- Opcionalmente, o usuário pode pedir para que o programa gere um .pdf com as  vendas do dia ou mês.

##Singleton - Design Pattern

Há classes no trabalho no qual podem existir apenas um de seus objetos. Para facilitar isso, foi usado o design pattern de Singleton.


#Arquivo .jar

##Servidor

O arquivo .jar do servidor estará na pasta "Servidor/src/Server/dist".

##Cliente

O arquivo .jar do servidor estará na pasta "T4_Cliente/src/T4_Cliente/dist"
