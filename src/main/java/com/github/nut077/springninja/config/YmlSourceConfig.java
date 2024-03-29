package com.github.nut077.springninja.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class YmlSourceConfig implements PropertySourceFactory {

    @SneakyThrows
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(resource.getResource());
        factoryBean.afterPropertiesSet();
        return new PropertiesPropertySource((name != null) ? name : resource.getResource().getFilename(), Objects.requireNonNull(factoryBean.getObject()));
    }
}
