package com.sparta.springlv2project.entity;

public enum UserRoleEnum {
    USER(Authority.USER),
    ADMIN(Authority.ADMIN);

    private final String authority;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

    public UserRoleEnum matchAdminToken(String token){
        if (!ADMIN_TOKEN.equals(token)) {
            throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
        }
        return UserRoleEnum.ADMIN;
    }
}
