package com.zh.gateway.authentication.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.ResponseCookie;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

public final class DefaultTokenService {
  private Key signingKey;

  public DefaultTokenService(Key key) {
    this.signingKey = key;
  }

  /**
   * 从Key中获取签名算法
   *
   * @param signingKey
   * @return
   * @throws SignatureException
   */
  private static SignatureAlgorithm getJcaName(Key signingKey) throws SignatureException {
    String jcaName = signingKey.getAlgorithm();
    if (jcaName == null) {
      return SignatureAlgorithm.NONE;
    }

    for (SignatureAlgorithm alg : SignatureAlgorithm.values()) {
      if (jcaName.equalsIgnoreCase(alg.getJcaName())) {
        return alg;
      }
    }

    throw new SignatureException("Unsupported signature '" + jcaName + "'");
  }


  /**
   * 创建JWT
   *
   * @param subject        认证主体
   * @param expiredMinutes 过期时间,以分钟为单位
   * @return JWT字符串
   */
  public String createJwt(String subject, long expiredMinutes) {
    long now = System.currentTimeMillis();
    SignatureAlgorithm signatureAlgorithm = null;
    try {
      signatureAlgorithm = getJcaName(signingKey);
    } catch (SignatureException e) {
      throw new IllegalStateException("Unsupported signature");
    }
    JwtBuilder jwtBuilder = Jwts.builder()
        .setIssuedAt(new Date(now))//签发时间
        .setSubject(subject)//认证主体
        .signWith(signatureAlgorithm, signingKey);
    if (expiredMinutes > 0) {
      jwtBuilder.setExpiration(new Date(now + expiredMinutes * 60 * 1000));
    }
    return jwtBuilder.compact();
  }

  /**
   * 解析JWT
   *
   * @param jwt
   * @return
   */
  public Claims parseJwt(String jwt) {
    try {
      Jws<Claims> jws = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwt);
      return jws.getBody();
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * 判断是否需要刷新JWT
   *
   * @param claims
   * @param expiredMinutes
   * @return
   */
  public Boolean shouldRefresh(Claims claims, long expiredMinutes) {
    long expireTime = claims.getExpiration().getTime();
    long now = new Date().getTime();
    return expireTime > now && (expireTime - now) / 1000 < ((expiredMinutes * 60) / 2);
  }

  /**
   * 生成cookie
   *
   * @param value
   * @param expiredMinutes
   * @return
   */
  public ResponseCookie getAuthCookie(String value, long expiredMinutes) {
    ResponseCookie cookie = ResponseCookie.from("SpringToken", value)
        .httpOnly(true)
        .maxAge(expiredMinutes * 60)
        .path("/")
        .build();
    return cookie;
  }
}
