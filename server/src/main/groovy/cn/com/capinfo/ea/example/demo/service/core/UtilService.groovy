package cn.com.capinfo.ea.example.demo.service.core

import cn.com.capinfo.ea.core.CapinfoSpringUtils
import org.springframework.stereotype.Service
import org.springframework.validation.FieldError

@Service
class UtilService {
    public Map collectionErrorMap(def entityClass, def entityInstance) {
        Map errors = new HashMap();
        entityInstance.errors.allErrors.each { FieldError error ->
            String message = CapinfoSpringUtils.getI18nMessage(error.code, error.arguments.toList(), error.defaultMessage).toString();
            if (CapinfoSpringUtils.isDomain(entityClass.gormPersistentEntity.persistentProperties.find { it.name == error.field }.type)) {
                errors.put("${error.field}.id", message);
            } else {
                errors.put(error.field, message);
            }
        }
        return errors
    }
}
