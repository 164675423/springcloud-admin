package com.zh.gateway.authentication.auth;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 身份验证及权限认证
 * TODO 生成一个key，传递给下层服务，在下层服务中进行验证，有效避免跳过网关直接访问下层服务的情况
 *
 * @author zh
 * @date 2020/2/18
 */
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {
  public final DefaultTokenService tokenService;
  private final String authCookie = "token";
  private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

  @Value("${security.oauth2.resource.jwt.expire-minutes}")
  private Integer expireMinutes;

  public AuthenticationFilter(DefaultTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    logger.info("{} {}", exchange.getRequest().getMethod(), exchange.getRequest().getPath());
    HttpCookie cookie = exchange.getRequest().getCookies().getFirst("SpringToken");
    if (cookie != null) {
      Claims claims = tokenService.parseJwt(cookie.getValue());
      if (claims != null) {
        //如果符合刷新策略,重新生成jwt
        Boolean shouldRefresh = tokenService.shouldRefresh(claims, expireMinutes);
        if (shouldRefresh) {
          String jwt = tokenService.createJwt(claims.getSubject(), expireMinutes);
          exchange.getResponse().addCookie(tokenService.getAuthCookie(jwt, expireMinutes));
        }
        //验证通过添加自定义header,向下层服务传递
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.add("X-USER-ID", claims.getSubject());
        ServerHttpRequest newRequest = new ServerHttpRequestDecorator(exchange.getRequest().mutate().build()) {
          @Override
          public HttpHeaders getHeaders() {
            return headers;
          }
        };
        return chain.filter(exchange.mutate().request(newRequest).build());
      }
    }
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    DataBuffer buffer = exchange
        .getResponse()
        .bufferFactory()
        .wrap(HttpStatus.UNAUTHORIZED.toString().getBytes(StandardCharsets.UTF_8));
    return exchange.getResponse().writeWith(Flux.just(buffer));
  }


  @Override
  public int getOrder() {
    return -100;
  }
}
