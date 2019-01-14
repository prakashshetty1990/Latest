package Utilities;



import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class Mailing extends Common
{
	public Mailing() {}

	public String[] getArrayOfEmails(String keyword)
	{
		String configProperty = getConfigProperty(keyword);
		if (configProperty.length() != 0)
		{
			String[] arr = configProperty.split(";");
			writeToLogFile("INFO", "Splitting email addresses : " + keyword);
			for (int i = 0; i < arr.length; i++)
				writeToLogFile("INFO", "Email :" + (i + 1) + " \"" + arr[i] + "\"");
			return arr;
		}

		writeToLogFile("INFO", "No email addresses found for : " + keyword);
		return null;
	}


	public void sendMail()
	{
		final String host = getConfigProperty("HostName");
		final String user = getConfigProperty("SenderMailId");		      	
		final String suiteName = getConfigProperty("SuiteName");
		final String[] to = getArrayOfEmails("ToEmail");
		final String[] cc = getArrayOfEmails("CcEmail");
		Properties props = new Properties();
	    props.put("mail.smtp.host", host);

	    Session session = Session.getDefaultInstance(props, null);
	      session.setDebug(false);
	      
	      
		try {
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("AutomationExecutionReport<" + user + ">"));
			for (int i = 0; i < to.length; ++i) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
			}
			for (int i = 0; i < cc.length; ++i) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
			}
			final int totalExecuted = testFailureCount + testSkippedCount + testSuccessCount;
		//	message.setSubject(String.valueOf(suiteName) + " Test | " + testSuccessCount * 100 / totalExecuted + "% Pass | " + timeStamp);
			String strApp = getConfigProperty("Application");
			message.setSubject(strApp+" Automation Execution results");
			BodyPart messageBodyPart = (BodyPart)new MimeBodyPart();
		//	messageBodyPart.setText(String.valueOf(suiteName.toUpperCase()) + " TEST REPORT" + '\n' + '\n' + '\n' + testSuccessCount * 100 / totalExecuted + "% Passed" + '\n' + testFailureCount * 100 / totalExecuted + "% Failed" + '\n' + '\n' + "Total Executed-" + (testFailureCount + testSkippedCount + testSuccessCount) + '\n' + "Total Passed-" + testSuccessCount + '\n' + "Total Failed-" + testFailureCount + '\n' + "Total Skipped-" + testSkippedCount);			
			messageBodyPart.setText("Please find the attached zip file for the execution results of "+strApp);
			final Multipart multipart = (Multipart)new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = (BodyPart)new MimeBodyPart();
			final String filename = String.valueOf(timeStamp) + ".zip";
			final DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
	     	messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			writeToLogFile("INFO", "<<<<<<<<<<<<<Please Wait>>>>>>>>>>>>>>>>>");
			Transport.send(message);
			writeToLogFile("INFO", "Mail sent successfully...");
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
