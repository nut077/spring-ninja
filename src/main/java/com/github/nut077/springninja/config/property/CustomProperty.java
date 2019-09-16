package com.github.nut077.springninja.config.property;

import com.github.nut077.springninja.config.YmlSourceConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@PropertySource(value = "classpath:custom.yml", factory = YmlSourceConfig.class)
@ConfigurationProperties("custom")
@Component
public class CustomProperty {

    private String name;
    private String email;
    private String tel;
}
