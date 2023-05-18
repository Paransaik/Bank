package com.back.miru.model.service;

import com.back.miru.model.dao.UserDAO;
import com.back.miru.model.dto.Interest;
import com.back.miru.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDao;

    @Value("${external.email.username}")
    String username;

    @Value("${external.email.password}")
    String password;


    @Autowired
    private UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public int checkId(String id) throws Exception {
        return userDao.checkId(id);
    }

    @Override
    public void registerUser(Map<String, String> map) throws Exception {
        map.put("salt", randomGenerateString(16));
        userDao.registerUser(map);
    }

    @Override
    public void updateUser(Map<String, String> map) throws Exception {
        map.put("salt", randomGenerateString(16));
        userDao.updateUser(map);
    }

    @Override
    public void deleteUser(String id) throws Exception {
        userDao.deleteUser(id);
    }

    @Override
    public User infoUser(String id) throws Exception {
        return userDao.infoUser(id);
    }

    @Override
    public User loginUser(String id, String password) throws Exception {
        return userDao.loginUser(id, password);
    }

    @Override
    public int checkPasswordFind(String id, String email) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("email", email);
        int cnt = userDao.checkPasswordFind(map);
        if (cnt == 1) {
            map.put("salt", randomGenerateString(16));
            String newPassword = randomGenerateString(5);
            map.put("password", newPassword);
            userDao.updatePassword(map);
            sendMail(email, newPassword);
        }
        return cnt;
    }

    @Override
    public void updatePassword(String id) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("password", randomGenerateString(8));
        map.put("salt", randomGenerateString(16));
        userDao.updatePassword(map);
    }

    @Override
    public boolean resisterInterest(Map<String, String> map) throws Exception {
        return userDao.registerInterest(map) == 1;
    }

    @Override
    public boolean deleteInterest(Map<String, String> map) throws Exception {
        return userDao.deleteInterest(map) >= 1;
    }

    @Override
    public List<Interest> getInterestList(String id) throws Exception {
        return userDao.getInterestList(id);
    }

    public String randomGenerateString(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }


    public void sendMail(String to, String newPassword) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("New message from Miru");

            String content = "<p>Hello " + to.split("@")[0] + "</p>" +
                    "<p>You got a new message from Miru</p>" +
                    "<p style=\"padding:12px;border-left:4px solid #d0d0d0;font-style:italic\">" + newPassword + "</p>" +
                    "<p>Please log in with the password again. Thank you.</p>";
            message.setContent(content, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
