package utils;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class JavaMailUtil {
    public static boolean sendOtpMail(String toEmail, String otp) {
        final String fromEmail = "thuatluu2003@gmail.com";
        final String password = "yrly hnue gmwv gfto"; // app password hoặc mật khẩu Gmail

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // hoặc 465 nếu SSL
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "TrainTicket System"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã xác thực đổi mật khẩu - TrainTicket");
            message.setText("Mã OTP của bạn là: " + otp + "\nMã có hiệu lực trong 10 phút.");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
