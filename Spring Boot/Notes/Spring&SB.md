# Spring Framework & Spring Boot - Notes & Interview Questions

---

## SPRING FRAMEWORK NOTES

### What is Spring Framework?
Spring is a comprehensive, lightweight, open-source Java framework for building enterprise applications. It provides infrastructure support so developers can focus on business logic. Its core is the IoC (Inversion of Control) container.

### Core Modules

| Module | Description |
|--------|-------------|
| spring-core | IoC container, DI fundamentals |
| spring-beans | Bean factory and bean lifecycle |
| spring-context | ApplicationContext, events, i18n |
| spring-aop | Aspect-Oriented Programming support |
| spring-jdbc | JDBC abstraction layer |
| spring-orm | ORM integration (JPA, Hibernate) |
| spring-tx | Transaction management |
| spring-web | Web support (MVC, REST) |
| spring-test | Testing support |

### IoC (Inversion of Control)
- The Spring container manages object creation and lifecycle instead of the developer.
- Objects declare their dependencies; Spring injects them.
- Two types of IoC containers:
  - `BeanFactory` — basic container, lazy initialization
  - `ApplicationContext` — advanced container, eager initialization, adds AOP, events, i18n

### Dependency Injection (DI)
- **Constructor Injection** — dependencies passed via constructor (recommended, immutable)
- **Setter Injection** — dependencies set via setter methods (optional dependencies)
- **Field Injection** — `@Autowired` directly on field (not recommended for testing)

