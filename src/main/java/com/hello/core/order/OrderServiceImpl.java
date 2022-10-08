package com.hello.core.order;

import com.hello.core.discount.DisCountPolicy;
import com.hello.core.member.Member;
import com.hello.core.member.MemberRepository;
import com.hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private DisCountPolicy disCountPolicy ;


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = disCountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
