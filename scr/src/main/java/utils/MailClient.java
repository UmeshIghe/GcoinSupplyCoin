package utils;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.mail.imap.*;

import java.io.*;

public class MailClient {
	// @Test
	public String checkMail(String toEmailId) {
		// public void checkMail() {
		try {
			Properties props = new Properties();
			props.put("mail.store.protocol", "imaps");

			Session session;
			String code = "";

			session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", "Scrinviteuser@gmail.com",
					"Welcome@123");

			IMAPFolder folder = (IMAPFolder) store.getFolder("inbox");
			folder.open(Folder.READ_WRITE);

			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message message[] = folder.search(unseenFlagTerm);

			int j = message.length - 1;
			for (int i = j; i >= 0; i--) {
				// System.out.println("Subject : " + message[i].getSubject());
				String recipients = message[i]
						.getRecipients(Message.RecipientType.TO)[0].toString();
				if (message[i].getSubject().equals(
						"Responsible Gold Verification Code")
						&& recipients.equals(toEmailId)) {
					// System.out.println("Subject : " +
					// message[i].getSubject());
					// System.out.println("Message " + (i + 1));
					// System.out.println("From : " + message[i].getReplyTo());
					// try {
					// System.out.println("Body: " + message[i].getContent());
					// Multipart mp = (Multipart) message[i].getContent();
					// BodyPart bp = mp.getBodyPart(0);
					// System.out.println("Body: " + bp.getContent());
					// } catch (IOException ex) {
					// System.out.println(ex);
					// }

					Message messages = message[i];
					String contentType = messages.getContentType();
					String messageContent = "";
					if (contentType.contains("multipart")) {
						System.out.println("inside m" + i);
						Multipart multiPart = (Multipart) messages.getContent();
						int numberOfParts = multiPart.getCount();
						for (int partCount = 0; partCount < numberOfParts; partCount++) {
							MimeBodyPart part = (MimeBodyPart) multiPart
									.getBodyPart(partCount);
							messageContent = part.getContent().toString();
							String readMessage = messageContent;
							code = StringUtils.substringBetween(readMessage,
									"Code: ", "</p>");
							System.out.println("inside method" + code);
						}
					} else if (contentType.contains("TEXT")) {
						messageContent = messages.getContent().toString();
						String readMessage = messageContent;

						final Pattern pattern = Pattern
								.compile("<strong>(.+?)</strong>");
						final Matcher matcher = pattern.matcher(readMessage);
						matcher.find();
						System.out.println(matcher.group(1));
						code = matcher.group(1);

					}

				}
			}
			// System.out.println(newMsg);
			folder.close(false);
			store.close();
			return code;

		} catch (MessagingException | IOException e) {
			System.out.println("Error: " + e);
		}
		return null;
	}
}
