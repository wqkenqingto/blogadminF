package com.blogadmin.sys.controller;

import com.blogadmin.core.controller.BaseController;
import com.blogadmin.sys.model.User;
import com.blogadmin.common.constant.SessionKeyConstant;
import com.blogadmin.sys.service.impl.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Properties;

/**
 * 安全中心
 * Created by 王雪莹 on 2016/10/9.
 */
@Controller
@RequestMapping("/sys/secutcent")
public class SecurityCenter extends BaseController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public String index() {
        return getNameSpace() + "index";
    }
    @RequestMapping(value = "modify.htm", method = RequestMethod.GET)
    public String modify(HttpServletRequest request, HttpServletResponse response) {
        String validate_flag = request.getSession().getAttribute("validatecode_result").toString();
        request.getSession().setAttribute("validatecode_result","false");
        if("true".equals(validate_flag)){
            return  getNameSpace() + "modify";
        }else {
            return  getNameSpace() + "index";
        }
    }

    /**
     * 判断验证码是否正确
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "validatecode.htm")
    public void validatecode(HttpServletRequest request, HttpServletResponse response) {

        String identifyingCode = null;
        if( request.getSession().getAttribute("identifyingCode") != null){
            identifyingCode = request.getSession().getAttribute("identifyingCode").toString();
        }
        String validatecode = "";
        if( request.getParameter("validatecode") != null){
            validatecode = request.getParameter("validatecode").toString();
        }

        // 验证成功or失败 将结果返回
        if(identifyingCode != null && validatecode != null && validatecode.equals(identifyingCode)){
            request.getSession().setAttribute("validatecode_result", "true");
            super.printJson(response, "true");
        }else {
            request.getSession().setAttribute("validatecode_result", "false");
            super.printJson(response, "false");
        }
    }
    @RequestMapping(value = "commit.htm", method = RequestMethod.POST)
    public void commit(HttpServletRequest request, HttpServletResponse response) {
        String identifyingCode = null;
        if( request.getSession().getAttribute("identifyingCode") != null){
            identifyingCode = request.getSession().getAttribute("identifyingCode").toString();
        }
        String validatecode = "";
        if( request.getParameter("validatecode") != null){
            validatecode = request.getParameter("validatecode").toString();
        }

        // 验证成功or失败 将结果返回
        if(identifyingCode != null && validatecode != null && validatecode.equals(identifyingCode)){
            String email = request.getParameter("new_email").toString();
            User user = (User) request.getSession().getAttribute(SessionKeyConstant.USER);
            HashMap<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("id",user.getId());
            paramMap.put("enterprise_email",email);
            userInfoService.updateSecurityInfo(paramMap);
            super.printJson(response, "true");
        }else {
            super.printJson(response, "false");
        }
    }


        /**
         * 邮箱验证码发送
         *
         * @param request
         * @param response
         * @return
         * @throws MessagingException
         */
        @RequestMapping(value = "sendmail.htm")
        public void sendmail(HttpServletRequest request, HttpServletResponse response) throws MessagingException {
            // 获取需要验证的邮箱地址
            String mail = "";
            mail = request.getParameter("mail");
            if(mail == "" || mail == null){
                User user = (User) request.getSession().getAttribute(SessionKeyConstant.USER);
                mail = user.getEnterprise_email();
            }

            // 获取验证码 并存放到session中
            StringBuffer identifyingCode = new StringBuffer();
            //获取6位验证码
            for (int i = 0; i < 6; i++) {
                Long num =(long) (Math.random()*10);
                identifyingCode.append(String.valueOf(num));
            }
            //存放到session
            request.getSession().setAttribute("identifyingCode", identifyingCode);

            final Properties props = new Properties();
            // 表示SMTP发送邮件，需要进行身份验证
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.exmail.qq.com");
            // 发件人的账号
            props.put("mail.user", "yukaiji@mocentre.com");
            // 访问SMTP服务时需要提供的密码
            props.put("mail.password", "Maniykj0703");


            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress(
                    props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人
            InternetAddress to = new InternetAddress(mail);
            message.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件标题
            message.setSubject("摩森特修改密码验证");

            // 设置邮件的内容体
            message.setContent("<h1>您的验证码为"+identifyingCode+"</h1>", "text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
        }
}
