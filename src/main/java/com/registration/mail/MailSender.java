package com.registration.mail;

import org.springframework.stereotype.Repository;


public interface MailSender {
    public void send(String to,String mail);
}
