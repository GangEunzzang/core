package com.hello.core.beanfind;

import com.hello.core.AppConfig;
import com.hello.core.member.MemberRepository;
import com.hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);


    @Test
    void 타입_조회시_같은_타입이_둘_이상이면_중복오류_발생() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
                () ->  ac.getBean(MemberRepository.class));
    }

    @Test
    void 타입_조회시_같은_타입이_둘_이상이면_빈이름을_지정한다() {
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    void 특정타입을_모두_조회하기() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        assertThat(beansOfType.size()).isEqualTo(2);
    }


    static class SameBeanConfig {

        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }
}
