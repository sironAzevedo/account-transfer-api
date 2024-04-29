package br.com.service.accountTransfer.infra.cache;

import br.com.service.accountTransfer.infra.cache.redis.CustomCacheErrorHandler;
import br.com.service.accountTransfer.infra.cache.redis.RedisConfigurationProperties;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.Delay;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.cache.interceptor.CacheErrorHandler;

import java.time.Duration;
import java.util.*;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@EnableCaching
@Configuration
@Profile("!test")
public class RedisCacheFactory implements CachingConfigurer {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(
            @Value("${cache.redis.config.host}") String host,
            @Value("${cache.redis.config.port}") Integer port,
            @Value("${cache.redis.config.database}") Integer database,
            @Value("${cache.redis.config.password}") String password
    ) {

        var clientResources = ClientResources.builder()
                .reconnectDelay(Delay.constant(Duration.ofSeconds(10)))
                .build();

        // Set custom client options
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(ClientOptions.builder()
                        .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                        .autoReconnect(true)
                        .build())
                .clientResources(clientResources)
                .build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port), clientConfig);
        factory.setShareNativeConnection(false);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public CacheManager cacheManagerRedis(LettuceConnectionFactory connectionFactory, CacheProperties cacheProperties) {
        log.info("INICIO - Configuracao do cache distribuido");

        var cacheConfigurations = new HashMap<String, RedisCacheConfiguration>();
        addCacheDefault(cacheConfigurations, getCacheNames(cacheProperties.getRedis().getCaches()));
        addNewCaches(cacheProperties, cacheConfigurations);

        var manager = RedisCacheManager.builder(connectionFactory)
                .disableCreateOnMissingCache()
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();

        log.info("FIM - Configuracao do cache distribuido realizado com sucesso");
        return manager;
    }

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        log.info("Configurando cacheErrorHandler Personalizado");
        return new CustomCacheErrorHandler();
    }

    private void addNewCaches(CacheProperties cacheProperties, Map<String, RedisCacheConfiguration> cacheConfigurations) {
        if(Objects.nonNull(cacheProperties)) {
            Optional.ofNullable(cacheProperties.getRedis()).ifPresent(r -> r.getCaches()
                    .forEach((cache) -> cacheConfigurations.put(cache.getCacheName(), this.cacheConfiguration(cache))));
        }
    }

    private void addCacheDefault(Map<String, RedisCacheConfiguration> cacheConfigurations, Set<String> cacheNames) {
        cacheNames.forEach(initialCache -> {
            RedisConfigurationProperties cache = RedisConfigurationProperties
                    .builder()
                    .cacheName(initialCache)
                    .expiration(Duration.ZERO)
                    .build();

            cacheConfigurations.put(cache.getCacheName(), this.cacheConfiguration(cache));
        });
    }

    private RedisCacheConfiguration cacheConfiguration(RedisConfigurationProperties conf) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(ObjectUtils.defaultIfNull(conf.getExpiration(), Duration.ZERO))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
    }

    private Set<String> getCacheNames(List<RedisConfigurationProperties> cachesExistentes) {
        Set<String> methods = new Reflections(new ConfigurationBuilder()
                .forPackages("br.com.service.accountTransfer.service")
                .setScanners(new MethodAnnotationsScanner()))
                .getMethodsAnnotatedWith(Cacheable.class)
                .stream().map(method -> method.getAnnotation(Cacheable.class))
                .flatMap(cacheable -> Arrays.stream(cacheable.value()))
                .collect(Collectors.toSet());

        // Criar um HashSet diretamente para evitar duplicatas e otimizar o contains
        Set<String> existingCacheNames = cachesExistentes.stream()
                .map(RedisConfigurationProperties::getCacheName)
                .collect(Collectors.toSet());

        // Filtrar diretamente em um HashSet para remover duplicatas e não existentes
        return methods.stream()
                .filter(cacheName -> !existingCacheNames.contains(cacheName))
                .collect(Collectors.toSet());
    }
}


