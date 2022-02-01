package com.ssafy.bjbj.api.booklog.repository;

import com.ssafy.bjbj.api.bookinfo.entity.BookInfo;
import com.ssafy.bjbj.api.bookinfo.repository.BookInfoRepository;
import com.ssafy.bjbj.api.booklog.dto.request.RequestBooklogDto;
import com.ssafy.bjbj.api.booklog.entity.Booklog;
import com.ssafy.bjbj.api.member.entity.Member;
import com.ssafy.bjbj.api.member.entity.Role;
import com.ssafy.bjbj.api.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class BooklogRepositoryTest {

    @Autowired
    private BooklogRepository booklogRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private EntityManager em;

    private Member setUpMember = null;

    private Booklog setUpBooklog = null;

    private BookInfo setUpBookInfo = null;

    @BeforeEach
    public void setUp() {
        String email = "setupEmail@bjbj.com";
        setUpMember = memberRepository.save(Member.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("setupNickname")
                .phoneNumber("010-1111-2222")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build());

        setUpBookInfo = bookInfoRepository.findBySeq(776L);

        // 북로그 작성
        setUpBooklog = booklogRepository.save(Booklog.builder()
                .title("북로그 제목")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(false)
                .views(0)
                .member(setUpMember)
                .bookInfo(setUpBookInfo)
                .build());
    }

    @DisplayName("북로그 엔티티 등록 테스트")
    @Test
    public void booklog_register_test() {
        // 회원가입
        String email = "email@bjbj.com";
        Member savedMember = memberRepository.save(Member.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("nickname")
                .phoneNumber("010-1234-5678")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build());

        // 책 정보 저장
        /*
        책 정보의 경우 현재 등록 메서드가 없으므로 이미 db에 저장된 775번 책 정보를 사용
         */
        BookInfo savedBookInfo = bookInfoRepository.findBySeq(775L);

        // 북로그 작성
        Booklog booklog = Booklog.builder()
                .title("북로그 제목")
                .content("북로그 내용")
                .summary("북로그 한줄평")
                .starRating(4)
                .readDate(LocalDateTime.parse("2021-12-21" + "T00:00:00"))
                .isOpen(true)
                .views(0)
                .member(savedMember)
                .bookInfo(savedBookInfo)
                .build();

        booklogRepository.save(booklog);
        em.flush();
        em.clear();

        // 작성된 북로그 검증
        Booklog savedBooklog = booklogRepository.findBySeq(booklog.getSeq());
        assertThat(booklog.getMember().getSeq()).isEqualTo(savedBooklog.getMember().getSeq());
        assertThat(booklog.getBookInfo().getSeq()).isEqualTo(savedBooklog.getBookInfo().getSeq());
        assertThat(booklog.getTitle()).isEqualTo(savedBooklog.getTitle());
        assertThat(booklog.getContent()).isEqualTo(savedBooklog.getContent());
        assertThat(booklog.getSummary()).isEqualTo(savedBooklog.getSummary());
        assertThat(booklog.getStarRating()).isEqualTo(savedBooklog.getStarRating());
        assertThat(booklog.getReadDate()).isEqualTo(savedBooklog.getReadDate());
        assertThat(booklog.isOpen()).isEqualTo(savedBooklog.isOpen());
        assertThat(booklog.getViews()).isEqualTo(savedBooklog.getViews());
    }

    @DisplayName("북로그 엔티티 수정 테스트")
    @Test
    public void booklog_update_test() {
        // 회원가입
        String email = "email@bjbj.com";
        Member savedMember = memberRepository.save(Member.builder()
                .email(email)
                .password("password")
                .name("name")
                .nickname("nickname")
                .phoneNumber("010-1234-5678")
                .exp(0)
                .point(100)
                .role(Role.MEMBER)
                .build());

        // 책 정보 저장
        /*
        책 정보의 경우 현재 등록 메서드가 없으므로 이미 db에 저장된 775번 책 정보를 사용
         */
        BookInfo savedBookInfo = bookInfoRepository.findBySeq(775L);

        // 북로그 작성
        Booklog booklog = Booklog.builder()
                .title("북로그 제목")
                .content(null)
                .summary(null)
                .starRating(null)
                .readDate(null)
                .isOpen(true)
                .views(0)
                .member(savedMember)
                .bookInfo(savedBookInfo)
                .build();

        booklogRepository.save(booklog);
        em.flush();
        em.clear();

        // 북로그 수정
        Booklog findBooklog = booklogRepository.findBySeq(booklog.getSeq());
        RequestBooklogDto reqBooklogDto = RequestBooklogDto.builder()
                .memberSeq(savedMember.getSeq())
                .bookInfoSeq(775L)
                .title("북로그 제목2")
                .content("북로그 내용2")
                .summary("북로그 한줄평2")
                .starRating(4)
                .readDate("2021-12-21")
                .isOpen(true)
                .build();
        findBooklog.changeBooklog(reqBooklogDto);
        em.flush();
        em.clear();

        // 수정된 북로그 검증
        Booklog savedBooklog = booklogRepository.findBySeq(findBooklog.getSeq());
        assertThat(findBooklog.getMember().getSeq()).isEqualTo(savedBooklog.getMember().getSeq());
        assertThat(findBooklog.getBookInfo().getSeq()).isEqualTo(savedBooklog.getBookInfo().getSeq());
        assertThat(findBooklog.getTitle()).isEqualTo(savedBooklog.getTitle());
        assertThat(findBooklog.getContent()).isEqualTo(savedBooklog.getContent());
        assertThat(findBooklog.getSummary()).isEqualTo(savedBooklog.getSummary());
        assertThat(findBooklog.getStarRating()).isEqualTo(savedBooklog.getStarRating());
        assertThat(findBooklog.getReadDate()).isEqualTo(savedBooklog.getReadDate());
        assertThat(findBooklog.isOpen()).isEqualTo(savedBooklog.isOpen());
        assertThat(findBooklog.getViews()).isEqualTo(savedBooklog.getViews());
    }

    @DisplayName("북로그 엔티티 공개여부 수정 테스트")
    @Test
    public void booklogEntityIsOpenChangeTest() {
        // 수정
        setUpBooklog.changeIsOpen(true);
        em.flush();
        em.clear();

        // 검증
        Booklog changedBooklog = booklogRepository.findBySeq(setUpBooklog.getSeq());
        assertThat(changedBooklog.isOpen()).isTrue();
    }

}