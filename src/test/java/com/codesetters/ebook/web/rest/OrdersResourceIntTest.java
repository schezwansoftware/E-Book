package com.codesetters.ebook.web.rest;

import com.codesetters.ebook.AbstractCassandraTest;
import com.codesetters.ebook.EbookApp;

import com.codesetters.ebook.domain.Orders;
import com.codesetters.ebook.repository.OrdersRepository;
import com.codesetters.ebook.service.OrdersService;
import com.codesetters.ebook.service.dto.OrdersDTO;
import com.codesetters.ebook.service.mapper.OrdersMapper;
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
 * Test class for the OrdersResource REST controller.
 *
 * @see OrdersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EbookApp.class)
public class OrdersResourceIntTest extends AbstractCassandraTest {

    private static final String DEFAULT_ORDERID = "AAAAAAAAAA";
    private static final String UPDATED_ORDERID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDERNAME = "AAAAAAAAAA";
    private static final String UPDATED_ORDERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORDERTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ORDERTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ORDERBY = "AAAAAAAAAA";
    private static final String UPDATED_ORDERBY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ORDERDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORDERDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ORDERATTENDEE = "AAAAAAAAAA";
    private static final String UPDATED_ORDERATTENDEE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISASSIGNED = false;
    private static final Boolean UPDATED_ISASSIGNED = true;

    private static final String DEFAULT_ORDERADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ORDERADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELIVERED = false;
    private static final Boolean UPDATED_DELIVERED = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restOrdersMockMvc;

    private Orders orders;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdersResource ordersResource = new OrdersResource(ordersService);
        this.restOrdersMockMvc = MockMvcBuilders.standaloneSetup(ordersResource)
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
    public static Orders createEntity() {
        Orders orders = new Orders()
            .orderid(DEFAULT_ORDERID)
            .ordername(DEFAULT_ORDERNAME)
            .ordertype(DEFAULT_ORDERTYPE)
            .orderby(DEFAULT_ORDERBY)
            .orderdate(DEFAULT_ORDERDATE)
            .orderattendee(DEFAULT_ORDERATTENDEE)
            .isassigned(DEFAULT_ISASSIGNED)
            .orderaddress(DEFAULT_ORDERADDRESS)
            .delivered(DEFAULT_DELIVERED)
            .description(DEFAULT_DESCRIPTION);
        return orders;
    }

    @Before
    public void initTest() {
        ordersRepository.deleteAll();
        orders = createEntity();
    }

