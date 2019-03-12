package fr.amu.ter_genrest.services;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.Stateless;
import javax.mail.BodyPart;
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

@Stateless
public class SendMail {

	public void sendMail(String firstName, String destinationEmail) {
		String to = destinationEmail;

		// OUR EMAIL SETTINGS
		String host = "smtp.gmail.com";// Gmail
		int port = 465;

		String from = "medem4991@gmail.com"; 
		String password = "*Momodouwa94";// Our Gmail password

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			// From: is our service
			message.setFrom(new InternetAddress(from));
			// To: destination given
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Your webservices is ready !");
			message.setText("Dear "+firstName+", you will find attached your web service");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	
	
	public void sendMailWithAttachment(String firstName, String destinationEmail, String zipPath) {

		String to = destinationEmail;

		// OUR EMAIL SETTINGS
		String host = "smtp.live.com";// Gmail
		int port = 587;

		String from = ""; 
		String password = "";// Our Gmail password

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			// From: is our service
			message.setFrom(new InternetAddress(from));
			// To: destination given
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Your webservices is ready !");
			
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText("Dear "+firstName+", you will find attached your web service");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(zipPath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(zipPath);
			multipart.addBodyPart(messageBodyPart);
	         
			
			// Send the complete message parts
			message.setContent(multipart);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}
