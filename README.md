shiro-unit
=========

JUnit rule to simplify apache shiro unit tests.

##Sample:

```ini
[users]
trillian = secret, user

[roles]
admin = *
user = something:*
```

```java
public class ShiroUnitTest {

  @Test
  @SubjectAware(
    username = "trillian",
    password = "secret",
    configuration = "classpath:path/to/shiro.ini"
  )
  public void testMethod(){
    Subject subject = SecurityUtils.getSubject();

    assertNotNull(subject);
    assertTrue(subject.isAuthenticated());
    assertEquals("trillian", subject.getPrincipal());
  }

  @Rule
  public ShiroRule rule = new ShiroRule();
}
```