```java
// Constructor Injection (preferred)
@Service
public class OrderService {
    private final PaymentService paymentService;
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

### Spring Bean Lifecycle
1. Instantiation
2. Populate properties (DI)
3. `BeanNameAware.setBeanName()`
4. `BeanFactoryAware.setBeanFactory()`
5. `ApplicationContextAware.setApplicationContext()`
6. `BeanPostProcessor.postProcessBeforeInitialization()`
7. `@PostConstruct` / `InitializingBean.afterPropertiesSet()`
8. Custom `init-method`
9. Bean is ready to use
10. `@PreDestroy` / `DisposableBean.destroy()`
11. Custom `destroy-method`

### Bean Scopes
| Scope | Description |
|-------|-------------|
| singleton | One instance per Spring container (default) |
| prototype | New instance every time requested |
| request | One instance per HTTP request (web only) |
| session | One instance per HTTP session (web only) |
| application | One instance per ServletContext (web only) |

### Spring AOP (Aspect-Oriented Programming)
- Separates cross-cutting concerns (logging, security, transactions) from business logic.
- Key concepts:
  - **Aspect** — module encapsulating a cross-cutting concern (`@Aspect`)
  - **Join Point** — a point in execution (method call)
  - **Advice** — action taken at a join point (`@Before`, `@After`, `@Around`, `@AfterReturning`, `@AfterThrowing`)
  - **Pointcut** — expression that matches join points
  - **Weaving** — linking aspects with application objects

```java
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.service.*.*(..))")
    public void logBefore(JoinPoint jp) {
        System.out.println("Calling: " + jp.getSignature().getName());
    }
}
```

### Spring MVC
- Model-View-Controller web framework built on the Servlet API.
- `DispatcherServlet` is the front controller — routes all requests.
- Flow: Request → DispatcherServlet → HandlerMapping → Controller → ViewResolver → View

### Spring Transaction Management
- Declarative: `@Transactional` (recommended)
- Programmatic: `TransactionTemplate`
- Propagation types:
  - `REQUIRED` — join existing or create new (default)
  - `REQUIRES_NEW` — always create new, suspend existing
  - `NESTED` — nested within existing transaction
  - `SUPPORTS` — join if exists, else non-transactional
  - `NOT_SUPPORTED` — suspend existing, run non-transactionally
  - `MANDATORY` — must have existing transaction, else exception
  - `NEVER` — must NOT have existing transaction, else exception
- Isolation levels: `READ_UNCOMMITTED`, `READ_COMMITTED`, `REPEATABLE_READ`, `SERIALIZABLE`

### Spring Events
- Publish/subscribe mechanism within the ApplicationContext.
- Publish: `applicationEventPublisher.publishEvent(new MyEvent(this))`
- Listen: `@EventListener` on a method or implement `ApplicationListener<MyEvent>`

### Spring Expression Language (SpEL)
- Powerful expression language for querying and manipulating objects at runtime.
- Used in `@Value`, `@ConditionalOnExpression`, `@PreAuthorize`, etc.
- Example: `@Value("#{systemProperties['user.name']}")`

---

## TOP 50 SPRING FRAMEWORK INTERVIEW QUESTIONS & ANSWERS

---

**S1. What is the Spring Framework?**
Spring is an open-source Java framework that provides comprehensive infrastructure support for enterprise applications. Its core feature is the IoC container which manages bean creation and dependency injection.

**S2. What is IoC (Inversion of Control)?**
IoC is a design principle where the control of object creation and dependency management is transferred from the application code to the Spring container. Instead of objects creating their dependencies, the container injects them.

**S3. What is Dependency Injection?**
DI is the implementation of IoC where dependencies are injected into objects by the container rather than the object creating them itself. Spring supports constructor, setter, and field injection.

**S4. What is the difference between BeanFactory and ApplicationContext?**
- `BeanFactory` — basic IoC container, lazy initialization, minimal features
- `ApplicationContext` — extends BeanFactory, adds AOP integration, event publishing, i18n, eager initialization. Preferred in most applications.

**S5. What is a Spring Bean?**
Any object managed by the Spring IoC container is called a Spring Bean. Beans are defined via `@Component`, `@Bean`, or XML configuration.

**S6. What are the ways to configure Spring Beans?**
- XML configuration (`applicationContext.xml`)
- Java-based configuration (`@Configuration` + `@Bean`)
- Annotation-based (`@Component`, `@Service`, `@Repository`, `@Controller`)

**S7. What is @Configuration?**
Marks a class as a source of bean definitions. Methods annotated with `@Bean` inside it return beans managed by the Spring container.

**S8. What is the Spring Bean lifecycle?**
Instantiation → DI → Aware interfaces → BeanPostProcessor (before) → `@PostConstruct`/init → Bean ready → `@PreDestroy`/destroy.

**S9. What is @PostConstruct and @PreDestroy?**
- `@PostConstruct` — runs after DI is complete, used for initialization logic
- `@PreDestroy` — runs before bean is destroyed, used for cleanup

**S10. What is the difference between singleton and prototype scope?**
Singleton creates one shared instance per container. Prototype creates a new instance every time the bean is requested. Singleton is the default.

**S11. What is Spring AOP?**
Aspect-Oriented Programming in Spring allows separating cross-cutting concerns (logging, security, transactions) from business logic using aspects, advice, and pointcuts.

**S12. What is the difference between @Before, @After, and @Around advice?**
- `@Before` — runs before the method
- `@After` — runs after the method (regardless of outcome)
- `@Around` — wraps the method, controls execution with `ProceedingJoinPoint.proceed()`
- `@AfterReturning` — runs after successful return
- `@AfterThrowing` — runs after exception is thrown

**S13. What is a Pointcut?**
A predicate/expression that matches join points. Defines where advice should be applied:
```java
@Pointcut("execution(* com.example.service.*.*(..))")
public void serviceMethods() {}
```

**S14. What is the difference between Spring AOP and AspectJ?**
- Spring AOP — proxy-based, works only on Spring beans, method-level join points only
- AspectJ — full AOP framework, compile/load-time weaving, supports field and constructor join points

**S15. What is Spring MVC?**
A web framework built on the Servlet API following the MVC pattern. `DispatcherServlet` acts as the front controller routing requests to appropriate handlers.

**S16. What is DispatcherServlet?**
The front controller in Spring MVC. It intercepts all incoming HTTP requests and delegates them to appropriate controllers via `HandlerMapping`.

**S17. What is HandlerMapping?**
Maps incoming requests to handler methods (controllers). Default is `RequestMappingHandlerMapping` which uses `@RequestMapping` annotations.

**S18. What is ViewResolver?**
Resolves logical view names returned by controllers to actual view templates (JSP, Thymeleaf, etc.). Example: `InternalResourceViewResolver`.

**S19. What is @Transactional and how does it work?**
Marks a method/class to run within a transaction. Spring creates a proxy around the bean; the proxy starts a transaction before the method and commits/rolls back after. Works only on public methods called from outside the bean.

**S20. What is transaction propagation?**
Defines how transactions behave when a transactional method calls another. `REQUIRED` (default) joins existing or creates new. `REQUIRES_NEW` always creates a new transaction.

**S21. What is the difference between @Component, @Service, @Repository, @Controller?**
All are specializations of `@Component` for component scanning. `@Repository` adds persistence exception translation. `@Service` signals business logic. `@Controller` signals web layer. Functionally similar but semantically different.

**S22. What is @Autowired?**
Tells Spring to inject a dependency automatically. Spring resolves by type first, then by name if multiple candidates exist. Use `@Qualifier` to disambiguate.

**S23. What is @Primary?**
When multiple beans of the same type exist, `@Primary` marks the default one to inject when no `@Qualifier` is specified.

**S24. What is @Lazy?**
Delays bean initialization until first use. Applied to `@Bean`, `@Component`, or `@Autowired` injection points.

**S25. What is the difference between @Inject and @Autowired?**
`@Autowired` is Spring-specific. `@Inject` is JSR-330 standard. Both perform DI; `@Autowired` has `required` attribute, `@Inject` does not.

**S26. What is ApplicationContext refresh?**
The process of loading bean definitions, instantiating singletons, and wiring dependencies. Called internally on startup. Can be triggered manually with `context.refresh()`.

**S27. What is the difference between @Bean and @Component?**
- `@Component` — class-level, Spring auto-detects via component scan
- `@Bean` — method-level in `@Configuration` class, gives full control over instantiation

**S28. What is @Scope?**
Defines the scope of a bean:
```java
@Bean
@Scope("prototype")
public MyBean myBean() { return new MyBean(); }
```

**S29. What is Spring's @EventListener?**
Listens to application events published via `ApplicationEventPublisher`:
```java
@EventListener
public void handleUserCreated(UserCreatedEvent event) { ... }
```

**S30. What is @Conditional?**
Registers a bean only when a condition is met. Base for all Spring Boot `@ConditionalOn*` annotations:
```java
@Bean
@Conditional(OnLinuxCondition.class)
public MyBean myBean() { ... }
```

**S31. What is the difference between @Resource and @Autowired?**
- `@Autowired` — injects by type (Spring)
- `@Resource` — injects by name first, then type (JSR-250)

**S32. What is a BeanPostProcessor?**
Allows custom modification of beans after instantiation but before use. Spring uses it internally for `@Autowired`, `@PostConstruct`, AOP proxying, etc.

**S33. What is a BeanFactoryPostProcessor?**
Allows modification of bean definitions (metadata) before beans are instantiated. `PropertySourcesPlaceholderConfigurer` is a common example.

**S34. What is Spring's Environment abstraction?**
Represents the environment in which the application runs. Provides access to profiles and properties via `Environment.getProperty()` and `Environment.acceptsProfiles()`.

**S35. What is @PropertySource?**
Loads properties from a file into the Spring Environment:
```java
@Configuration
@PropertySource("classpath:app.properties")
public class AppConfig { ... }
```

**S36. What is the difference between constructor and setter injection?**
Constructor injection is preferred for mandatory dependencies (ensures immutability, easier testing). Setter injection is for optional dependencies.

**S37. What is circular dependency in Spring?**
When Bean A depends on Bean B and Bean B depends on Bean A. Spring can resolve circular dependencies with setter/field injection (not constructor injection). Best practice is to redesign to avoid them.

**S38. What is Spring's JdbcTemplate?**
Simplifies JDBC operations by handling connection management, exception translation, and resource cleanup:
```java
jdbcTemplate.query("SELECT * FROM users", (rs, row) -> new User(rs.getLong("id"), rs.getString("name")));
```

**S39. What is Spring's RestTemplate?**
Synchronous HTTP client for consuming REST APIs. Deprecated in favor of `WebClient` for new applications.

**S40. What is WebClient?**
Reactive, non-blocking HTTP client from Spring WebFlux. Supports both sync and async calls:
```java
webClient.get().uri("/users/{id}", id).retrieve().bodyToMono(User.class);
```

**S41. What is Spring WebFlux?**
Reactive web framework built on Project Reactor. Supports non-blocking I/O for high-concurrency applications. Uses `Mono` (0-1 item) and `Flux` (0-N items).

**S42. What is the difference between Spring MVC and Spring WebFlux?**
- Spring MVC — synchronous, blocking, thread-per-request model
- Spring WebFlux — asynchronous, non-blocking, event-loop model (better for high concurrency)

**S43. What is @EnableAspectJAutoProxy?**
Enables support for handling `@Aspect` annotated components. Required when using Spring AOP with Java config.

**S44. What is the difference between JDK dynamic proxy and CGLIB proxy in Spring?**
- JDK proxy — used when the bean implements an interface
- CGLIB proxy — used when the bean does not implement an interface (subclasses the class)

**S45. What is Spring's @Cacheable?**
Caches the result of a method call. On subsequent calls with the same arguments, returns cached result without executing the method:
```java
@Cacheable("users")
public User findById(Long id) { ... }
```

**S46. What is @CacheEvict?**
Removes entries from the cache:
```java
@CacheEvict(value = "users", key = "#id")
public void deleteUser(Long id) { ... }
```

**S47. What is @CachePut?**
Updates the cache without skipping method execution. Used to keep cache in sync after updates.

**S48. What is Spring's MessageSource?**
Provides internationalization (i18n) support. Resolves messages from properties files based on locale.

**S49. What is @Async in Spring?**
Executes a method asynchronously in a separate thread. Requires `@EnableAsync` on a configuration class. Returns `void`, `Future`, or `CompletableFuture`.

**S50. What is the difference between Spring Framework and Spring Boot?**
Spring Framework is the core framework requiring manual configuration. Spring Boot is an opinionated extension that auto-configures Spring components, provides embedded servers, and reduces boilerplate — making it faster to build production-ready apps.

---

## SPRING BOOT NOTES

### What is Spring Boot?
Spring Boot is an opinionated, convention-over-configuration extension of the Spring Framework that simplifies the setup and development of Spring applications. It eliminates boilerplate configuration and provides production-ready features out of the box.

### Core Concepts

**Auto-Configuration**
- Spring Boot automatically configures your application based on the JARs present on the classpath.
- Uses `@EnableAutoConfiguration` (included in `@SpringBootApplication`).
- Auto-config classes are listed in `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`.

**Spring Boot Starters**
- Pre-packaged dependency descriptors that simplify Maven/Gradle configuration.
- Examples: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-security`, `spring-boot-starter-test`.