    @Test
    public void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderid()).isEqualTo(DEFAULT_ORDERID);
        assertThat(testOrders.getOrdername()).isEqualTo(DEFAULT_ORDERNAME);
        assertThat(testOrders.getOrdertype()).isEqualTo(DEFAULT_ORDERTYPE);
        assertThat(testOrders.getOrderby()).isEqualTo(DEFAULT_ORDERBY);
        assertThat(testOrders.getOrderdate()).isEqualTo(DEFAULT_ORDERDATE);
        assertThat(testOrders.getOrderattendee()).isEqualTo(DEFAULT_ORDERATTENDEE);
        assertThat(testOrders.isIsassigned()).isEqualTo(DEFAULT_ISASSIGNED);
        assertThat(testOrders.getOrderaddress()).isEqualTo(DEFAULT_ORDERADDRESS);
        assertThat(testOrders.isDelivered()).isEqualTo(DEFAULT_DELIVERED);
        assertThat(testOrders.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders with an existing ID
        orders.setId(UUID.randomUUID());
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllOrders() throws Exception {
        // Initialize the database
        ordersRepository.save(orders);

        // Get all the ordersList
        restOrdersMockMvc.perform(get("/api/orders"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().toString())))
            .andExpect(jsonPath("$.[*].orderid").value(hasItem(DEFAULT_ORDERID.toString())))
            .andExpect(jsonPath("$.[*].ordername").value(hasItem(DEFAULT_ORDERNAME.toString())))
            .andExpect(jsonPath("$.[*].ordertype").value(hasItem(DEFAULT_ORDERTYPE.toString())))
            .andExpect(jsonPath("$.[*].orderby").value(hasItem(DEFAULT_ORDERBY.toString())))
            .andExpect(jsonPath("$.[*].orderdate").value(hasItem(sameInstant(DEFAULT_ORDERDATE))))
            .andExpect(jsonPath("$.[*].orderattendee").value(hasItem(DEFAULT_ORDERATTENDEE.toString())))
            .andExpect(jsonPath("$.[*].isassigned").value(hasItem(DEFAULT_ISASSIGNED.booleanValue())))
            .andExpect(jsonPath("$.[*].orderaddress").value(hasItem(DEFAULT_ORDERADDRESS.toString())))
            .andExpect(jsonPath("$.[*].delivered").value(hasItem(DEFAULT_DELIVERED.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    public void getOrders() throws Exception {
        // Initialize the database
        ordersRepository.save(orders);

        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orders.getId().toString()))
            .andExpect(jsonPath("$.orderid").value(DEFAULT_ORDERID.toString()))
            .andExpect(jsonPath("$.ordername").value(DEFAULT_ORDERNAME.toString()))
            .andExpect(jsonPath("$.ordertype").value(DEFAULT_ORDERTYPE.toString()))
            .andExpect(jsonPath("$.orderby").value(DEFAULT_ORDERBY.toString()))
            .andExpect(jsonPath("$.orderdate").value(sameInstant(DEFAULT_ORDERDATE)))
            .andExpect(jsonPath("$.orderattendee").value(DEFAULT_ORDERATTENDEE.toString()))
            .andExpect(jsonPath("$.isassigned").value(DEFAULT_ISASSIGNED.booleanValue()))
            .andExpect(jsonPath("$.orderaddress").value(DEFAULT_ORDERADDRESS.toString()))
            .andExpect(jsonPath("$.delivered").value(DEFAULT_DELIVERED.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOrders() throws Exception {
        // Initialize the database
        ordersRepository.save(orders);
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        Orders updatedOrders = ordersRepository.findOne(orders.getId());
        updatedOrders
            .orderid(UPDATED_ORDERID)
            .ordername(UPDATED_ORDERNAME)
            .ordertype(UPDATED_ORDERTYPE)
            .orderby(UPDATED_ORDERBY)
            .orderdate(UPDATED_ORDERDATE)
            .orderattendee(UPDATED_ORDERATTENDEE)
            .isassigned(UPDATED_ISASSIGNED)
            .orderaddress(UPDATED_ORDERADDRESS)
            .delivered(UPDATED_DELIVERED)
            .description(UPDATED_DESCRIPTION);
        OrdersDTO ordersDTO = ordersMapper.toDto(updatedOrders);

        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderid()).isEqualTo(UPDATED_ORDERID);
        assertThat(testOrders.getOrdername()).isEqualTo(UPDATED_ORDERNAME);
        assertThat(testOrders.getOrdertype()).isEqualTo(UPDATED_ORDERTYPE);
        assertThat(testOrders.getOrderby()).isEqualTo(UPDATED_ORDERBY);
        assertThat(testOrders.getOrderdate()).isEqualTo(UPDATED_ORDERDATE);
        assertThat(testOrders.getOrderattendee()).isEqualTo(UPDATED_ORDERATTENDEE);
        assertThat(testOrders.isIsassigned()).isEqualTo(UPDATED_ISASSIGNED);
        assertThat(testOrders.getOrderaddress()).isEqualTo(UPDATED_ORDERADDRESS);
        assertThat(testOrders.isDelivered()).isEqualTo(UPDATED_DELIVERED);
        assertThat(testOrders.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteOrders() throws Exception {
        // Initialize the database
        ordersRepository.save(orders);
        int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Get the orders
        restOrdersMockMvc.perform(delete("/api/orders/{id}", orders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orders.class);
        Orders orders1 = new Orders();
        orders1.setId(UUID.randomUUID());
        Orders orders2 = new Orders();
        orders2.setId(orders1.getId());
        assertThat(orders1).isEqualTo(orders2);
        orders2.setId(UUID.randomUUID());
        assertThat(orders1).isNotEqualTo(orders2);
        orders1.setId(null);
        assertThat(orders1).isNotEqualTo(orders2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdersDTO.class);
        OrdersDTO ordersDTO1 = new OrdersDTO();
        ordersDTO1.setId(UUID.randomUUID());
        OrdersDTO ordersDTO2 = new OrdersDTO();
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO2.setId(ordersDTO1.getId());
        assertThat(ordersDTO1).isEqualTo(ordersDTO2);
        ordersDTO2.setId(UUID.randomUUID());
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
        ordersDTO1.setId(null);
        assertThat(ordersDTO1).isNotEqualTo(ordersDTO2);
    }
}
