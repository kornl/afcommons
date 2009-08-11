package org.af.commons.errorhandling;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Simple class to send bug info mails using the Java Mail API
 */

//TODO its no real use to refactor this now, when we have no real good system of sending mails
public class Mailman {
    private String hostPop3 = "pop.gmx.net";
    private String hostSMTP = "mail.gmx.net";
    private String pop3Login = "biostat@gmx.net";
    private String pop3Pass = "vPWqYWv2";
    private String from = "biostat@gmx.net";
    private String to = "biostat@gmx.net";

    private String subjectPrefix = "[Bug]";

    public Mailman() {}

    public Mailman(String to) {
    	this.to = to;
	}

	public void sendErrorMessage(String userName, String email, String otherContact, String desc, List<File> attachedFiles)
            throws MessagingException{
        String subject = subjectPrefix + " " + userName + " : " +
            (desc.length() > 30 ?  desc.substring(0, 30) : desc);
        String body = "Name: " + userName + "\n";
        body += "Email: " + email +       "\n";
        body += "Other contact: " + otherContact +       "\n\n";
        body += "Description:\n\n" + desc +       "\n\n--------------------------------------------\n\n";
        sendMsgPop3(subject, body, attachedFiles);
    }

    public void sendMsgPop3(String subject, String body, List<File> attachedFiles) 
            throws MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", hostSMTP);
        Session session = Session.getDefaultInstance(props, null);
        Store store = session.getStore("pop3");
        store.connect(hostPop3, pop3Login, pop3Pass );

        // Define message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        Multipart mp = new MimeMultipart();
        // create and fill the first message part
        MimeBodyPart mbp1 = new MimeBodyPart();
        mbp1.setText(body);
        mp.addBodyPart(mbp1);

        for (File f:attachedFiles) {
            // create and fill the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            DataSource source = new FileDataSource(f);
            mbp2.setDataHandler(new DataHandler(source));
            mbp2.setFileName(f.getName());
            mp.addBodyPart(mbp2);
        }

        // add the Multipart to the message
        message.setContent(mp);

        // Send message
        Transport.send(message);
        store.close();
    }
}