**Embedded Server**
- Spring Boot embeds Tomcat (default), Jetty, or Undertow — no need to deploy WAR files.
- Application runs as a standalone JAR: `java -jar app.jar`.

**@SpringBootApplication**
- Combines three annotations:
  - `@Configuration` — marks the class as a source of bean definitions.
  - `@EnableAutoConfiguration` — enables auto-configuration.
  - `@ComponentScan` — scans for components in the current package and sub-packages.

**application.properties / application.yml**
- Central configuration file for all Spring Boot settings.
- Supports profiles: `application-dev.properties`, `application-prod.properties`.
- Activated via `spring.profiles.active=dev`.

**Spring Boot Actuator**
- Provides production-ready endpoints: `/actuator/health`, `/actuator/metrics`, `/actuator/info`, `/actuator/env`.
- Add dependency: `spring-boot-starter-actuator`.

**Spring Boot DevTools**
- Enables automatic restart, live reload, and relaxed configuration during development.
- Add dependency: `spring-boot-devtools`.

**Externalized Configuration (Priority Order)**
1. Command-line arguments
2. `SPRING_APPLICATION_JSON`
3. OS environment variables
4. `application.properties` / `application.yml`
5. Default properties

**Spring Boot Layers**
- Presentation Layer → `@RestController`, `@Controller`
- Service Layer → `@Service`
- Repository/Data Layer → `@Repository`, Spring Data JPA
- Domain/Model Layer → `@Entity`

