package de.jservice.kidsgard.Service;

import de.jservice.kidsgard.data.Warning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Amr Reda
 */
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendWarningMail(final Warning email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
       if(email.getEmailList().size()>0){
           for(String receiver : email.getEmailList()){   
        mailMessage.setTo(receiver);
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(email.getContent());
        mailMessage.setFrom(email.getSender());

        javaMailSender.send(mailMessage);}}
    }
    

}
