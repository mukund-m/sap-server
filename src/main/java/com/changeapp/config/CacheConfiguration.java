package com.changeapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.changeapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.changeapp.domain.Request.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.Request.class.getName() + ".attachments", jcacheConfiguration);
            cm.createCache(com.changeapp.domain.Request.class.getName() + ".definitions", jcacheConfiguration);
            cm.createCache(com.changeapp.domain.RequestAttachment.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.DefinitionConfig.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.DefinitionConfig.class.getName() + ".fieldConfigs", jcacheConfiguration);
            cm.createCache(com.changeapp.domain.RequestTypeDefConfig.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.FieldDefinition.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.FieldDefinition.class.getName() + ".definitions", jcacheConfiguration);
            cm.createCache(com.changeapp.domain.ReuestDefinition.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.FieldOptionDefinition.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.FieldChoiceDefinition.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.FieldDefinition.class.getName() + ".choices", jcacheConfiguration);
            cm.createCache(com.changeapp.domain.RefCodeDetails.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.AttachmentType.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.TaskStructureConfig.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.PeopleRole.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.PeopleRoleUserMapping.class.getName(), jcacheConfiguration);
            cm.createCache(com.changeapp.domain.TaskQuestionInstance.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
