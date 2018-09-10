package com.hkt.rms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;

//@Service
public class EmailService {

    @Autowired
    JavaMailSenderImpl javaMailSender;

}
