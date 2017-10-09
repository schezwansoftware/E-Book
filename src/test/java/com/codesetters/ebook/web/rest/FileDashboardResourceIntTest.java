package com.codesetters.ebook.web.rest;

import com.codesetters.ebook.AbstractCassandraTest;
import com.codesetters.ebook.EbookApp;

import com.codesetters.ebook.domain.FileDashboard;
import com.codesetters.ebook.repository.FileDashboardRepository;
import com.codesetters.ebook.service.FileDashboardService;
import com.codesetters.ebook.service.dto.FileDashboardDTO;
import com.codesetters.ebook.service.mapper.FileDashboardMapper;
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
 * Test class for the FileDashboardResource REST controller.
 *
 * @see FileDashboardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbookApp.class)
public class FileDashboardResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final Long DEFAULT_FILESIZE = 1L;
    private static final Long UPDATED_FILESIZE = 2L;

    private static final String DEFAULT_CREATEDBY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDBY = "BBBBBBBBBB";

    @Autowired
    private FileDashboardRepository fileDashboardRepository;

    @Autowired
    private FileDashboardMapper fileDashboardMapper;

    @Autowired
    private FileDashboardService fileDashboardService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFileDashboardMockMvc;

    private FileDashboard fileDashboard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileDashboardResource fileDashboardResource = new FileDashboardResource(fileDashboardService);
        this.restFileDashboardMockMvc = MockMvcBuilders.standaloneSetup(fileDashboardResource)
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
    public static FileDashboard createEntity() {
        FileDashboard fileDashboard = new FileDashboard()
            .filename(DEFAULT_FILENAME)
            .filesize(DEFAULT_FILESIZE)
            .createdby(DEFAULT_CREATEDBY);
        return fileDashboard;
    }

    @Before
    public void initTest() {
        fileDashboardRepository.deleteAll();
        fileDashboard = createEntity();
    }

    @Test
    public void createFileDashboard() throws Exception {
        int databaseSizeBeforeCreate = fileDashboardRepository.findAll().size();

        // Create the FileDashboard
        FileDashboardDTO fileDashboardDTO = fileDashboardMapper.toDto(fileDashboard);
        restFileDashboardMockMvc.perform(post("/api/file-dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDashboardDTO)))
            .andExpect(status().isCreated());

        // Validate the FileDashboard in the database
        List<FileDashboard> fileDashboardList = fileDashboardRepository.findAll();
        assertThat(fileDashboardList).hasSize(databaseSizeBeforeCreate + 1);
        FileDashboard testFileDashboard = fileDashboardList.get(fileDashboardList.size() - 1);
        assertThat(testFileDashboard.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testFileDashboard.getFilesize()).isEqualTo(DEFAULT_FILESIZE);
        assertThat(testFileDashboard.getCreatedby()).isEqualTo(DEFAULT_CREATEDBY);
    }

    @Test
    public void createFileDashboardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileDashboardRepository.findAll().size();

        // Create the FileDashboard with an existing ID
        fileDashboard.setId(UUID.randomUUID());
        FileDashboardDTO fileDashboardDTO = fileDashboardMapper.toDto(fileDashboard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileDashboardMockMvc.perform(post("/api/file-dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDashboardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileDashboard in the database
        List<FileDashboard> fileDashboardList = fileDashboardRepository.findAll();
        assertThat(fileDashboardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFileDashboards() throws Exception {
        // Initialize the database
        fileDashboardRepository.save(fileDashboard);

        // Get all the fileDashboardList
        restFileDashboardMockMvc.perform(get("/api/file-dashboards"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileDashboard.getId().toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].filesize").value(hasItem(DEFAULT_FILESIZE.intValue())))
            .andExpect(jsonPath("$.[*].createdby").value(hasItem(DEFAULT_CREATEDBY.toString())));
    }

    @Test
    public void getFileDashboard() throws Exception {
        // Initialize the database
        fileDashboardRepository.save(fileDashboard);

        // Get the fileDashboard
        restFileDashboardMockMvc.perform(get("/api/file-dashboards/{id}", fileDashboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileDashboard.getId().toString()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME.toString()))
            .andExpect(jsonPath("$.filesize").value(DEFAULT_FILESIZE.intValue()))
            .andExpect(jsonPath("$.createdby").value(DEFAULT_CREATEDBY.toString()));
    }

    @Test
    public void getNonExistingFileDashboard() throws Exception {
        // Get the fileDashboard
        restFileDashboardMockMvc.perform(get("/api/file-dashboards/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFileDashboard() throws Exception {
        // Initialize the database
        fileDashboardRepository.save(fileDashboard);
        int databaseSizeBeforeUpdate = fileDashboardRepository.findAll().size();

        // Update the fileDashboard
        FileDashboard updatedFileDashboard = fileDashboardRepository.findOne(fileDashboard.getId());
        updatedFileDashboard
            .filename(UPDATED_FILENAME)
            .filesize(UPDATED_FILESIZE)
            .createdby(UPDATED_CREATEDBY);
        FileDashboardDTO fileDashboardDTO = fileDashboardMapper.toDto(updatedFileDashboard);

        restFileDashboardMockMvc.perform(put("/api/file-dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDashboardDTO)))
            .andExpect(status().isOk());

        // Validate the FileDashboard in the database
        List<FileDashboard> fileDashboardList = fileDashboardRepository.findAll();
        assertThat(fileDashboardList).hasSize(databaseSizeBeforeUpdate);
        FileDashboard testFileDashboard = fileDashboardList.get(fileDashboardList.size() - 1);
        assertThat(testFileDashboard.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFileDashboard.getFilesize()).isEqualTo(UPDATED_FILESIZE);
        assertThat(testFileDashboard.getCreatedby()).isEqualTo(UPDATED_CREATEDBY);
    }

    @Test
    public void updateNonExistingFileDashboard() throws Exception {
        int databaseSizeBeforeUpdate = fileDashboardRepository.findAll().size();

        // Create the FileDashboard
        FileDashboardDTO fileDashboardDTO = fileDashboardMapper.toDto(fileDashboard);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFileDashboardMockMvc.perform(put("/api/file-dashboards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileDashboardDTO)))
            .andExpect(status().isCreated());

        // Validate the FileDashboard in the database
        List<FileDashboard> fileDashboardList = fileDashboardRepository.findAll();
        assertThat(fileDashboardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFileDashboard() throws Exception {
        // Initialize the database
        fileDashboardRepository.save(fileDashboard);
        int databaseSizeBeforeDelete = fileDashboardRepository.findAll().size();

        // Get the fileDashboard
        restFileDashboardMockMvc.perform(delete("/api/file-dashboards/{id}", fileDashboard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FileDashboard> fileDashboardList = fileDashboardRepository.findAll();
        assertThat(fileDashboardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileDashboard.class);
        FileDashboard fileDashboard1 = new FileDashboard();
        fileDashboard1.setId(UUID.randomUUID());
        FileDashboard fileDashboard2 = new FileDashboard();
        fileDashboard2.setId(fileDashboard1.getId());
        assertThat(fileDashboard1).isEqualTo(fileDashboard2);
        fileDashboard2.setId(UUID.randomUUID());
        assertThat(fileDashboard1).isNotEqualTo(fileDashboard2);
        fileDashboard1.setId(null);
        assertThat(fileDashboard1).isNotEqualTo(fileDashboard2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileDashboardDTO.class);
        FileDashboardDTO fileDashboardDTO1 = new FileDashboardDTO();
        fileDashboardDTO1.setId(UUID.randomUUID());
        FileDashboardDTO fileDashboardDTO2 = new FileDashboardDTO();
        assertThat(fileDashboardDTO1).isNotEqualTo(fileDashboardDTO2);
        fileDashboardDTO2.setId(fileDashboardDTO1.getId());
        assertThat(fileDashboardDTO1).isEqualTo(fileDashboardDTO2);
        fileDashboardDTO2.setId(UUID.randomUUID());
        assertThat(fileDashboardDTO1).isNotEqualTo(fileDashboardDTO2);
        fileDashboardDTO1.setId(null);
        assertThat(fileDashboardDTO1).isNotEqualTo(fileDashboardDTO2);
    }
}
