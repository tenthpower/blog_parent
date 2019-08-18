package com.blog.rest;

import com.blog.dto.user.SysAdminVo;
import com.blog.dto.user.SysRoleVo;
import com.blog.security.auth.token.extractor.TokenExtractor;
import com.blog.security.auth.token.verifier.TokenVerifier;
import com.blog.security.config.WebSecurityConfig;
import com.blog.security.exceptions.InvalidTokenException;
import com.blog.security.model.UserContext;
import com.blog.security.model.token.RawAccessToken;
import com.blog.security.model.token.RefreshToken;
import com.blog.security.model.token.Token;
import com.blog.security.model.token.TokenFactory;
import com.blog.service.SysAdminService;
import com.blog.service.SysRoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Api(value="demoController", tags = "DemoController")
public class DemoController {


    @Autowired
    private TokenVerifier tokenVerifier;
    @Autowired
    private TokenFactory tokenFactory;
    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private SysAdminService sysAdminService;

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    @GetMapping("/api/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping("/manage/test3")
    public String test3() {
        return "test3";
    }

    @GetMapping("/api/auth/refresh_token")
    public Token refreshToken(HttpServletRequest request) {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.TOKEN_HEADER_PARAM));
        RawAccessToken rawToken = new RawAccessToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, "battcn").orElseThrow(() -> new InvalidTokenException("Token验证失败"));

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidTokenException("Token验证失败");
        }

        String subjectect = refreshToken.getSubject();
        SysAdminVo user =
                Optional.ofNullable(sysAdminService.findByName(subjectect)).orElseThrow(() -> new UsernameNotFoundException("用户未找到: " + subjectect));
        List<SysRoleVo> roles =
                Optional.ofNullable(sysRoleService.findByAdminId(user.getId())).orElseThrow(() -> new InsufficientAuthenticationException(
                        "用户没有分配角色"));
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleName()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getLoginName(), authorities);
        return tokenFactory.createAccessToken(userContext);
    }

}
