package com.hanaro.wouldyouhana.forSignIn;

import org.springframework.transaction.annotation.Transactional;

public abstract class CustomerService2 {
    @Transactional
    public abstract JwtToken signIn(String email, String password);
}
