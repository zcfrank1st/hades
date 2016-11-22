package com.chaos.hades.client.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
public class HadesNameSpaceHandlerSupport extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("client", new HadesParser());
    }
}
