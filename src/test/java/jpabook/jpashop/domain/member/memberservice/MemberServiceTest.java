package jpabook.jpashop.domain.member.memberservice;

import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.member.memberrepository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("kim");
        //when
        Long savedId = memberService.join(member);
        //then
        assertThat(member).isEqualTo(memberService.findOne(savedId));
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        //when
        memberService.join(member1);

        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
}