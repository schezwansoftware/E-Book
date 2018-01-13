package com.codesetters.ebook.web.rest;

import com.codesetters.ebook.AbstractCassandraTest;
import com.codesetters.ebook.EbookApp;

import com.codesetters.ebook.domain.UserCertificates;
import com.codesetters.ebook.repository.UserCertificatesRepository;
import com.codesetters.ebook.service.UserCertificatesService;
import com.codesetters.ebook.service.dto.UserCertificatesDTO;
import com.codesetters.ebook.service.mapper.UserCertificatesMapper;
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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserCertificatesResource REST controller.
 *
 * @see UserCertificatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbookApp.class)
public class UserCertificatesResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_USERLOGIN = "AAAAAAAAAA";
    private static final String UPDATED_USERLOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATEURL = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATEURL = "BBBBBBBBBB";

    @Autowired
    private UserCertificatesRepository userCertificatesRepository;

    @Autowired
    private UserCertificatesMapper userCertificatesMapper;

    @Autowired
    private UserCertificatesService userCertificatesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restUserCertificatesMockMvc;

    private UserCertificates userCertificates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserCertificatesResource userCertificatesResource = new UserCertificatesResource(userCertificatesService);
        this.restUserCertificatesMockMvc = MockMvcBuilders.standaloneSetup(userCertificatesResource)
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
    public static UserCertificates createEntity() {
        UserCertificates userCertificates = new UserCertificates()
                .userlogin(DEFAULT_USERLOGIN)
                .certificateurl(DEFAULT_CERTIFICATEURL);
        return userCertificates;
    }

    @Before
    public void initTest() {
        userCertificatesRepository.deleteAll();
        userCertificates = createEntity();
    }

    @Test
    public void createUserCertificates() throws Exception {
        int databaseSizeBeforeCreate = userCertificatesRepository.findAll().size();

        // Create the UserCertificates
        UserCertificatesDTO userCertificatesDTO = userCertificatesMapper.userCertificatesToUserCertificatesDTO(userCertificates);

        restUserCertificatesMockMvc.perform(post("/api/user-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCertificatesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserCertificates in the database
        List<UserCertificates> userCertificatesList = userCertificatesRepository.findAll();
        assertThat(userCertificatesList).hasSize(databaseSizeBeforeCreate + 1);
        UserCertificates testUserCertificates = userCertificatesList.get(userCertificatesList.size() - 1);
        assertThat(testUserCertificates.getUserlogin()).isEqualTo(DEFAULT_USERLOGIN);
        assertThat(testUserCertificates.getCertificateurl()).isEqualTo(DEFAULT_CERTIFICATEURL);
    }

    @Test
    public void createUserCertificatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userCertificatesRepository.findAll().size();

        // Create the UserCertificates with an existing ID
        UserCertificates existingUserCertificates = new UserCertificates();
        existingUserCertificates.setId(UUID.randomUUID());
        UserCertificatesDTO existingUserCertificatesDTO = userCertificatesMapper.userCertificatesToUserCertificatesDTO(existingUserCertificates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCertificatesMockMvc.perform(post("/api/user-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserCertificatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserCertificates> userCertificatesList = userCertificatesRepository.findAll();
        assertThat(userCertificatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllUserCertificates() throws Exception {
        // Initialize the database
        userCertificatesRepository.save(userCertificates);

        // Get all the userCertificatesList
        restUserCertificatesMockMvc.perform(get("/api/user-certificates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userCertificates.getId().toString())))
            .andExpect(jsonPath("$.[*].userlogin").value(hasItem(DEFAULT_USERLOGIN.toString())))
            .andExpect(jsonPath("$.[*].certificateurl").value(hasItem(DEFAULT_CERTIFICATEURL.toString())));
    }

    @Test
    public void getUserCertificates() throws Exception {
        // Initialize the database
        userCertificatesRepository.save(userCertificates);

        // Get the userCertificates
        restUserCertificatesMockMvc.perform(get("/api/user-certificates/{id}", userCertificates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userCertificates.getId().toString()))
            .andExpect(jsonPath("$.userlogin").value(DEFAULT_USERLOGIN.toString()))
            .andExpect(jsonPath("$.certificateurl").value(DEFAULT_CERTIFICATEURL.toString()));
    }

    @Test
    public void getNonExistingUserCertificates() throws Exception {
        // Get the userCertificates
        restUserCertificatesMockMvc.perform(get("/api/user-certificates/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUserCertificates() throws Exception {
        // Initialize the database
        userCertificatesRepository.save(userCertificates);
        int databaseSizeBeforeUpdate = userCertificatesRepository.findAll().size();

        // Update the userCertificates
        UserCertificates updatedUserCertificates = userCertificatesRepository.findOne(userCertificates.getId());
        updatedUserCertificates
                .userlogin(UPDATED_USERLOGIN)
                .certificateurl(UPDATED_CERTIFICATEURL);
        UserCertificatesDTO userCertificatesDTO = userCertificatesMapper.userCertificatesToUserCertificatesDTO(updatedUserCertificates);

        restUserCertificatesMockMvc.perform(put("/api/user-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCertificatesDTO)))
            .andExpect(status().isOk());

        // Validate the UserCertificates in the database
        List<UserCertificates> userCertificatesList = userCertificatesRepository.findAll();
        assertThat(userCertificatesList).hasSize(databaseSizeBeforeUpdate);
        UserCertificates testUserCertificates = userCertificatesList.get(userCertificatesList.size() - 1);
        assertThat(testUserCertificates.getUserlogin()).isEqualTo(UPDATED_USERLOGIN);
        assertThat(testUserCertificates.getCertificateurl()).isEqualTo(UPDATED_CERTIFICATEURL);
    }

    @Test
    public void updateNonExistingUserCertificates() throws Exception {
        int databaseSizeBeforeUpdate = userCertificatesRepository.findAll().size();

        // Create the UserCertificates
        UserCertificatesDTO userCertificatesDTO = userCertificatesMapper.userCertificatesToUserCertificatesDTO(userCertificates);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserCertificatesMockMvc.perform(put("/api/user-certificates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCertificatesDTO)))
            .andExpect(status().isCreated());

        // Validate the UserCertificates in the database
        List<UserCertificates> userCertificatesList = userCertificatesRepository.findAll();
        assertThat(userCertificatesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteUserCertificates() throws Exception {
        // Initialize the database
        userCertificatesRepository.save(userCertificates);
        int databaseSizeBeforeDelete = userCertificatesRepository.findAll().size();

        // Get the userCertificates
        restUserCertificatesMockMvc.perform(delete("/api/user-certificates/{id}", userCertificates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserCertificates> userCertificatesList = userCertificatesRepository.findAll();
        assertThat(userCertificatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCertificates.class);
    }
}
