package com.example.my_app.common;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;

@Service
public class SenderEmail {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public boolean handleSenderEmai(String toEmail, String body) throws Exception {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            Context context = new Context();
            context.setVariable("url", body);
            String process = templateEngine.process("emailTemplate.html", context);
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setTo(toEmail);
            helper.setFrom("nguyenhatquang522004@gmail.com");
            helper.setSubject("Code Url");
            helper.setText(process, true);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;

        }

    }
}
