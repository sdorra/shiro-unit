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



package sonia.junit.shiro.internal;

//~--- non-JDK imports --------------------------------------------------------

import sonia.junit.shiro.SubjectAware;

/**
 *
 * @author Sebastian Sdorra
 */
public class SubjectAwareDescriptor
{

  /**
   * Constructs ...
   *
   */
  public SubjectAwareDescriptor() {}

  /**
   * Constructs ...
   *
   *
   * @param subjectAware
   */
  public SubjectAwareDescriptor(SubjectAware subjectAware)
  {
    username = subjectAware.username();
    password = subjectAware.password();
    configuration = subjectAware.configuration();
  }

  //~--- methods --------------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param subjectAware
   */
  public void merge(SubjectAware subjectAware)
  {
    merged = true;

    String username = subjectAware.username();

    if (username.length() > 0)
    {
      this.username = username;
    }

    String password = subjectAware.password();

    if (password.length() > 0)
    {
      this.password = password;
    }

    String configuration = subjectAware.configuration();

    if (configuration.length() > 0)
    {
      this.configuration = configuration;
    }
  }

  //~--- get methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @return
   */
  public String getConfiguration()
  {
    return configuration;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getPassword()
  {
    if (password == null)
    {
      password = "";
    }

    return password;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getUsername()
  {
    return username;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public boolean isMerged()
  {
    return merged;
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  private String configuration = "classpath:sonia/junit/shiro/empty.ini";

  /** Field description */
  private boolean merged = false;

  /** Field description */
  private String password;

  /** Field description */
  private String username;
}
