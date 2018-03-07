package com.codesetters.ebook.web.rest;

import com.codesetters.ebook.AbstractCassandraTest;
import com.codesetters.ebook.EbookApp;

import com.codesetters.ebook.domain.Member;
import com.codesetters.ebook.repository.MemberRepository;
import com.codesetters.ebook.service.MemberService;
import com.codesetters.ebook.service.dto.MemberDTO;
import com.codesetters.ebook.service.mapper.MemberMapper;
import com.codesetters.ebook.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static com.codesetters.ebook.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MemberResource REST controller.
 *
 * @see MemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbookApp.class)
public class MemberResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_JOIN_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JOIN_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MEMEBER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEMEBER_TYPE = "BBBBBBBBBB";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMemberMockMvc;

    private Member member;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemberResource memberResource = new MemberResource(memberService);
        this.restMemberMockMvc = MockMvcBuilders.standaloneSetup(memberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity() {
        Member member = new Member()
            .name(DEFAULT_NAME)
            .member_login(DEFAULT_MEMBER_LOGIN)
            .member_email(DEFAULT_MEMBER_EMAIL)
            .join_date(DEFAULT_JOIN_DATE)
            .memeber_type(DEFAULT_MEMEBER_TYPE);
        return member;
    }

    @Before
    public void initTest() {
        memberRepository.deleteAll();
        member = createEntity();
    }

    @Test
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMember.getMember_login()).isEqualTo(DEFAULT_MEMBER_LOGIN);
        assertThat(testMember.getMember_email()).isEqualTo(DEFAULT_MEMBER_EMAIL);
        assertThat(testMember.getJoin_date()).isEqualTo(DEFAULT_JOIN_DATE);
        assertThat(testMember.getMemeber_type()).isEqualTo(DEFAULT_MEMEBER_TYPE);
    }

    @Test
    public void createMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member with an existing ID
        member.setId(UUID.randomUUID());
        MemberDTO memberDTO = memberMapper.toDto(member);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.save(member);

        // Get all the memberList
        restMemberMockMvc.perform(get("/api/members"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].member_login").value(hasItem(DEFAULT_MEMBER_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].member_email").value(hasItem(DEFAULT_MEMBER_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].join_date").value(hasItem(sameInstant(DEFAULT_JOIN_DATE))))
            .andExpect(jsonPath("$.[*].memeber_type").value(hasItem(DEFAULT_MEMEBER_TYPE.toString())));
    }

    @Test
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.save(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.member_login").value(DEFAULT_MEMBER_LOGIN.toString()))
            .andExpect(jsonPath("$.member_email").value(DEFAULT_MEMBER_EMAIL.toString()))
            .andExpect(jsonPath("$.join_date").value(sameInstant(DEFAULT_JOIN_DATE)))
            .andExpect(jsonPath("$.memeber_type").value(DEFAULT_MEMEBER_TYPE.toString()));
    }

    @Test
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMember() throws Exception {
        // Initialize the database
        memberRepository.save(member);
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findOne(member.getId());
        updatedMember
            .name(UPDATED_NAME)
            .member_login(UPDATED_MEMBER_LOGIN)
            .member_email(UPDATED_MEMBER_EMAIL)
            .join_date(UPDATED_JOIN_DATE)
            .memeber_type(UPDATED_MEMEBER_TYPE);
        MemberDTO memberDTO = memberMapper.toDto(updatedMember);

        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMember.getMember_login()).isEqualTo(UPDATED_MEMBER_LOGIN);
        assertThat(testMember.getMember_email()).isEqualTo(UPDATED_MEMBER_EMAIL);
        assertThat(testMember.getJoin_date()).isEqualTo(UPDATED_JOIN_DATE);
        assertThat(testMember.getMemeber_type()).isEqualTo(UPDATED_MEMEBER_TYPE);
    }

    @Test
    public void updateNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.save(member);
        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Get the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Member.class);
        Member member1 = new Member();
        member1.setId(UUID.randomUUID());
        Member member2 = new Member();
        member2.setId(member1.getId());
        assertThat(member1).isEqualTo(member2);
        member2.setId(UUID.randomUUID());
        assertThat(member1).isNotEqualTo(member2);
        member1.setId(null);
        assertThat(member1).isNotEqualTo(member2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberDTO.class);
        MemberDTO memberDTO1 = new MemberDTO();
        memberDTO1.setId(UUID.randomUUID());
        MemberDTO memberDTO2 = new MemberDTO();
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
        memberDTO2.setId(memberDTO1.getId());
        assertThat(memberDTO1).isEqualTo(memberDTO2);
        memberDTO2.setId(UUID.randomUUID());
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
        memberDTO1.setId(null);
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
    }
}
