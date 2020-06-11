package cn.com.capinfo.ea.example.demo.controller.rest;

import cn.com.capinfo.ea.core.CapinfoSpringUtils
import cn.com.capinfo.ea.core.annotation.CapinfoRestController
import org.springframework.transaction.annotation.Transactional

@Transactional
@CapinfoRestController
class ApplicationController {
    public Map index() {
        Map map = System.properties;
        Map artefacts = [:]
        artefacts.controllers = CapinfoSpringUtils.controllerNames.collect { it.toString().tokenize(".")[-1].uncapitalize() };
        artefacts.services = CapinfoSpringUtils.serviceNames.collect { it.toString().tokenize(".")[-1].uncapitalize() };
        artefacts.domains = CapinfoSpringUtils.domains.collect { it.simpleName.uncapitalize() };
        map.artefacts = artefacts;
        return map;
    }
}
