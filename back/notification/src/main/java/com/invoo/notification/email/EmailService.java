package com.invoo.notification.email;

import com.invoo.global.confirmaccount.ConfirmAccount;
import com.invoo.global.notification.ResetPasswordEmail;
import com.invoo.global.payment.PaymentNotification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.invoo.notification.email.EmailTemplates.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * The type Email service.
 */
@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * Instantiates a new Email service.
     *
     * @param mailSender     the mail sender
     * @param templateEngine the template engine
     */
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Send payment request email.
     *
     * @param payment the payment
     * @throws MessagingException the messaging exception
     */
    @Async
    public void sendPaymentRequestEmail(PaymentNotification payment) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name()
        );
        messageHelper.setFrom("contact@invoo.com");

        final String templateName = PAYMENT_REQUEST.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("recipientName", payment.recipientName());
        variables.put("sessionUrl", payment.sessionUrl());

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_REQUEST.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.addInline("invooLogo", new ClassPathResource("static/images/invoo-logo.png"));
            messageHelper.setTo(payment.recipientEmail());
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", payment.recipientEmail(), templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", payment.recipientEmail());
        }

    }


    /**
     * Send reset password email.
     *
     * @param resetPasswordEmail the reset password email
     * @throws MessagingException the messaging exception
     */
    public void sendResetPasswordEmail(ResetPasswordEmail resetPasswordEmail) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name()
        );
        messageHelper.setFrom("contact@invoo.com");

        final String templateName = RESET_PASSWORD.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("username", resetPasswordEmail.firstname() + " " + resetPasswordEmail.lastname());
        variables.put("resetPasswordLink", resetPasswordEmail.resetPasswordLink());

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(RESET_PASSWORD.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(resetPasswordEmail.email());
            mailSender.send(mimeMessage);
            log.info(String.format("Email successfully sent to %s with template %s ", resetPasswordEmail.email(), templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", resetPasswordEmail.email());
        }
    }

    /**
     * Send confirm account email.
     *
     * @param confirmAccount the confirm account
     * @throws MessagingException the messaging exception
     */
    public void sendConfirmAccountEmail(ConfirmAccount confirmAccount) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name()
        );
        messageHelper.setFrom("contact@invoo.com");

        final String templateName = ACTIVATE_ACCOUNT.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("username", confirmAccount.firstname() + " " + confirmAccount.lastname());
        variables.put("link", confirmAccount.token());

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ACTIVATE_ACCOUNT.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(confirmAccount.email());
            mailSender.send(mimeMessage);
            log.info(String.format("Email successfully sent to %s with template %s ", confirmAccount.email(), templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", confirmAccount.email());
        }

    }
}
