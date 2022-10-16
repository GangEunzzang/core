package com.hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ScopeMetadata;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void 싱글톤_오류_테스트() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThreadA: 사용자A 만원 주문
        statefulService1.order("userA", 10000);
        //ThreadB: 사용자B 만원 주문
        statefulService2.order("userB", 20000);

        //ThreadA: 사용자 주문 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);
        ScopeMetadata.class;
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }


    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }


}