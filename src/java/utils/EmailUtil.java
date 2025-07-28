package utils;

import java.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailUtil {
    public static void sendOrderSuccess(String toEmail, String orderCode, List<String> ticketCodes,
                                        double originalAmount, double discountAmount, double finalAmount) {
        final String username = "thuatluu2003@gmail.com";
        final String password = "yrly hnue gmwv gfto"; // Mật khẩu ứng dụng Gmail

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Java 11+

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username, "Train Ticket", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("[TrainTicket] Xác nhận đặt vé thành công!", "UTF-8");

            StringBuilder sb = new StringBuilder();
            sb.append("<h2>Đặt vé thành công!</h2>");
            sb.append("<b>Mã đơn hàng:</b> ").append(orderCode).append("<br>");
            sb.append("<b>Danh sách mã vé:</b><br>");
            for (String code : ticketCodes) {
                sb.append("&bull; <b>").append(code).append("</b><br>");
            }
            
            sb.append("<br>Cảm ơn bạn đã sử dụng dịch vụ!");

            message.setContent(sb.toString(), "text/html; charset=UTF-8");
            Transport.send(message);

            System.out.println("Đã gửi mail xác nhận đặt vé cho: " + toEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("LỖI gửi mail đặt vé tới: " + toEmail + " --- " + e.getMessage());
        }
    }
    
    
    public static void sendRefundSuccess(String toEmail, String ticketCode, double refundAmount, String passengerName) {
    String subject = "Xác nhận hoàn vé - Mã vé " + ticketCode;

    String content = "Xin chào " + passengerName + ",<br><br>" +
            "Chúng tôi xác nhận rằng vé có mã <b>" + ticketCode + "</b> đã được hoàn thành công.<br>" +
            "Số tiền hoàn lại là: <b>" + String.format("%,.0f VNĐ", refundAmount * 1000) + "</b>.<br><br>" +
            "Cảm ơn bạn đã sử dụng dịch vụ đặt vé tàu của chúng tôi.<br>" +
            "Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ bộ phận hỗ trợ.<br><br>" +
            "Trân trọng,<br>TrainTicket Team";

    sendEmail(toEmail, subject, content);
}

    
    public static void sendEmail(String to, String subject, String htmlContent) {
        final String fromEmail = "thuatluu2003@gmail.com";      // 📌 Thay bằng email gửi
        final String password = "yrly hnue gmwv gfto";          // 📌 Mật khẩu ứng dụng (App password nếu dùng Gmail)

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "587"); 
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable", "true"); 

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            }
        );

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "TrainTicket System")); // Tên người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("✔ Email sent to " + to);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
