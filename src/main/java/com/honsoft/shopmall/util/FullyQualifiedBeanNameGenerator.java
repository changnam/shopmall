package com.honsoft.shopmall.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class FullyQualifiedBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        // Use fully qualified class name as the default name
        return definition.getBeanClassName();
    }
}