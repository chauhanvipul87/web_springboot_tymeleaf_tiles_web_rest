package com.caonline.utility;


import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;

import com.caonline.common.GlobalVariables;


public class MailSender extends Authenticator {

	/**
	 * @param to[]
	 * @param from : this will be a username
	 * @param password
	 * @param subject
	 * @param content : will be normal or html form
	 * @param contentType : can be blank or html 
	 * @param attachment[]
	 * @param attachmentRequired : true / false
	 * @param imagesForBody[] : value should be absolute file path, in html img tag src value will be "cid:i" (i=start from 1....etc)
	 * @param extraMap
	 * @return Map<String, String> : key will be SUCCESS / FAILURE
	 */
	public Map<String, String> sendMail(String to[], String from, String password,
			String subject, String content, String contentType, String attachment[], Boolean attachmentRequired, String imagesForBody[], Map<String, Object> extraMap) {
		Map<String, String> returnMap = new HashMap<String, String>();

		final String pass = password;

		String SUCCESS = "SUCCESS";
		String FAILURE = "FAILURE";
		String HTML = "HTML";
		String contentTypeHTML = "text/html";
		
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		String invalidAddress = "Address is not valid.";
		String invalidReceiver = "Receiver is not valid.";
		String invalidSender = "Sender is not valid.";
		String subjectNotBlank = "Subject should not blank.";
		String passValidFiles = "Please pass valid Files:";
		String attachmentRequiredMessage = "Please pass atleast one file.";
		String invalidContent = "Please pass valid content";
		String invalidContentType = "Please pass valid contentType";
		String invalidPassword = "Please pass valid password";
		

		Session session = Session.getInstance(GlobalVariables.EMAIL_PROPS);

		if (StringUtils.isBlank(pass)) {
			throw new IllegalArgumentException(invalidPassword);
		}
		
		// validate from mailid
		if (StringUtils.isBlank(from) || !from.matches(EMAIL_PATTERN)) {
			throw new IllegalArgumentException(invalidSender);
		}
		
		if (to == null) {
			throw new IllegalArgumentException(invalidReceiver);
		}

		if (StringUtils.isBlank(subject)) {
			throw new IllegalArgumentException(subjectNotBlank);
		}
		
		if (content == null) {
			throw new IllegalArgumentException(invalidContent);
		}
		
		if (contentType == null) {
			throw new IllegalArgumentException(invalidContentType);
		}
		

		InternetAddress[] toAddresses = new InternetAddress[to.length];

		// read to mailid and store into InternetAddress object
		for (int i = 0; i < to.length; i++) {

			if (StringUtils.isBlank(to[i]) || !to[i].matches(EMAIL_PATTERN)) {
				throw new IllegalArgumentException(invalidReceiver);
			}

			try {
				toAddresses[i] = new InternetAddress(to[i]);

			} catch (AddressException e) {
				throw new IllegalArgumentException(invalidAddress);
			}
		}

		// Create a multipart message
		Multipart multipart = new MimeMultipart();

		try {

			// creates a new e-mail message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			if (StringUtils.isBlank(contentType)
					|| !HTML.equalsIgnoreCase(contentType)) {
				messageBodyPart.setText(content);
			} else {
				
				// match html string
				messageBodyPart.setContent(content, contentTypeHTML);
			}

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			String fileNotFoundName = StringUtils.EMPTY;
			if(attachmentRequired) {
				
				if(attachment == null) {
					returnMap.put(FAILURE,
							attachmentRequiredMessage);
					return returnMap;
				}
				
				for (String attachmentString : attachment) {
	
					messageBodyPart = new MimeBodyPart();
	
					String fileName = attachmentString;
					File file = new File(fileName);
					if (file.exists()) {
						DataSource source = new FileDataSource(fileName);
						messageBodyPart.setDataHandler(new DataHandler(source));
						String newFileName = fileName.substring(fileName
								.lastIndexOf("\\") + 1);
						if((fileName.lastIndexOf("\\") + 1) <= 0) {
							newFileName = fileName.substring(fileName
									.lastIndexOf("/") + 1);
						}
						messageBodyPart.setFileName(newFileName);
						multipart.addBodyPart(messageBodyPart);
	
					} else {
						fileNotFoundName = "," + file.getName();
					}
	
				}
	
				if (StringUtils.isNotBlank(fileNotFoundName)) {
	
					returnMap.put(FAILURE,
							passValidFiles + fileNotFoundName.substring(1));
					return returnMap;
				}
			}
			
			// code to display images in mail body in html starts
			if(null != imagesForBody && imagesForBody.length > 0) 
			{
				Boolean isFileExist = true;
				for(int i=0;i<imagesForBody.length;i++) {
					if(null != imagesForBody[i]) {
						String filePath = imagesForBody[i];
						if((new File(filePath)).isFile()) {
							
							messageBodyPart = new MimeBodyPart();
							DataSource fds = new FileDataSource(filePath);
		
					        messageBodyPart.setDataHandler(new DataHandler(fds));
					        messageBodyPart.setHeader("Content-ID", "<"+(i+1)+">");
			
						    multipart.addBodyPart(messageBodyPart);
						    
						} else {
							isFileExist = false;
						}
					}
				}
				if(!isFileExist) {
					returnMap.put(FAILURE,
							"Please pass valid files/images.");
					return returnMap;
				}
			}

			// code to display images in mail body in html end
			
			
			// Send the complete message parts
			msg.setContent(multipart);

			// sends the e-mail
			Transport.send(msg);

			returnMap.put(SUCCESS, SUCCESS);
			return returnMap;

		} catch (AuthenticationFailedException e) {
			e.printStackTrace();
			returnMap.put(FAILURE, "Username / Password are invalid.");
			return returnMap;
		} catch (MessagingException e) {
			e.printStackTrace();
			returnMap.put(FAILURE, e.getMessage());
			return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put(FAILURE, e.getMessage());
			return returnMap;
		}
	}
	
	/*public static void main(String[] args) {
		System.out.println(new MailSender().sendMail(new String[]{"chauhanvipul87@gmail.com"}, "itsupport@ianaoffshore.com", "dd", "eqp update location info", 
				   "test", "html", null, false, null, null));
	}*/
}
