package cn.com.capinfo.ea.example.demo.service.core

import cn.com.capinfo.ea.core.PageParams
import cn.com.capinfo.ea.example.demo.domain.core.BaseUser
import cn.com.capinfo.ea.example.demo.domain.core.BaseRole
import cn.com.capinfo.ea.example.demo.domain.core.BaseUserBaseRole
import groovy.util.logging.Slf4j
import org.grails.datastore.mapping.query.api.BuildableCriteria
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
@Transactional
class BaseUserBaseRoleService {
    public long count(Closure logic) {
        BuildableCriteria buildableCriteria = BaseUserBaseRole.createCriteria()
        logic.delegate = buildableCriteria
        long count = buildableCriteria.count {
            logic()
        }
        return count
    }

    public List list(PageParams pageParams, Closure logic) {
        BuildableCriteria buildableCriteria = BaseUserBaseRole.createCriteria()
        logic.delegate = buildableCriteria
        List list = buildableCriteria.list {
            logic()
            order(pageParams.sort, pageParams.order)
            maxResults(pageParams.max)
            firstResult(pageParams.offset)
        }
        return list;
    }

    public void authRoleToUser(BaseUser baseUser, List roles) {
        BaseUserBaseRole.removeAll(baseUser, true);
        //添加角色
        roles.each { roleId ->
            if (roleId) {
                BaseUserBaseRole.create(baseUser, BaseRole.read(roleId), true);
            }
        }
    }
}
