package cn.com.capinfo.ea.example.demo.controller.core

import cn.com.capinfo.ea.example.demo.domain.core.BaseRole;
import cn.com.capinfo.ea.example.demo.domain.core.BaseUser;
import cn.com.capinfo.ea.example.demo.domain.core.BaseUserBaseRole;
import cn.com.capinfo.ea.example.demo.domain.core.SystemLoginRecord;
import cn.com.capinfo.ea.example.demo.service.core.UtilService
import cn.com.capinfo.ea.example.demo.service.core.BaseUserBaseRoleService
import cn.com.capinfo.ea.example.demo.service.core.BaseUserService
import cn.com.capinfo.ea.core.CapinfoSpringUtils
import cn.com.capinfo.ea.core.PageParams
import cn.com.capinfo.ea.plugin.poi.ExcelWriteBuilder
import cn.com.capinfo.ea.core.annotation.CapinfoController
import org.springframework.ui.Model;
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import groovy.util.logging.Slf4j
import org.springframework.transaction.annotation.Transactional
import org.springframework.beans.factory.annotation.Autowired

/**
 * 用户管理控制器
 */
@Slf4j
@CapinfoController
class BaseUserController {
    @Autowired
    private BaseUserService baseUserService
    @Autowired
    private BaseUserBaseRoleService baseUserBaseRoleService
    @Autowired
    private UtilService utilService

    public void index(Model model) {
        model.addAttribute("roles", BaseRole.list(['sort': 'id', 'order': 'asc']));
    }

    @ResponseBody
    public Map json(PageParams pageParams, String search, String role) {
        Map map = new HashMap();
        if (!role) {
            Closure searchLogic = {
                if (search) {
                    or {
                        like('username', "%${search}%")
                        like('realname', "%${search}%")
                    }
                }
            }
            long count = baseUserService.count(searchLogic)
            List list = baseUserService.list(pageParams, searchLogic)
            map.total = count;
            map.rows = list;
        } else {
            Closure searchLogic = {
                baseRole {
                    eq("authority", role);
                }
                if (search) {
                    baseUser {
                        or {
                            like('username', "%${search}%")
                            like('realname', "%${search}%")
                        }
                    }
                }
            }
            long count = baseUserBaseRoleService.count(searchLogic)
            List list = baseUserBaseRoleService.list(pageParams, searchLogic)
            map.total = count;
            map.rows = list.baseUser;
        }

        return map;
    }
    //@CacheEvict(value = 'baseUserJson', allEntries=true)
    public void show(long id, Model model) {
        BaseUser baseUserInstance = baseUserService.read(id)
        model.addAttribute("baseUserInstance", baseUserInstance);
    }

    public void create(Model model) {
        BaseUser baseUserInstance = baseUserService.create()
        model.addAttribute("baseUserInstance", baseUserInstance);
        model.addAttribute("baseUserRoles", []);
        model.addAttribute("booleanVals", [true, false]);
        model.addAttribute("roles", BaseRole.list(['sort': 'id', 'order': 'asc']));
    }

    @ResponseBody
    public Map save(HttpServletRequest request, BaseUser baseUserInstance) {
        Map map = new HashMap();
        if (baseUserInstance == null) {
            notFound(map, "");
            return map;
        }

        if (baseUserInstance.hasErrors()) {
            foundErrors(baseUserInstance, map);
            return map;
        }

        if (!baseUserService.save(baseUserInstance)) {
            foundErrors(baseUserInstance, map);
        } else {
            if (request.getParameterValues("authorities") && request.getParameterValues("authorities").size() > 0) {
                baseUserBaseRoleService.authRoleToUser(baseUserInstance, request.getParameterValues("authorities").toList())
            }
            map.result = true;
            map.id = baseUserInstance.id;
            map.password = baseUserInstance.password;
            String domainName = CapinfoSpringUtils.getI18nMessage("baseUser.label");
            map.message = CapinfoSpringUtils.getI18nMessage("default.created.message", [domainName, baseUserInstance.id]);
        }
        return map;
    }

    public void edit(long id, Model model) {
        BaseUser baseUserInstance = baseUserService.read(id)
        model.addAttribute("baseUserInstance", baseUserInstance);
        model.addAttribute("baseUserRoles", baseUserInstance.authorities*.id);
        model.addAttribute("booleanVals", [true, false]);
        model.addAttribute("roles", BaseRole.list(['sort': 'id', 'order': 'asc']));
    }