**Spring Data JPA**
- Simplifies database access using repositories.
- Extends `JpaRepository<Entity, ID>` for CRUD + pagination + sorting.
- Supports derived query methods: `findByEmailAndStatus(...)`.

**Spring Security (Basics)**
- Provides authentication and authorization.
- Configure via `SecurityFilterChain` bean.
- Supports JWT, OAuth2, Basic Auth, Form Login.

**Bean Scopes**
| Scope | Description |
|-------|-------------|
| singleton | One instance per Spring container (default) |
| prototype | New instance every time requested |
| request | One instance per HTTP request |
| session | One instance per HTTP session |
| application | One instance per ServletContext |

**Dependency Injection Types**
- Constructor Injection (recommended)
- Setter Injection
- Field Injection (`@Autowired` — not recommended for testing)

---

## TOP 100 SPRING BOOT INTERVIEW QUESTIONS & ANSWERS

> Questions S1–S50 above cover core Spring Framework. Questions 1–100 below cover Spring Boot specifically.

---

### BASICS

**1. What is Spring Boot?**
Spring Boot is a framework built on top of Spring that simplifies application development by providing auto-configuration, embedded servers, and opinionated defaults, reducing boilerplate code.

**2. What are the advantages of Spring Boot?**
- Auto-configuration
- Embedded server (no WAR deployment needed)
- Starter dependencies
- Production-ready features via Actuator
- Reduced boilerplate
- Easy integration with Spring ecosystem

**3. What is the difference between Spring and Spring Boot?**
Spring is a comprehensive framework requiring manual configuration. Spring Boot is an opinionated layer on top of Spring that auto-configures components based on classpath dependencies, reducing setup time.

