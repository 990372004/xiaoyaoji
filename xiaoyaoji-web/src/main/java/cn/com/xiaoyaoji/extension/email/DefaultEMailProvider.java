package cn.com.xiaoyaoji.extension.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.xiaoyaoji.core.util.ConfigUtils;

/**
 * 基于commons email 实现
 *
 * @author zhoujingjie created on 2017/5/18
 */
public class DefaultEMailProvider implements EmailProvider {
    private static Logger logger = LoggerFactory.getLogger(DefaultEMailProvider.class);
    private String hostName = ConfigUtils.getProperty("email.smtp.server");
    private int port = Integer.parseInt(ConfigUtils.getProperty("email.smtp.port"));
    private String username = ConfigUtils.getProperty("email.username");
    private String password = ConfigUtils.getProperty("email.password");
    private String from = ConfigUtils.getProperty("email.from");

    @Override
    public void sendCaptcha(String code, String to) {
        try {
            Session session = getAuthentication();
            // 获取邮件对象
            MimeMessage message = new MimeMessage(session);
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress(from));
            // 设置收件人邮箱地址
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(to) });
            // 设置邮件标题
            message.setSubject("小幺鸡验证码");
            // 设置邮件内容
            message.setText("验证码是：" + code);
            // 得到邮差对象
            Transport transport = session.getTransport();
            // 连接自己的邮箱账户
            transport.connect(from, password);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
        }
    }
//    @Override
//    public void sendCaptcha(String code, String to) {
//        try {
//            Email email = new SimpleEmail();
//            authentication(email);
//            email.setFrom(from);
//            email.setCharset("UTF-8");
//            email.setSubject("小幺鸡验证码");
//            email.setMsg("验证码是：" + code);
//            email.addTo(to);
//            email.send();
//        } catch (EmailException e) {
//            logger.error(e.getMessage(), e);
//        }
//    }

    @SuppressWarnings("unused")
    private void authentication(Email email) {
        email.setHostName(hostName);
        email.setSmtpPort(port);
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSLOnConnect(true);
    }

    private Session getAuthentication() throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", hostName);// 主机名
        properties.put("mail.smtp.port", port);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        return Session.getInstance(properties);
    }

    @Override
    public void findPassword(String findPageURL, String to) {
        try {
            Session session = getAuthentication();
            // 获取邮件对象
            MimeMessage message = new MimeMessage(session);
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress(from));
            // 设置收件人邮箱地址
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(to) });
            // 设置邮件标题
            message.setSubject("找回密码");
            // 设置邮件内容
            String readHTML = "<html><body><a href=\"" + findPageURL + "\">点击找回密码</a></body></html><br />"
                    + "复制地址到浏览器上打开:" + findPageURL;
            message.setContent(readHTML, "text/html; charset=utf-8");
            // 得到邮差对象
            Transport transport = session.getTransport();
            // 连接自己的邮箱账户
            transport.connect(from, password);// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
        }
    }
//    @Override
//    public void findPassword(String findPageURL, String to) {
//        try {
//            HtmlEmail email = new HtmlEmail();
//            authentication(email);
//            email.setCharset("UTF-8");
//            email.addTo(to);
//            email.setFrom(from, "系统管理员");
//            email.setSubject("找回密码");
//            email.setHtmlMsg("<html><body><a href=\"" + findPageURL + "\">点击找回密码</a></body></html>");
//            email.setTextMsg("复制地址到浏览器上打开:" + findPageURL);
//            email.send();
//        } catch (EmailException e) {
//            logger.error(e.getMessage(), e);
//        }
//    }
}
