package com.leduyminh.userservice.constant;

public class UriMatchersConstant {
    public static final String[] PUBLIC_URI_GET = new String[]{};

    public static final String[] PUBLIC_URI_POST = new String[]{
            "/accounts/authenticate"
    };

    public static final String[] PUBLIC_URI_PUT = new String[]{};

    public static final String[] PUBLIC_URI_PATCH = new String[]{};

    public static final String[] PUBLIC_URI_DELETE = new String[]{};

    public static final String[] AUTHORITY_URI = new String[]{
            "/admin/**",
    };
}