**4. What does @SpringBootApplication do?**
It is a convenience annotation combining `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`.

**5. What is auto-configuration in Spring Boot?**
Auto-configuration automatically configures Spring beans based on the JARs on the classpath and the properties defined. It uses `@Conditional` annotations to apply configuration only when certain conditions are met.

**6. How do you disable a specific auto-configuration?**
```java
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
```
Or in `application.properties`:
```properties
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
```

**7. What is a Spring Boot Starter?**
A starter is a set of convenient dependency descriptors. For example, `spring-boot-starter-web` includes Spring MVC, Jackson, and an embedded Tomcat server.

**8. What is the default embedded server in Spring Boot?**
Apache Tomcat. It can be replaced with Jetty or Undertow.

**9. How do you change the embedded server to Jetty?**
Exclude Tomcat and add Jetty:
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
  <exclusions>
    <exclusion>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-tomcat</artifactId>
    </exclusion>
  </exclusions>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

**10. What is the default port of a Spring Boot application?**
`8080`. Change it with `server.port=9090` in `application.properties`.

---

### CONFIGURATION

**11. What is application.properties vs application.yml?**
Both are used for externalized configuration. YAML supports hierarchical structure and is more readable for complex configs. Both are functionally equivalent.

**12. What are Spring Boot profiles?**
Profiles allow different configurations for different environments (dev, test, prod). Activated via `spring.profiles.active=dev`. Profile-specific files: `application-dev.properties`.

**13. How do you read custom properties in Spring Boot?**
Using `@Value("${property.name}")` or `@ConfigurationProperties(prefix = "app")` for type-safe binding.

**14. What is @ConfigurationProperties?**
It binds external configuration properties to a POJO:
```java
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String name;
    private int timeout;
    // getters/setters
}
```

**15. What is the difference between @Value and @ConfigurationProperties?**
`@Value` injects a single property. `@ConfigurationProperties` binds a group of related properties to a class — better for structured config.

**16. How do you define environment-specific beans?**
Using `@Profile("dev")` on a bean or configuration class.

**17. What is Spring Boot's relaxed binding?**
Spring Boot accepts property names in various formats: `app.myName`, `app.my-name`, `APP_MY_NAME`, `app.my_name` — all map to the same field.

**18. How do you externalize configuration in production?**
Via environment variables, command-line arguments, or external config files. Spring Boot's property source priority ensures these override `application.properties`.

**19. What is @PropertySource?**
Used to load a custom properties file:
```java
@PropertySource("classpath:custom.properties")
```

**20. How do you configure multiple data sources in Spring Boot?**
Define two `DataSource` beans with `@Primary` on one, and use `@Qualifier` to inject the correct one. Use separate `@ConfigurationProperties` prefixes.

---

### ANNOTATIONS

**21. What is @RestController?**
A combination of `@Controller` and `@ResponseBody`. All methods return data directly (JSON/XML) instead of view names.

**22. What is the difference between @Controller and @RestController?**
`@Controller` returns view names (used with templates). `@RestController` returns data serialized to JSON/XML directly.

**23. What is @RequestMapping?**
Maps HTTP requests to handler methods. Can be applied at class or method level. Shorthand annotations: `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping`.

**24. What is @PathVariable vs @RequestParam?**
- `@PathVariable` extracts values from the URI path: `/users/{id}`
- `@RequestParam` extracts query parameters: `/users?id=1`

**25. What is @RequestBody?**
Binds the HTTP request body to a method parameter, typically deserializing JSON to a Java object.

**26. What is @ResponseBody?**
Indicates the return value of a method should be written directly to the HTTP response body.

**27. What is @Autowired?**
Marks a dependency to be injected by Spring's DI container. Can be applied to constructors, setters, or fields.

**28. What is @Component, @Service, @Repository?**
All are specializations of `@Component`:
- `@Service` — business logic layer
- `@Repository` — data access layer (also enables exception translation)
- `@Component` — generic Spring-managed component

**29. What is @Bean?**
Used in `@Configuration` classes to declare a Spring bean explicitly:
```java
@Bean
public MyService myService() { return new MyService(); }
```

**30. What is @Qualifier?**
Used alongside `@Autowired` to specify which bean to inject when multiple candidates exist.

---

### SPRING DATA JPA

**31. What is Spring Data JPA?**
A Spring module that simplifies JPA-based data access by providing repository abstractions. Extends `JpaRepository` for CRUD, pagination, and sorting.

