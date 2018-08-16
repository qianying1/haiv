package cn.qhb.haiv.model.access_control;

import org.apache.shiro.authc.AuthenticationToken;

public class ShiroToken implements AuthenticationToken {

    private String username;

    private String password;

    public ShiroToken(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
