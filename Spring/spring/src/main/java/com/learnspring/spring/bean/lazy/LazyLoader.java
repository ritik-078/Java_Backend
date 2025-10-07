package com.learnspring.spring.bean.lazy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
// @Lazy annotation means beans are created on demand and not sutomatically
public class LazyLoader {
    public LazyLoader() {
        System.out.println("LazyLoader");
    }
}
