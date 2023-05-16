package com.bptn.feedapp.provider.factory;


import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

// The purpose of this class is to read a YAML file and create a Properties object containing the properties defined in the config YAML file

public class YamlPropertySourceFactory implements PropertySourceFactory {

    // This method takes in the property source and an EncodedResource object representing the YAML file
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        Properties loadedProperties = this.loadYamlIntoProperties(resource.getResource()); // LoadYaml method converts YAML into Properties
        
        return new PropertiesPropertySource((StringUtils.hasLength(name)) ? name : resource.getResource().getFilename(), loadedProperties);
    }

    // This method uses YamlPropertiesFactoryBean to convert yaml file into properties
    private Properties loadYamlIntoProperties(Resource resource) {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource);
        factory.afterPropertiesSet();
        
        return factory.getObject();
    }
}