/**
 * The MIT License
 *
 * Copyright (c) 2013, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */



package sonia.junit.shiro;

//~--- non-JDK imports --------------------------------------------------------

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Sebastian Sdorra
 */
public class ShiroRuleMethodTest
{

  /**
   * Method description
   *
   */
  @Test
  @SubjectAware(configuration = "classpath:sonia/junit/shiro/001.ini")
  public void testAnonymous()
  {
    Subject subject = SecurityUtils.getSubject();

    assertNotNull(subject);
    assertNull(subject.getPrincipal());
    assertNull(subject.getPrincipals());
    assertFalse(subject.isAuthenticated());
  }

  /**
   * Method description
   *
   */
  @Test
  @SubjectAware(
    username = "trillian",
    password = "secret",
    configuration = "classpath:sonia/junit/shiro/001.ini"
  )
  public void testAuthentication()
  {
    Subject subject = SecurityUtils.getSubject();

    assertNotNull(subject);
    assertTrue(subject.isAuthenticated());
    assertEquals("trillian", subject.getPrincipal());
  }

  /**
   * Method description
   *
   */
  @Test(expected = UnavailableSecurityManagerException.class)
  public void testWithoutAnnotation()
  {
    SecurityUtils.getSubject();
  }

  /**
   * Method description
   *
   */
  @Test
  @SubjectAware
  public void testWithoutConfiguration()
  {
    testAnonymous();
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  @Rule
  public ShiroRule rule = new ShiroRule();
}
