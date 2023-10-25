package com.leduyminh.filemgtservice.configs;

import com.leduyminh.authlib.util.AuthUtil;
import com.leduyminh.gridfs.utils.GridFSUtils;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Locale;

@Configuration
public class BeanConfig {
    @Autowired
    protected Environment evn;

    @Autowired
    protected RedisTemplate redisTemplate;

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        return mapper;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages");
        source.setDefaultEncoding("UTF-8");
        source.setDefaultLocale(new Locale("vi"));
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasenames("i18n/messages");
        source.setDefaultEncoding("UTF-8");
        source.setDefaultLocale(new Locale("vi"));
        source.setUseCodeAsDefaultMessage(true);
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(source);
        return bean;
    }


    @Bean
    public AuthUtil authUtil() {
        return AuthUtil.build()
                .withRedisTemplate(redisTemplate)
                .withSecretKey(evn.getProperty("config.user.token.sercetkey"))
                .withKey(evn.getProperty("config.user.token.key"))
                .withTimeoutMillis(Long.valueOf(evn.getProperty("config.user.token.time")));
    }

    @Bean
    public GridFSUtils gridFSUtils() {
        HashMap<String, MongoDatabase> mongoInstanceNodes = new HashMap<>();
        String[] keys = evn.getProperty("mongo.file.keys").split(",");
        String[] urls = evn.getProperty("mongo.file.urls").split(",");
        String[] databases = evn.getProperty("mongo.file.databases").split(",");
        for(int i=0 ;i < keys.length; i++){
            mongoInstanceNodes.put(keys[i], MongoClients.create(urls[i]).getDatabase(databases[i]));
        }
        return GridFSUtils.builder().mongoInstanceCurrentKey(keys[0]).mongoInstanceNodes(mongoInstanceNodes).build();
    }
}
