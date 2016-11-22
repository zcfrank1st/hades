package com.chaos.hades.client.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * Created by zcfrank1st on 22/11/2016.
 */
public class HadesParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return SpringBasedHadesClient.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String app = element.getAttribute("app");
        String connections = element.getAttribute("connections");

        builder.addConstructorArgValue(app);
        builder.addConstructorArgValue(connections);
    }
}
