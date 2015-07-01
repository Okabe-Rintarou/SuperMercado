/*
   
    Trabalho 2 de Programaçao Orientada a Objetos
    Professor: Adenilso Simao
    Alunos: 
        Luiz Massao Miyazaki    8937080
        Rafael Kenji Nissi      8937013

    Primeiro semestre de 2015
    

*/
package Server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author 8937080
 */
public class Mail 
{
    private String mail;
    private String produto;
    private int quant;
    
    /*
        Construtor
    */
    public Mail(String mail, String produto, int quant)
    {
        this.mail = mail;
        this.produto = produto;
        this.quant = quant;
    }

    /*
        Envia um email para o endereço de email "mail"
    */
    public static final void sendMail(String mail, String nome) 
    {
        
        final String user = "takeda.void@gmail.com";
        final String toReceive = mail;
        final String key = "miojodenovo32";
        
        //takeda.void@gmail.com
        
        Properties props = new Properties();
        /*
        props.put("mail.smtp.auth", "true"); //autentication
        props.put("mail.smtp.starttls.enble", "true"); //secure protocol
        props.put("mail.stmp.host", "smtp.gmail.com"); //host name
        props.put("mail.smtp.port", "587"); //port mail
        */
        
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
 
        
        //smtp.gmail.com
        //smtp-mail.outlook.com
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, key); //To change body of generated methods, choose Tools | Templates.
            }
        
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(user)); //send mail
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toReceive)); //receive mail
            msg.setSubject("Um produto que voce estava esperando esta a venda!");
            msg.setText(new SimpleDateFormat("dd/MM/YYYY \nhh:mm:ss").format(Calendar.getInstance().getTime()) + "\n\nO produto " + nome + " chegou as lojas! Va comprar antes que acabe!");
            
            Transport.send(msg);
            
                        
        } 
        catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }
        
        
        
    }
    
}
