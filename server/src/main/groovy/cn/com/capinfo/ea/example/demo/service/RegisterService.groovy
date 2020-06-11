package cn.com.capinfo.ea.example.demo.service


import cn.com.capinfo.ea.example.demo.domain.core.BaseRole
import cn.com.capinfo.ea.example.demo.domain.core.BaseUser
import cn.com.capinfo.ea.example.demo.domain.core.BaseUserBaseRole
import cn.com.capinfo.ea.example.demo.service.core.BaseRoleService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@Transactional
class RegisterService {
    @Autowired
    BaseRoleService baseRoleService

    public BaseUser register(BaseUser baseUser) {
        BaseUser.withTransaction { status ->
            try {
                if (baseUser.save(flush: true) && !baseUser.hasErrors()) {
                    BaseRole baseRole = baseRoleService.get('ROLE_USER')
                    if (!baseRole) {
                        baseRole = baseRoleService.create(authority: 'ROLE_USER', description: 'ROLE_USER')
                        baseRole.id = 'ROLE_USER'
                        baseRoleService.save(baseRole)
                    }
                    BaseUserBaseRole userrole = BaseUserBaseRole.create(baseUser, baseRole, true);
                    if (!userrole) {
                        status.setRollbackOnly();
                    }
                }
            } catch (e) {
                status.setRollbackOnly();
            }
        }
        return baseUser
    }
}
