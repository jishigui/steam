package com.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * Jwt加密和解密
 */
public class JwtToken {

        private String secret="JO6HN3NGIU25G2FIG8V7VD6CK9B6T2Z5";//密钥

        private long expire=120*60*4L* 1000; //8小时 //1200秒=20分钟  120*60

        /**
         * 生成jwt token  传入字符串，使用JWT对该字符串做加密操作
         */
        public String generateToken(String json) {
            Date nowDate = new Date();
            Date expireDate = new Date(nowDate.getTime() + expire );

            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;    //把一段文件信息进行加密，产生加密字符串，并且设置过期时间

            return Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setSubject(json + "")//被加密的字符串 （登录用户名称）
                    .setIssuedAt(nowDate)//当前系统 时间
                    .setExpiration(expireDate)   //过期时间
                    .signWith(signatureAlgorithm, secret.getBytes())  // new String(org.apache.commons.codec.binary.Base64.encodeBase64(secret.getBytes()) 把密钥用Base64加密
                    .compact();
            //.signWith(SignatureAlgorithm.HS512, secret)
            //.compact();
        }


        public Claims getClaimByToken(String token) { //把generateToken产生的字符串进行解密,得到Claims对象

            try {
                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
                return Jwts.parser()
                        .setSigningKey(secret.getBytes())
                        .parseClaimsJws(token)
                        .getBody();
            } catch (Exception e) {
                System.out.println("jwt 解密失败");
                e.printStackTrace();;
                return null;
            }
        }

    /**
     *传入Reqeust对象，获得解密后的字符串
     * @param
     * @return
     * @throws AuthenticationException
     */
    public  String getTokenData(HttpServletRequest request) throws AuthenticationException {
            //获得名为access_token的请求参数值
            String access_token =  request.getParameter("access_token");



            if (access_token==null || access_token.equals("")){
                access_token =   request.getHeader("access_token");
            }

            if (access_token==null || access_token.equals("")){
                return null;//表示请求参数中未传递中access_token
            }


            Claims claims = getClaimByToken(access_token);//解密

            if (claims == null || JwtToken.isTokenExpired(claims.getExpiration()) ) {
                throw new AuthenticationException("access_token 过期");
            }


            String tokenVal  = claims.getSubject();//得到加密时的内容，即 .setSubject(json + "")所设置的内容

            return tokenVal;
        }
    /*public Claims getClaimByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String[] header = token.split("Bearer");
        token = header[1];
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            logger.debug("validate is token error ", e);
            return null;
        }
    }*/

        /**
         * token是否过期
         * @return  true：过期
         */
        public static boolean isTokenExpired(Date expiration) {
            return expiration.before(new Date());
        }

        // Getter && Setter
    }