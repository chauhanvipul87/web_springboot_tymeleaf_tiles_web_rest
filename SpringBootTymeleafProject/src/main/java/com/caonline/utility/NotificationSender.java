package com.caonline.utility;

import com.caonline.common.GlobalVariables;

public class NotificationSender {

	//static Logger log = Logger.getLogger(NotificationSender.class);
	
	public static void send(Exception e) {
		StringBuilder exceptionLogs = getIterateStackTraceLog(e);
		sendExceptionEmail(e.getMessage(), exceptionLogs);
	}
	
	
	
	private static StringBuilder getIterateStackTraceLog(Exception e) {
		//log.error("An Exception Occured:",e);
		StringBuilder exceptionLogs = new StringBuilder(e.toString());
		exceptionLogs.append("<br/>");
		StackTraceElement[] elements = e.getStackTrace();  
		/*int displayCount  =  elements.length >= 4 ? 4 : elements.length;*/
		int displayCount  =  elements.length;
		for (int iterator=1; iterator <= displayCount; iterator++){
		   exceptionLogs.append("Class Name:"+elements[iterator-1].getClassName()+" Method Name:"+elements[iterator-1].getMethodName()
		               +" Line Number:"+elements[iterator-1].getLineNumber()+"<br/>");
		   
		 }
//		 log.error(exceptionLogs);
		 return exceptionLogs;
	}
	
	private static void sendExceptionEmail(String responseMsg, StringBuilder exceptionLogs){
		
		String emailHtmlBody = prepareExceptionEmailBody(responseMsg,exceptionLogs);
		String toEmails = GlobalVariables.EMAIL_PROPS.getProperty("mail.smtp.to.exception.mail");
		String[] toEmailsArray = toEmails.split(GlobalVariables.EMAIL_ADDRESS_SEPARATOR);
		new MailSender().sendMail(toEmailsArray, 
			    GlobalVariables.EMAIL_PROPS.getProperty("mail.smtp.from.exception.mail"),"password",
			    GlobalVariables.EMAIL_PROPS.getProperty("mail.smtp.exception.title"), 
			    emailHtmlBody, GlobalVariables.EMAIL_TYPE_HTML, null, false, null, null);
		
	}

	private static String prepareExceptionEmailBody(String responseMsg,StringBuilder exceptionLogs) {
		StringBuilder sb = new StringBuilder("<div>");
		sb.append(" An error has occurred, Kindly look into the details and try to fix it as soon as possible. ");
		sb.append(" <br>");
		sb.append("<br> ");
		sb.append("<div style=\"border: 1px solid #0000ff; width: 100%;font-family:Verdana, Geneva, sans-serif;\"> ");
		sb.append("<table style=\"border-spacing: 0px; width: 100%\" border=\"0\" rowspan=\"0\"> ");
		sb.append("<tbody>");
		sb.append("<tr style=\"color: #fff; background-color: #006aac;\"> ");
		sb.append("<td style=\"padding:5px;font-size:14px;\">Exception Information</td> ");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style=\"font-size:13px;padding:3px;\"> ");
		sb.append(responseMsg);
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr style=\"color: #fff; background-color: #006aac;\">");
		sb.append("<td style=\"padding:5px;font-size:14px;\">StackTrace Log Details</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td style=\"font-size:13px;padding:3px;\"> ");
		sb.append("<div>");
		sb.append(exceptionLogs);
		sb.append("</div>");
		sb.append("</td>");
		sb.append("</tr>"); 
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</div>");
		sb.append("</div>");
		return sb.toString();
	}
}
