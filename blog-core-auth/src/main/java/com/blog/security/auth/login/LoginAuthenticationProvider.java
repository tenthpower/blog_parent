package com.blog.security.auth.login;

import com.blog.dto.user.SysAdminVo;
import com.blog.dto.user.SysRoleVo;
import com.blog.security.model.UserContext;
import com.blog.service.SysAdminService;
import com.blog.service.SysRoleService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 
 * @author Levin
 *
 * @since 2017-05-25
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {
	
	private Logger logger = LoggerFactory.getLogger(LoginAuthenticationProvider.class);

	@Autowired
    private BCryptPasswordEncoder encoder;

	@Autowired
    private SysAdminService sysAdminService;

	@Autowired
    private SysRoleService sysRoleService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        SysAdminVo user = sysAdminService.findByName(username);
        if(user == null) throw new UsernameNotFoundException("User not found: " + username);
        if (!StringUtils.equals(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }
        List<SysRoleVo> roles = sysRoleService.findByAdminId(user.getId());
        if (roles == null || roles.size() <= 0) throw new InsufficientAuthenticationException("User has no roles assigned");
        
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRoleName()))
                .collect(Collectors.toList());
        
        UserContext userContext = UserContext.create(user.getLoginName(), authorities);
        
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
