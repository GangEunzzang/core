package com.hello.core.autowired;

import static org.assertj.core.api.Assertions.*;

import com.hello.core.AutoAppConfig;
import com.hello.core.discount.DisCountPolicy;
import com.hello.core.member.Grade;
import com.hello.core.member.Member;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPolicy = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPolicy).isEqualTo(2000);
    }

    static class DiscountService {

        private final Map<String, DisCountPolicy> policyMap;
        private final List<DisCountPolicy> policies;
        public DiscountService(Map<String, DisCountPolicy> policyMap, List<DisCountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DisCountPolicy disCountPolicy = policyMap.get(discountCode);
            return disCountPolicy.discount(member, price);
        }
    }
}