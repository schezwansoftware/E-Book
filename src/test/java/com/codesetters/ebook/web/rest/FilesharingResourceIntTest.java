package com.codesetters.ebook.web.rest;

import com.codesetters.ebook.AbstractCassandraTest;
import com.codesetters.ebook.EbookApp;

import com.codesetters.ebook.domain.Filesharing;
import com.codesetters.ebook.repository.FilesharingRepository;
import com.codesetters.ebook.service.FilesharingService;
import com.codesetters.ebook.service.dto.FilesharingDTO;
import com.codesetters.ebook.service.mapper.FilesharingMapper;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FilesharingResource REST controller.
 *
 * @see FilesharingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbookApp.class)
public class FilesharingResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHAREDBY = "AAAAAAAAAA";
    private static final String UPDATED_SHAREDBY = "BBBBBBBBBB";

    private static final String DEFAULT_SHAREDTO = "AAAAAAAAAA";
    private static final String UPDATED_SHAREDTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SHAREDON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHAREDON = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_VERIFIED = false;
    private static final Boolean UPDATED_VERIFIED = true;

    @Autowired
    private FilesharingRepository filesharingRepository;

    @Autowired
    private FilesharingMapper filesharingMapper;

    @Autowired
    private FilesharingService filesharingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFilesharingMockMvc;

    private Filesharing filesharing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilesharingResource filesharingResource = new FilesharingResource(filesharingService);
        this.restFilesharingMockMvc = MockMvcBuilders.standaloneSetup(filesharingResource)
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
    public static Filesharing createEntity() {
        Filesharing filesharing = new Filesharing()
            .filename(DEFAULT_FILENAME)
            .sharedby(DEFAULT_SHAREDBY)
            .sharedto(DEFAULT_SHAREDTO)
            .sharedon(DEFAULT_SHAREDON)
            .verified(DEFAULT_VERIFIED);
        return filesharing;
    }

    @Before
    public void initTest() {
        filesharingRepository.deleteAll();
        filesharing = createEntity();
    }

    @Test
    public void createFilesharing() throws Exception {
        int databaseSizeBeforeCreate = filesharingRepository.findAll().size();

        // Create the Filesharing
        FilesharingDTO filesharingDTO = filesharingMapper.toDto(filesharing);
        restFilesharingMockMvc.perform(post("/api/filesharings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesharingDTO)))
            .andExpect(status().isCreated());

        // Validate the Filesharing in the database
        List<Filesharing> filesharingList = filesharingRepository.findAll();
        assertThat(filesharingList).hasSize(databaseSizeBeforeCreate + 1);
        Filesharing testFilesharing = filesharingList.get(filesharingList.size() - 1);
        assertThat(testFilesharing.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testFilesharing.getSharedby()).isEqualTo(DEFAULT_SHAREDBY);
        assertThat(testFilesharing.getSharedto()).isEqualTo(DEFAULT_SHAREDTO);
        assertThat(testFilesharing.getSharedon()).isEqualTo(DEFAULT_SHAREDON);
        assertThat(testFilesharing.isVerified()).isEqualTo(DEFAULT_VERIFIED);
    }

    @Test
    public void createFilesharingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filesharingRepository.findAll().size();

        // Create the Filesharing with an existing ID
        filesharing.setId(UUID.randomUUID());
        FilesharingDTO filesharingDTO = filesharingMapper.toDto(filesharing);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilesharingMockMvc.perform(post("/api/filesharings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesharingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Filesharing in the database
        List<Filesharing> filesharingList = filesharingRepository.findAll();
        assertThat(filesharingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFilesharings() throws Exception {
        // Initialize the database
        filesharingRepository.save(filesharing);

        // Get all the filesharingList
        restFilesharingMockMvc.perform(get("/api/filesharings"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filesharing.getId().toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].sharedby").value(hasItem(DEFAULT_SHAREDBY.toString())))
            .andExpect(jsonPath("$.[*].sharedto").value(hasItem(DEFAULT_SHAREDTO.toString())))
            .andExpect(jsonPath("$.[*].sharedon").value(hasItem(DEFAULT_SHAREDON.toString())))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())));
    }

    @Test
    public void getFilesharing() throws Exception {
        // Initialize the database
        filesharingRepository.save(filesharing);

        // Get the filesharing
        restFilesharingMockMvc.perform(get("/api/filesharings/{id}", filesharing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filesharing.getId().toString()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME.toString()))
            .andExpect(jsonPath("$.sharedby").value(DEFAULT_SHAREDBY.toString()))
            .andExpect(jsonPath("$.sharedto").value(DEFAULT_SHAREDTO.toString()))
            .andExpect(jsonPath("$.sharedon").value(DEFAULT_SHAREDON.toString()))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()));
    }

    @Test
    public void getNonExistingFilesharing() throws Exception {
        // Get the filesharing
        restFilesharingMockMvc.perform(get("/api/filesharings/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFilesharing() throws Exception {
        // Initialize the database
        filesharingRepository.save(filesharing);
        int databaseSizeBeforeUpdate = filesharingRepository.findAll().size();

        // Update the filesharing
        Filesharing updatedFilesharing = filesharingRepository.findOne(filesharing.getId());
        updatedFilesharing
            .filename(UPDATED_FILENAME)
            .sharedby(UPDATED_SHAREDBY)
            .sharedto(UPDATED_SHAREDTO)
            .sharedon(UPDATED_SHAREDON)
            .verified(UPDATED_VERIFIED);
        FilesharingDTO filesharingDTO = filesharingMapper.toDto(updatedFilesharing);

        restFilesharingMockMvc.perform(put("/api/filesharings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesharingDTO)))
            .andExpect(status().isOk());

        // Validate the Filesharing in the database
        List<Filesharing> filesharingList = filesharingRepository.findAll();
        assertThat(filesharingList).hasSize(databaseSizeBeforeUpdate);
        Filesharing testFilesharing = filesharingList.get(filesharingList.size() - 1);
        assertThat(testFilesharing.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFilesharing.getSharedby()).isEqualTo(UPDATED_SHAREDBY);
        assertThat(testFilesharing.getSharedto()).isEqualTo(UPDATED_SHAREDTO);
        assertThat(testFilesharing.getSharedon()).isEqualTo(UPDATED_SHAREDON);
        assertThat(testFilesharing.isVerified()).isEqualTo(UPDATED_VERIFIED);
    }

    @Test
    public void updateNonExistingFilesharing() throws Exception {
        int databaseSizeBeforeUpdate = filesharingRepository.findAll().size();

        // Create the Filesharing
        FilesharingDTO filesharingDTO = filesharingMapper.toDto(filesharing);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilesharingMockMvc.perform(put("/api/filesharings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filesharingDTO)))
            .andExpect(status().isCreated());

        // Validate the Filesharing in the database
        List<Filesharing> filesharingList = filesharingRepository.findAll();
        assertThat(filesharingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFilesharing() throws Exception {
        // Initialize the database
        filesharingRepository.save(filesharing);
        int databaseSizeBeforeDelete = filesharingRepository.findAll().size();

        // Get the filesharing
        restFilesharingMockMvc.perform(delete("/api/filesharings/{id}", filesharing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Filesharing> filesharingList = filesharingRepository.findAll();
        assertThat(filesharingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filesharing.class);
        Filesharing filesharing1 = new Filesharing();
        filesharing1.setId(UUID.randomUUID());
        Filesharing filesharing2 = new Filesharing();
        filesharing2.setId(filesharing1.getId());
        assertThat(filesharing1).isEqualTo(filesharing2);
        filesharing2.setId(UUID.randomUUID());
        assertThat(filesharing1).isNotEqualTo(filesharing2);
        filesharing1.setId(null);
        assertThat(filesharing1).isNotEqualTo(filesharing2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FilesharingDTO.class);
        FilesharingDTO filesharingDTO1 = new FilesharingDTO();
        filesharingDTO1.setId(UUID.randomUUID());
        FilesharingDTO filesharingDTO2 = new FilesharingDTO();
        assertThat(filesharingDTO1).isNotEqualTo(filesharingDTO2);
        filesharingDTO2.setId(filesharingDTO1.getId());
        assertThat(filesharingDTO1).isEqualTo(filesharingDTO2);
        filesharingDTO2.setId(UUID.randomUUID());
        assertThat(filesharingDTO1).isNotEqualTo(filesharingDTO2);
        filesharingDTO1.setId(null);
        assertThat(filesharingDTO1).isNotEqualTo(filesharingDTO2);
    }
}
