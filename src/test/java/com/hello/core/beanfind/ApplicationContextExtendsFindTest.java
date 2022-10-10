package com.hello.core.beanfind;

import com.hello.core.discount.DisCountPolicy;
import com.hello.core.discount.FixDiscountPolicy;
import com.hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);


    @Test
    void 부모타입으로_조회시_자식이둘이상이면_중복오류가발생() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DisCountPolicy.class));
    }

    @Test
    void 부모타입으로조회시_자식이둘이상이면_빈이름을지정하면된다() {
        DisCountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DisCountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    void 특정하위타입으로_조회() {
        RateDiscountPolicy rateDiscountPolicy = ac.getBean(RateDiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    void 부모타입으로_모두조회하기() {
        Map<String, DisCountPolicy> beansOfType = ac.getBeansOfType(DisCountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
    }

    @Test
    void 부모타입으로_모두조회하기_Object() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "value = " + beansOfType.get(key));
        }
    }


    @Configuration
    static class TestConfig {

        @Bean
        public DisCountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DisCountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
