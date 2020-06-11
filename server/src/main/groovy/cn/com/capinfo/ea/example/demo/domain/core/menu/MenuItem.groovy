package cn.com.capinfo.ea.example.demo.domain.core.menu

import cn.com.capinfo.ea.example.demo.domain.core.*
import cn.com.capinfo.ea.core.annotation.Title
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import grails.gorm.annotation.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * 系统菜单项类
 */
@EqualsAndHashCode(includes = 'name')
@ToString(includes = 'name', includeNames = true, includePackage = false)
@Entity
@Title(zh_CN = "菜单项")
@JsonIgnoreProperties(["errors", "metaClass", "dirty", "attached", "dirtyPropertyNames", "handler", "target", "session", "entityPersisters", "hibernateLazyInitializer", "initialized", "proxyKey", "children"])
class MenuItem implements Serializable {
    @Title(zh_CN = "名称")
    String name
    @Title(zh_CN = "地址")
    String url
    @Title(zh_CN = "顺序")
    long sequenceNum = 0
    @Title(zh_CN = "关联requestmap")
    Requestmap requestmap
    @Title(zh_CN = "父节点")
    MenuItem parent
    static hasMany = [children: MenuItem]
    static constraints = {
        name(blank: false, unique: true, maxSize: 200);
        url(nullable: true, maxSize: 250);
        sequenceNum();
        requestmap(nullable: true, unique: true);
        parent(nullable: true);
    }
    static mapping = {
        //id generator:'native', params:[sequence:'menuitem_id_seq']
        comment "菜单项"
    }

    String toString() {
        return name;
    }
}