    @ResponseBody
    public Map update(HttpServletRequest request, BaseUser baseUserInstance, Long version) {
        Map map = new HashMap();
        if (baseUserInstance == null) {
            notFound(map, "${baseUserInstance.id}")
            return map
        }
        if (version != null) {
            if (baseUserInstance.version > version) {
                map.result = false
                map.message = CapinfoSpringUtils.getI18nMessage("default.optimistic.locking.failure", [baseUserInstance.id]);
                return map;
            }
        }
        if (baseUserInstance.hasErrors()) {
            foundErrors(baseUserInstance, map);
            return map
        }
        boolean result = baseUserService.save(baseUserInstance)
        if (!result) {
            foundErrors(baseUserInstance, map);
        } else {
            if (request.getParameterValues("authorities") && request.getParameterValues("authorities").size() > 0) {
                baseUserBaseRoleService.authRoleToUser(baseUserInstance, request.getParameterValues("authorities").toList())
            }
            map.result = true;
            map.password = baseUserInstance.password;
            String domainName = CapinfoSpringUtils.getI18nMessage("baseUser.label");
            map.message = CapinfoSpringUtils.getI18nMessage("default.updated.message", [domainName, baseUserInstance.id]);
        }
        return map;
    }

    @ResponseBody
    public Map delete(long id) {
        Map map = new HashMap();
        BaseUser baseUserInstance = BaseUser.get(id);
        if (baseUserInstance == null) {
            notFound(map, "${id}");
            return map;
        }
        String domainId = baseUserInstance.id;
        boolean result = baseUserService.delete(baseUserInstance)
        map.result = result;
        String domainName = CapinfoSpringUtils.getI18nMessage("baseRole.label");
        if (result) {
            map.message = CapinfoSpringUtils.getI18nMessage("default.deleted.message", [domainName, domainId]);
        } else {
            map.message = CapinfoSpringUtils.getI18nMessage("default.not.deleted.message", [domainName, domainId]);
        }
        return map;
    }

    @ResponseBody
    public Map deletes(String ids, Model model) {
        Map map = new HashMap();
        String domainName = CapinfoSpringUtils.getI18nMessage("baseUser.label");
        boolean result = baseUserService.deletes(ids)
        map.result = result;
        if (result) {
            map.message = CapinfoSpringUtils.getI18nMessage("default.deleted.message", [domainName, ids]);
        } else {
            map.message = CapinfoSpringUtils.getI18nMessage("default.not.deleted.message", [domainName, ids]);
        }
        return map;
    }

    public void download(javax.servlet.http.HttpServletResponse response) {
        File tempFile = File.createTempFile("_tmp", ".xls")
        //开发模式获取模板文件
        URL templateFileResource = ClassLoader.getResource("/templates/tools/empty.xls")
        if (templateFileResource) {
            tempFile.bytes = new File(templateFileResource.toURI()).bytes
        } else {
            //jar包模式下获取模板文件
            tempFile.bytes = this.class.classLoader.getResourceAsStream("/templates/tools/empty.xls")?.bytes
        }
        ExcelWriteBuilder excelWriteBuilder = new ExcelWriteBuilder(tempFile);
        excelWriteBuilder.workbook {
            sheet("sheet1") {
                row(["id", "用户名", "真实姓名", "是否启用"]);
                baseUserService.list(new PageParams(max: 100), {}).each { BaseUser baseUserInstance ->
                    row([baseUserInstance.id, baseUserInstance.username, baseUserInstance.realname, baseUserInstance.enabled])
                }
            }
        }
        byte[] excelBytes = excelWriteBuilder.download();
        try {
            response.setContentType("application/octet-stream;charset=GBK")
            response.addHeader("Content-Disposition", 'attachment; filename="' + new String("文件导出Excel.xls".getBytes("GBK"), "ISO8859-1") + '"');
            OutputStream out = response.outputStream;
            out.write(excelBytes);
            out.close();
        } catch (e) {
            log.error(e.message);
            response.writer.print(e.message);
        } finally {
            tempFile.delete()
        }
    }

    protected Map notFound(Map map, String id) {
        String domainName = CapinfoSpringUtils.getI18nMessage("baseUser.label");
        map.result = false;
        map.message = CapinfoSpringUtils.getI18nMessage("default.not.found.message", [domainName, id]);
        return map;
    }

    protected Map foundErrors(BaseUser baseUserInstance, Map map) {
        map.result = false;
        map.errors = utilService.collectionErrorMap(BaseUser, baseUserInstance);
        return map;
    }
}

