package com.example.demo.Config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

/**
 * Created by jesse on 2017/2/16.
 */
@Configuration
@EnableCaching
public class RedisConfig  extends CachingConfigurerSupport {

    /***
     *  这个东西就是用来针对object查询生成缓存key的一个key生成器,更具key来判断是否命中
     *  这里生成的规则是类名加方法名加参数的字符串
     */

       @Bean
        public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... objects) {
                StringBuilder sb=new StringBuilder();
                sb.append(target.getClass().getName());//拿到类名
                sb.append(method.getName());//拿到方法名
                //添加参数名
                for(Object object:objects){
                    sb.append(object.toString());
                }

                return sb.toString();//类名方法名参数名连起来的字符串
            }
        };
    }

    /***
     * 缓存管理器，这里是包裹了redisTemplate的缓存管理器
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate){
        return new RedisCacheManager(redisTemplate);
        }

    /***
     * 配置redisTemplate用于查询redis
     * @param factory 用于连接redis
     * @return
     */
    @Bean
    public RedisTemplate<String,String> redisTemplate(
            RedisConnectionFactory factory){

        StringRedisTemplate template = new StringRedisTemplate(factory);
        //
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();

        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


}
