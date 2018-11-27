# testcontainers-java-postgres

Use testcontainer to start postgres docker container for integration tests

## How to use
Add Testcontainers Postgres as testCompile Dependency
```
testCompile 'org.testcontainers:postgresql:1.10.1'
```

Prepare Postgres Config in abstract class
```java
@RunWith(SpringJUnit4ClassRunner.class)
   @SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
   @ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
   public abstract class AbstractIntegrationTest {
       private static final String TEST_DATABASE_NAME = "test-db";
       private static final String TEST_USER = "klausimausi";
       private static final String TEST_PASSWORD = "klausimausi";
   
   
       @ClassRule
       public static PostgreSQLContainer postgres = new PostgreSQLContainer()
               .withDatabaseName(TEST_DATABASE_NAME)
               .withUsername(TEST_USER)
               .withPassword(TEST_PASSWORD);
   
       public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
           private static final String TEST_DATASOURCE = postgres.getJdbcUrl();
   
           @Override
           public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {
               TestPropertyValues values = TestPropertyValues.of(
                       "spring.datasource.url=" + TEST_DATASOURCE,
                       "spring.datasource.username=" + TEST_USER,
                       "spring.datasource.password=" + TEST_PASSWORD
               );
               values.applyTo(configurableApplicationContext);
           }
       }
   }
```

Inherit from abstract class in Integration Test
```java
@RunWith(SpringRunner.class)
   @SpringBootTest
   @ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
   public class HelloControllerTest extends AbstractIntegrationTest {
   
       @Autowired
       private WebApplicationContext ctx;
   
       private MockMvc mockMvc;
   
       @Before
       public void init() {
           this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build();
       }
   
       @Test
       public void testSpringBootContext() throws Exception {
           Hello hello = new Hello();
           hello.setName("{\"name\": \"robin\"}");
   
           System.out.print(hello.toString());
   
           this.mockMvc.perform(
                   post("/hello")
                           .contentType(MediaType.APPLICATION_JSON_UTF8)
                           .content(hello.toString()))
                   .andExpect(status().isOk()).andExpect(content().string("Nice"));
   
           this.mockMvc.perform(
                   get("/hello")
                           .contentType(MediaType.APPLICATION_JSON_UTF8)
           ).andExpect(status().isOk());
       }
```