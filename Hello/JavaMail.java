package Hello;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.Properties;
public class JavaMail implements Serializable  {
    public String recipients;
    String subject;
    String emailBody;
    private static final long serialVersionUID = 4L;

    String dateTake;
    public JavaMail(String recipientTake,String subjectTake) {
        this.recipients = recipientTake;
        this.subject=subjectTake;

    }

    public void SendEmail(String emailContent) throws Exception,SendFailedException  {

        this.emailBody=emailContent;
        final String username = "amandavolgz@gmail.com";
        final String password = "*********************";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session;
        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("amandavolgz@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipients)//put the recipients emails here
            );
            message.setSubject(subject);
            message.setText("Hi!,"
                    + "\n" +"\n"+ emailBody +"\n"+"Best Regards!"+"\n"+"your loving,"+"\n"+"Amandi Nimasha.");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
    }

}