**32. What is the difference between JpaRepository, CrudRepository, and PagingAndSortingRepository?**
- `CrudRepository` — basic CRUD
- `PagingAndSortingRepository` — adds pagination and sorting
- `JpaRepository` — extends both + adds JPA-specific methods like `flush()`, `saveAndFlush()`

**33. What are derived query methods?**
Spring Data JPA generates queries from method names:
```java
List<User> findByLastNameAndStatus(String lastName, String status);
```

**34. What is @Entity?**
Marks a class as a JPA entity mapped to a database table.

**35. What is @Id and @GeneratedValue?**
`@Id` marks the primary key field. `@GeneratedValue` specifies the generation strategy (AUTO, IDENTITY, SEQUENCE, TABLE).

**36. What is the difference between @OneToMany and @ManyToOne?**
- `@OneToMany` — one entity relates to many (e.g., one User has many Orders)
- `@ManyToOne` — many entities relate to one (e.g., many Orders belong to one User)

**37. What is FetchType.LAZY vs FetchType.EAGER?**
- LAZY — related entities are loaded on demand (default for collections)
- EAGER — related entities are loaded immediately with the parent

**38. What is @Transactional?**
Marks a method or class to run within a transaction. Spring manages commit/rollback automatically.

**39. What is the N+1 problem in JPA?**
When fetching a list of entities, JPA issues 1 query for the list + N queries for each related entity. Solved using `JOIN FETCH` in JPQL or `@EntityGraph`.

**40. What is JPQL?**
Java Persistence Query Language — an object-oriented query language similar to SQL but operates on entity objects:
```java
@Query("SELECT u FROM User u WHERE u.email = :email")
User findByEmail(@Param("email") String email);
```

---

### REST API

**41. What is a RESTful web service?**
A web service following REST principles: stateless, client-server, uniform interface, cacheable, layered system. Uses HTTP methods (GET, POST, PUT, DELETE) and resources identified by URIs.

**42. What HTTP status codes should REST APIs return?**
- 200 OK — success
- 201 Created — resource created
- 204 No Content — success with no body
- 400 Bad Request — invalid input
- 401 Unauthorized — authentication required
- 403 Forbidden — access denied
- 404 Not Found — resource not found
- 500 Internal Server Error — server error

**43. What is ResponseEntity?**
A Spring class that represents the full HTTP response including status code, headers, and body:
```java
return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
```

