package ru.soft.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import ru.soft.web.utils.JsonUtil;

@Configuration
public class WebConfig {

    @Autowired
    ObjectMapper mapper;

    @PostConstruct
    void setObjectMapper() {
        Assert.notNull(this.mapper, "Нет в контексте");
        JsonUtil.setMapper(this.mapper);
    }
}
