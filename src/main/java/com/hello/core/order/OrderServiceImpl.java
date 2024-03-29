package com.hello.core.order;

import com.hello.core.annotation.MainDiscountPolicy;
import com.hello.core.discount.DisCountPolicy;
import com.hello.core.member.Member;
import com.hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;

    private final DisCountPolicy disCountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DisCountPolicy disCountPolicy) {
        this.memberRepository = memberRepository;
        this.disCountPolicy = disCountPolicy;
    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = disCountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
