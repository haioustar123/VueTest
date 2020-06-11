package cn.com.capinfo.ea.example.demo.controller

import cn.com.capinfo.ea.example.demo.domain.core.BaseRole
import cn.com.capinfo.ea.example.demo.domain.core.BaseUser
import cn.com.capinfo.ea.example.demo.domain.core.BaseUserBaseRole
import cn.com.capinfo.ea.example.demo.service.RegisterService
import cn.com.capinfo.ea.plugin.springsecurity.CapinfoSpringSecurityService
import cn.com.capinfo.ea.core.annotation.CapinfoController
import cn.com.capinfo.ea.core.CapinfoSpringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

/**
 * 系统注册控制器
 */


@CapinfoController
class RegisterController {
    @Autowired
    private RegisterService registerService
    @Autowired
    private CapinfoSpringSecurityService capinfoSpringSecurityService

    public String index(HttpServletRequest request, Model model) {
        if (capinfoSpringSecurityService.isLoggedIn()) {
            return "redirect:${CapinfoSpringUtils.getConfiginfo('capinfo.springsecurity.successHandler.defaultTargetUrl')}";
        } else {
            return "/register/index"
        }
    }

    @ResponseBody
    public Map onlineReg(BaseUser baseUser, HttpServletRequest request) {
        Map map = new HashMap();
        if (capinfoSpringSecurityService.isLoggedIn()) {
            map.result = false;
            map.message = "用户已登录系统，无法注册新帐号";
        } else {
            if (request.getParameter("password") != request.getParameter("repassword")) {
                map.result = false;
                map.message = "确认密码与密码输入不相同";
            } else {
                baseUser = registerService.register(baseUser)
                if (baseUser.id && !baseUser.hasErrors()) {
                    map.result = true;
                    map.message = "注册成功";
                } else {
                    map.result = false;
                    Map errors = new HashMap();
                    baseUser.errors.allErrors.each { FieldError error ->
                        String message = CapinfoSpringUtils.getI18nMessage("register.${error.field}.${error.code}", error.arguments.toList(), error.defaultMessage);
                        errors.put(error.field, message);
                    }
                    map.errors = errors;
                    map.message = "注册失败";
                }
            }
        }
        return map;
    }
}

