package com.github.nut077.springninja.config.property;

import com.github.nut077.springninja.config.YmlSourceConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
@Validated
@PropertySource(value = "classpath:async.yml", factory = YmlSourceConfig.class)
@ConfigurationProperties("custom.async")
@Component
public class AsyncProperty {

    // จะใช้การ validate ได้ต้องใส annotation @Validated ที่ข้างบน class
    @NotNull
    @Min(1)
    @Max(16)
    private int corePoolSize;
    private int queueCapacity;
    private int maxPoolSize;
    private Duration keepAlive;
    private String threadNamePrefix;
}
