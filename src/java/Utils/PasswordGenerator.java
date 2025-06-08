/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;


public class PasswordGenerator {

    public static void main(String[] args) {
        // 1. Thay thế mật khẩu này bằng mật khẩu bạn muốn sử dụng.
        String newPassword = "your_new_password_here"; 
        
        // 2. Chạy tệp này và sao chép kết quả đầu ra từ console.
        String hashedPassword = PasswordUtils.hashPassword(newPassword);
        
        System.out.println("Generated Hashed Password:");
        System.out.println(hashedPassword);
    }
} 
