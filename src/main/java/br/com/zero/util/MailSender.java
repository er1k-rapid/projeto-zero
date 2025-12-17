package br.com.zero.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {

	private final Session session;

	public MailSender(String host, int port, String username, String password, boolean debug) {
     Properties props = new Properties();
     props.put("mail.smtp.host", host);
     props.put("mail.smtp.port", String.valueOf(port));
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.ssl.protocols", "TLSv1.2");
     props.put("mail.smtp.starttls.enable", "true"); // STARTTLS na porta 587/2525
     props.put("mail.smtp.connectiontimeout", "10000");
     props.put("mail.smtp.timeout", "10000");
     props.put("mail.smtp.user", "b446637b2bc21e");

     this.session = Session.getInstance(props, new Authenticator() {
         @Override protected PasswordAuthentication getPasswordAuthentication() {
             return new PasswordAuthentication(username, password);
         }
     });
     this.session.setDebug(debug); // útil em dev
 }

	public void sendHtml(String from, String to, String subject, String html) throws MessagingException {
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject(subject);
		msg.setContent(html, "text/html; charset=UTF-8");
		Transport.send(msg);
	}

	public void sendHtmlWithAttachment(String from, String to, String subject, String html, java.io.File file)
			throws Exception {
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		msg.setSubject(subject);

		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(html, "text/html; charset=UTF-8");

		MimeBodyPart attachmentPart = new MimeBodyPart();
		attachmentPart.attachFile(file);

		Multipart mixed = new MimeMultipart();
		mixed.addBodyPart(htmlPart);
		mixed.addBodyPart(attachmentPart);

		msg.setContent(mixed);
		Transport.send(msg);
	}

	public static void main(String[] args) throws Exception {
		String host = "sandbox.smtp.mailtrap.io";
		int port = 587; 
		String username = "b446637b2bc21e";
		String password = "98f685f4b22244";

		MailSender mailSender = new MailSender(host, port, username, password, true);

		String from = "eumesmo@meuemail.com";
		String to = "jvsrdez@hotmail.com";
		String subject = "Teste";
		String html = "<h1>Teste</h1><p>Este e-mail é um teste de Email.</p>";
		mailSender.sendHtml( from , to, subject, html);
	}
}