**44. How do you handle exceptions globally in Spring Boot?**
Using `@ControllerAdvice` with `@ExceptionHandler`:
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
```

**45. What is @ExceptionHandler?**
Handles exceptions thrown by controller methods. Can be local (in a controller) or global (in `@ControllerAdvice`).

**46. What is content negotiation in Spring Boot?**
The process of determining the response format (JSON, XML) based on the `Accept` header or URL extension. Configured via `ContentNegotiationConfigurer`.

**47. How do you validate request data in Spring Boot?**
Using Bean Validation (`@Valid`, `@NotNull`, `@Size`, `@Email`, etc.) with `spring-boot-starter-validation`:
```java
public ResponseEntity<?> create(@Valid @RequestBody UserDto dto) { ... }
```

**48. What is @Valid vs @Validated?**
- `@Valid` — standard JSR-303 validation
- `@Validated` — Spring's variant, supports group validation and can be used on class level for method-level validation

**49. What is HATEOAS?**
Hypermedia As The Engine Of Application State — REST responses include links to related actions/resources. Spring provides `spring-boot-starter-hateoas`.

**50. How do you implement pagination in Spring Boot REST APIs?**
Using `Pageable` from Spring Data:
```java
Page<User> findAll(Pageable pageable);
// Request: GET /users?page=0&size=10&sort=name,asc
```

---

### SPRING SECURITY

**51. What is Spring Security?**
A powerful authentication and authorization framework for Spring applications. Provides protection against common attacks (CSRF, session fixation, etc.).

**52. How do you secure a Spring Boot REST API with JWT?**
1. Add `spring-boot-starter-security` and a JWT library (e.g., `jjwt`).
2. Create a `JwtFilter` extending `OncePerRequestFilter`.
3. Configure `SecurityFilterChain` to add the filter and disable session management.
4. Issue tokens on login, validate on each request.

**53. What is the difference between authentication and authorization?**
- Authentication — verifying who you are (login)
- Authorization — verifying what you can do (permissions)

**54. What is CSRF and how does Spring handle it?**
Cross-Site Request Forgery — an attack that tricks users into submitting malicious requests. Spring Security enables CSRF protection by default. For stateless REST APIs, it's typically disabled.

**55. What is @PreAuthorize?**
Method-level security annotation:
```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) { ... }
```
Requires `@EnableMethodSecurity` on a configuration class.

**56. What is UserDetailsService?**
An interface used by Spring Security to load user-specific data during authentication:
```java
UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
```

**57. What is PasswordEncoder?**
An interface for encoding passwords. `BCryptPasswordEncoder` is the recommended implementation.

**58. What is SecurityFilterChain?**
The modern way (Spring Security 5.7+) to configure HTTP security, replacing the deprecated `WebSecurityConfigurerAdapter`.

**59. How do you implement OAuth2 in Spring Boot?**
Add `spring-boot-starter-oauth2-resource-server` or `spring-boot-starter-oauth2-client`. Configure issuer URI in `application.properties`. Spring Boot auto-configures the rest.

**60. What is the difference between @Secured and @PreAuthorize?**
- `@Secured` — simple role-based check: `@Secured("ROLE_ADMIN")`
- `@PreAuthorize` — supports SpEL expressions for complex conditions

---

### ACTUATOR & MONITORING

**61. What is Spring Boot Actuator?**
A sub-project providing production-ready features: health checks, metrics, environment info, thread dumps, etc., exposed via HTTP endpoints.

**62. What are common Actuator endpoints?**
- `/actuator/health` — application health
- `/actuator/metrics` — application metrics
- `/actuator/info` — app info
- `/actuator/env` — environment properties
- `/actuator/beans` — all Spring beans
- `/actuator/mappings` — all request mappings
- `/actuator/loggers` — logging configuration

**63. How do you expose all Actuator endpoints?**
```properties
management.endpoints.web.exposure.include=*
```

**64. How do you secure Actuator endpoints?**
Configure Spring Security to restrict access to `/actuator/**` to specific roles.

**65. How do you create a custom Actuator endpoint?**
```java
@Component
@Endpoint(id = "custom")
public class CustomEndpoint {
    @ReadOperation
    public Map<String, String> info() {
        return Map.of("status", "running");
    }
}
```

**66. What is the health indicator in Spring Boot?**
A component that contributes to the `/actuator/health` endpoint. Custom ones implement `HealthIndicator`:
```java
@Component
public class MyHealthIndicator implements HealthIndicator {
    public Health health() {
        return Health.up().withDetail("service", "running").build();
    }
}
```

**67. How do you integrate Spring Boot with Micrometer/Prometheus?**
Add `micrometer-registry-prometheus` dependency. Expose `/actuator/prometheus`. Configure Prometheus to scrape the endpoint.

**68. What is the difference between /health and /info?**
- `/health` — shows application health status (UP/DOWN) and component health
- `/info` — shows arbitrary application info (version, description) from `application.properties`

---

### TESTING

**69. What is @SpringBootTest?**
Loads the full application context for integration testing. Can be configured with `webEnvironment` to start an actual or mock server.

**70. What is @WebMvcTest?**
Loads only the web layer (controllers) for unit testing without starting the full context. Used with `MockMvc`.

**71. What is MockMvc?**
A Spring test utility for testing MVC controllers without starting a real HTTP server:
```java
mockMvc.perform(get("/users/1"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.name").value("John"));
```

**72. What is @DataJpaTest?**
Loads only JPA-related components (repositories, entities) for testing the data layer. Uses an in-memory database by default.

**73. What is @MockBean?**
Creates a Mockito mock and registers it as a Spring bean, replacing the real bean in the application context.

**74. What is the difference between @Mock and @MockBean?**
- `@Mock` — Mockito mock, not registered in Spring context
- `@MockBean` — Mockito mock registered in Spring context, replaces the real bean

**75. What is @TestConfiguration?**
Defines additional beans or overrides for use only in tests. Not picked up by component scanning in production.

**76. How do you test a Spring Boot REST API?**
Use `@SpringBootTest` with `TestRestTemplate` or `WebTestClient` for full integration tests, or `@WebMvcTest` with `MockMvc` for controller unit tests.

**77. What is @ExtendWith(SpringExtension.class)?**
Integrates Spring TestContext Framework with JUnit 5. Included automatically when using `@SpringBootTest`.

**78. What is Testcontainers?**
A Java library that provides lightweight, throwaway instances of databases and other services using Docker containers for integration testing.

---

### MICROSERVICES & ADVANCED

**79. How does Spring Boot support microservices?**
Through Spring Cloud (service discovery with Eureka, config server, API gateway, circuit breaker with Resilience4j, distributed tracing with Sleuth/Zipkin).

**80. What is Spring Cloud Config?**
A centralized external configuration server for distributed systems. Clients fetch config from the server at startup.

**81. What is a circuit breaker pattern?**
Prevents cascading failures by stopping calls to a failing service. Implemented with Resilience4j in Spring Boot:
```java
@CircuitBreaker(name = "userService", fallbackMethod = "fallback")
public User getUser(Long id) { ... }
```

**82. What is service discovery?**
Allows microservices to find each other dynamically. Spring Cloud Netflix Eureka is a common implementation. Services register themselves and discover others via the Eureka server.

**83. What is an API Gateway?**
A single entry point for all client requests. Spring Cloud Gateway provides routing, filtering, rate limiting, and load balancing.

**84. What is @FeignClient?**
A declarative HTTP client for inter-service communication:
```java
@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id);
}
```

**85. What is distributed tracing?**
Tracking requests across multiple microservices. Spring Boot integrates with Micrometer Tracing + Zipkin/Jaeger for this purpose.

**86. What is the difference between synchronous and asynchronous communication in microservices?**
- Synchronous — REST/gRPC, caller waits for response
- Asynchronous — messaging (Kafka, RabbitMQ), caller doesn't wait

**87. How do you implement async processing in Spring Boot?**
Enable with `@EnableAsync` and annotate methods with `@Async`:
```java
@Async
public CompletableFuture<String> processAsync() { ... }
```

**88. What is @Scheduled?**
Enables scheduled task execution:
```java
@Scheduled(cron = "0 0 * * * *")  // every hour
public void runTask() { ... }
```
Requires `@EnableScheduling`.

**89. What is Spring Boot's support for caching?**
Add `@EnableCaching` and use `@Cacheable`, `@CachePut`, `@CacheEvict` on methods. Supports providers like Redis, EhCache, Caffeine.

**90. How do you integrate Redis with Spring Boot?**
Add `spring-boot-starter-data-redis`. Configure `spring.redis.host` and `spring.redis.port`. Use `RedisTemplate` or Spring Cache abstraction.

---

### PERFORMANCE & BEST PRACTICES

**91. How do you optimize Spring Boot application startup time?**
- Use lazy initialization: `spring.main.lazy-initialization=true`
- Use Spring Native (GraalVM AOT compilation)
- Exclude unused auto-configurations
- Use `spring-boot-starter` only for needed modules

**92. What is Spring Boot's lazy initialization?**
Beans are created only when first requested instead of at startup. Reduces startup time but may delay first-request latency.

**93. What is connection pooling in Spring Boot?**
Spring Boot uses HikariCP by default for JDBC connection pooling. Configure with `spring.datasource.hikari.*` properties.

**94. How do you handle database migrations in Spring Boot?**
Using Flyway (`spring-boot-starter-flyway`) or Liquibase (`spring-boot-starter-liquibase`). Migration scripts run automatically on startup.

**95. What is the difference between Flyway and Liquibase?**
- Flyway — SQL-based migrations, simpler, versioned scripts
- Liquibase — XML/YAML/JSON/SQL changesets, more flexible, supports rollback

**96. How do you implement rate limiting in Spring Boot?**
Using Spring Cloud Gateway filters, Bucket4j library, or Resilience4j RateLimiter.

**97. What is Spring Boot's support for WebSockets?**
Add `spring-boot-starter-websocket`. Use `@EnableWebSocketMessageBroker` and configure a message broker (STOMP over WebSocket).

**98. How do you implement file upload in Spring Boot?**
Use `MultipartFile` in a controller:
```java
@PostMapping("/upload")
public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
    // process file
}
```
Configure limits: `spring.servlet.multipart.max-file-size=10MB`.

**99. What is Spring Boot's support for GraphQL?**
Spring Boot 2.7+ includes `spring-boot-starter-graphql`. Define schema in `.graphqls` files and implement `@QueryMapping`, `@MutationMapping` controllers.

**100. What are the best practices for building production-ready Spring Boot applications?**
- Use profiles for environment-specific config
- Externalize all secrets (use AWS Secrets Manager, Vault)
- Enable and secure Actuator endpoints
- Use structured logging (JSON) with correlation IDs
- Implement health checks and readiness/liveness probes
- Use connection pooling (HikariCP)
- Handle exceptions globally with `@ControllerAdvice`
- Validate all inputs with Bean Validation
- Use DTOs to decouple API from domain model
- Write unit + integration tests
- Use database migrations (Flyway/Liquibase)
- Enable HTTPS in production
- Implement circuit breakers for external calls
- Monitor with Micrometer + Prometheus + Grafana

---

*End of Spring Boot Notes & Interview Questions*
