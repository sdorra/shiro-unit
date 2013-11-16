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
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.util.ThreadContext;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import sonia.junit.shiro.internal.SubjectAwareDescriptor;
import sonia.junit.shiro.internal.SubjectAwares;

/**
 * The shiro rule starts the apache shiro security system for each method or 
 * class which is annotated with the {@link SubjectAware} annotation.
 *
 * @author Sebastian Sdorra
 */
public class ShiroRule implements MethodRule
{

  /**
   * {@inheritDoc}
   */
  @Override
  public Statement apply(final Statement base, FrameworkMethod method,
    Object target)
  {
    final SubjectAwareDescriptor desc = new SubjectAwareDescriptor();
    SubjectAware subjectAware = SubjectAwares.find(target);

    if (subjectAware != null)
    {
      desc.merge(subjectAware);
    }

    subjectAware = SubjectAwares.find(method.getAnnotations());

    if (subjectAware != null)
    {
      desc.merge(subjectAware);
    }

    return new Statement()
    {

      @Override
      public void evaluate() throws Throwable
      {
        if (desc.isMerged())
        {
          initializeSecurityManager(desc);
        }

        try
        {
          base.evaluate();
        }
        finally
        {
          tearDownShiro();
        }
      }
    };
  }

  //~--- set methods ----------------------------------------------------------

  /**
   * Set a subject manually for the current method execution.
   *
   *
   * @param subject subject to set
   */
  public void setSubject(Subject subject)
  {
    ThreadContext.bind(subject);
  }

  //~--- methods --------------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param subjectAware
   */
  private void initializeSecurityManager(SubjectAwareDescriptor subjectAware)
  {
    String cfg = subjectAware.getConfiguration();

    if (cfg.length() > 0)
    {
      Factory<SecurityManager> factory = new IniSecurityManagerFactory(cfg);
      SecurityManager securityManager = factory.getInstance();

      SecurityUtils.setSecurityManager(securityManager);
    }

    String username = subjectAware.getUsername();

    if ((username != null) && (username.length() > 0))
    {
      UsernamePasswordToken token = new UsernamePasswordToken(username,
                                      subjectAware.getPassword());

      SecurityUtils.getSubject().login(token);
    }
  }

  /**
   * Method description
   *
   */
  private void tearDownShiro()
  {
    try
    {
      SecurityManager securityManager = SecurityUtils.getSecurityManager();

      LifecycleUtils.destroy(securityManager);
      ThreadContext.unbindSecurityManager();
      ThreadContext.unbindSubject();
      ThreadContext.remove();
    }
    catch (UnavailableSecurityManagerException e)
    {

      // we don't care about this when cleaning up the test environment
      // (for example, maybe the subclass is a unit test and it didn't
      // need a SecurityManager instance because it was using only mock Subject instances)
    }

    SecurityUtils.setSecurityManager(null);
  }
}
