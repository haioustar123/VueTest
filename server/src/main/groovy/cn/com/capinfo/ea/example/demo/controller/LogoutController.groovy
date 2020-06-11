/**
 * 系统登出控制器
 */
package cn.com.capinfo.ea.example.demo.controller

import cn.com.capinfo.ea.example.demo.domain.core.SystemLoginRecord
import cn.com.capinfo.ea.core.annotation.CapinfoController
import cn.com.capinfo.ea.core.CapinfoSpringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.session.SessionRegistry
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 系统登出控制器
 */
@Transactional
@CapinfoController
class LogoutController {
    @Autowired
    SessionRegistry sessionRegistry
    /**
     * Index action. Redirects to the Spring security logout uri.
     */
    @RequestMapping(value = ["", "index"])
    def index(HttpServletRequest request, HttpServletResponse response) {

        //取消register session
        sessionRegistry.removeSessionInformation(request.session.id);
        SystemLoginRecord systemLoginRecord = SystemLoginRecord.findBySessionId(request.session.id);
        if (systemLoginRecord) {
            systemLoginRecord.logoutTime = new Date();
            systemLoginRecord.save(flush: true);
        }
        response.sendRedirect(request.contextPath + CapinfoSpringUtils.getConfiginfo("capinfo.springsecurity.logout.filterProcessesUrl"))
        // '/logoff'
        response.flushBuffer()
    }
}
