package br.com.service.accountTransfer.infra.cache;

import br.com.service.accountTransfer.infra.cache.redis.RedisProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties("cache")
public class CacheProperties {
    private RedisProperties redis;
}
