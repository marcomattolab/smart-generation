package ${packageClass}.${srcConfigFolder};

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class ${className} {
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public ${className}(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(${packageClass}.${srcRepositoryFolder}.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(${packageClass}.${srcRepositoryFolder}.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(${packageClass}.${srcDomainFolder}.User.class.getName(), jcacheConfiguration);
            cm.createCache(${packageClass}.${srcDomainFolder}.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(${packageClass}.${srcDomainFolder}.User.class.getName() + ".authorities", jcacheConfiguration);
<#list tables as table>
            cm.createCache(${packageClass}.${srcDomainFolder}.${table.name}.class.getName(), jcacheConfiguration);
</#list>
        };
    }
}
