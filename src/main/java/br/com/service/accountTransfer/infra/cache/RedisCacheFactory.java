package br.com.service.accountTransfer.infra.cache;

import br.com.service.accountTransfer.infra.cache.redis.RedisConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Configuration
//@ConditionalOnProperty(
//        value = {"cache.redis.enabled"},
//        havingValue = "true"
//)
public class RedisCacheFactory {

    @Bean
    public CacheManager cacheManagerRedis(RedisConnectionFactory redisConnectionFactory, CacheProperties cacheProperties) {
        log.info("INICIO - Configuracao do cache distribuido");
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        addNewCaches(cacheProperties, cacheConfigurations);

        RedisCacheManager manager = RedisCacheManager.builder(redisConnectionFactory)
                .disableCreateOnMissingCache()
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();

        log.info("FIM - Configuracao do cache distribuido realizado com sucesso");
        return manager;
    }

    private void addNewCaches(CacheProperties cacheProperties, Map<String, RedisCacheConfiguration> cacheConfigurations) {
        if(Objects.nonNull(cacheProperties)) {
            Optional.ofNullable(cacheProperties.getRedis()).ifPresent(r -> r.getCaches()
                    .forEach((cache) -> cacheConfigurations.put(cache.getCacheName(), this.cacheConfiguration(cache))));
        }
    }

    private RedisCacheConfiguration cacheConfiguration(RedisConfigurationProperties conf) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(ObjectUtils.defaultIfNull(conf.getExpiration(), Duration.ZERO))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
    }
}