## Basic Mockito with Spring Boot ##
Setup a basic REST api with 3 layers (controller, service and repository) and how to use Mockito to test for each layer.

### Dependency ###
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
    </dependency>
</dependencies>
```

### Coding ###

1. UserController
```java
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseBody
    public UserDto getUser(@RequestParam("name") String name) {
        return userService.getUser(name);
    }
}
```

2. UserService
```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto getUser(String name) {
        User user = userRepository.findOne(name);
        return convert(user);
    }

    private UserDto convert(User user) {
        return new UserDto(user.getId(), user.getName());
    }
}
```

3. UserRepository
```java
@Repository
public class UserRepository {

    public User findOne(String name) {
        User user = new User();
        user.setId("1");
        user.setName(name);

        return user;
    }
}
```

4. User
```java
public class User {

    private String id;
    private String name;

    // getters & setters
}
```

5. UserDto
```java
public class UserDto {
    private String id;
    private String name;
    private Long timestamp;

    public UserDto(String id, String name) {
        this.id = id;
        this.name = name;
        this.timestamp = System.currentTimeMillis();
    }

    // getters & setters
}
```

### Unit Testing ###

**Test service layer: UserServiceTest**

The test run with MockitoJUnitRunner
```java
@RunWith(MockitoJUnitRunner.class)
```

The class need to be tested is UserService, so inject mock the service
```java
@InjectMocks
private UserService userService;
```

UserService use UserRepository as a dependency for getting user (from the database), mock the UserRepository class
```java
@Mock
private UserRepository userRepository;
```

I want to return a mock user, so just mock the User and setup for UserRepository
```java
when(user.getId()).thenReturn("10");
when(user.getName()).thenReturn("hoangdieuctu");

when(userRepository.findOne("hoangdieuctu")).thenReturn(user);
```

The full test class
```java
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Before
    public void setup() {
        when(user.getId()).thenReturn("10");
        when(user.getName()).thenReturn("hoangdieuctu");

        when(userRepository.findOne("hoangdieuctu")).thenReturn(user);
    }

    @Test
    public void testGetUser() {
        UserDto found = userService.getUser("hoangdieuctu");
        Assert.assertEquals("hoangdieuctu", found.getName());
        Assert.assertEquals("10", found.getId());
    }
}
```

**Test controller layer: UserControllerTest**
It will similar with service layer, but we need to use MockMvc object to execute REST call.

On setup method
```java
this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
```

The test method, execute the call and valid json response
```java
@Test
public void testGetUser() throws Exception {
    this.mockMvc.perform(get("/user?name=hoangdieuctu"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is("10")))
            .andExpect(jsonPath("$.name", is("hoangdieuctu")))
            .andExpect(jsonPath("$.timestamp", is(notNullValue())));
}
```

The full class
```java
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private UserDto userDto;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userDto = new UserDto("10", "hoangdieuctu");
        when(userService.getUser("hoangdieuctu")).thenReturn(userDto);
    }

    @Test
    public void testGetUser() throws Exception {
        this.mockMvc.perform(get("/user?name=hoangdieuctu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("10")))
                .andExpect(jsonPath("$.name", is("hoangdieuctu")))
                .andExpect(jsonPath("$.timestamp", is(notNullValue())));
    }
}
```