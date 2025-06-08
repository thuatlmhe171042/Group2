/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    // !!! QUAN TRỌNG: THAY THẾ CÁC GIÁ TRỊ NÀY !!!
    // Email bạn sẽ sử dụng để gửi.
    private static final String FROM_EMAIL = "buttinpro@gmail.com"; 
    // Mật khẩu ứng dụng của email. (Xem hướng dẫn của Google về cách tạo Mật khẩu ứng dụng nếu bạn bật 2FA).
    private static final String APP_PASSWORD = "oiwz ybra piow ozpx"; 

    public void sendResetPasswordEmail(String toEmail, String token, String requestURL) {
        // Cấu hình thuộc tính cho Gmail SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo phiên làm việc với Authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            
            String subject = "Yeu Cau Dat Lai Mat Khau";
            // Lấy URL cơ sở của ứng dụng và tạo liên kết đặt lại
            // Chú ý: Cần đảm bảo getBaseURL trả về đúng URL của ứng dụng WebApplication2
            String resetLink = getBaseURL(requestURL) + "/resetPassword?token=" + token;

            String emailContent = "<p>Xin chao,</p>"
                                + "<p>Ban da yeu cau dat lai mat khau. Vui long nhan vao lien ket duoi day de tiep tuc:</p>"
                                + "<p><a href=\"" + resetLink + "\">Dat lai mat khau cua ban</a></p>"
                                + "<p>Lien ket nay se het han sau 1 gio.</p>"
                                + "<p>Neu ban khong yeu cau dieu nay, vui long bo qua email nay.</p>"
                                + "<p>Tran trong,</p>"
                                + "<p>Doi ngu ho tro cua ban.</p>";

            message.setSubject(subject);
            message.setContent(emailContent, "text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("Email sent successfully to " + toEmail);

        } catch (MessagingException e) {
            System.err.println("Error sending email to " + toEmail);
            e.printStackTrace();
            // Ném một ngoại lệ runtime để báo hiệu lỗi nghiêm trọng
            throw new RuntimeException("Could not send email. Please check configuration and logs.", e);
        }
    }
    
    private String getBaseURL(String fullURL) {
        try {
            java.net.URL url = new java.net.URL(fullURL);
            // Xây dựng URL cơ sở từ request, không hardcode tên ứng dụng
            String contextPath = "/WebApplication2"; // Lấy từ request.getContextPath() sẽ tốt hơn
            return url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + contextPath;
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }
} 