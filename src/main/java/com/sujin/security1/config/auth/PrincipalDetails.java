package com.sujin.security1.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어줍니다 (Security ContextHolder)
// 오브젝트 타입 -> Authentication 타입 객체
// Authentication 안에 user정보가 있어야됨
// User오브젝트 타입 -> UserDetails 타입 객체

// Security Session => Authentication -> UserDetails 타입

import com.sujin.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 user의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }


    //계정만료?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
//계정 잠김?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 1년 지남?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정활성화?
    @Override
    public boolean isEnabled() {
        // 회원이 로그인한지 1년지나 휴면계정으로 하기로 함
        // 현재시간 - 로그인 시간 => 1년 초과 하면 return false
        return true;
    }
}
