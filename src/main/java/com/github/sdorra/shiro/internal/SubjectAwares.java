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



package com.github.sdorra.shiro.internal;

//~--- non-JDK imports --------------------------------------------------------

import com.github.sdorra.shiro.SubjectAware;

//~--- JDK imports ------------------------------------------------------------

import java.lang.annotation.Annotation;

/**
 *
 * @author Sebastian Sdorra
 */
public final class SubjectAwares
{

  /**
   * Constructs ...
   *
   */
  private SubjectAwares() {}

  //~--- methods --------------------------------------------------------------

  /**
   *  Method description
   *
   *
   *
   *  @param object
   *  @return
   */
  public static SubjectAware find(Object object)
  {
    return find(object.getClass());
  }

  /**
   * Method description
   *
   *
   * @param clazz
   *
   * @return
   */
  public static SubjectAware find(Class<?> clazz)
  {
    return find(clazz.getAnnotations());
  }

  /**
   * Method description
   *
   *
   * @param annotations
   *
   * @return
   */
  public static SubjectAware find(Annotation... annotations)
  {
    return findInternal(true, annotations);
  }

  /**
   * Method description
   *
   *
   * @param recursive
   * @param annotations
   *
   * @return
   */
  private static SubjectAware findInternal(boolean recursive,
    Annotation... annotations)
  {
    SubjectAware subjectAware = null;

    for (Annotation annotation : annotations)
    {
      if (annotation.annotationType().isAssignableFrom(SubjectAware.class))
      {
        subjectAware = (SubjectAware) annotation;

        break;
      }
    }

    if ((subjectAware == null) && recursive)
    {
      for (Annotation annotation : annotations)
      {
        subjectAware = findInternal(false,
          annotation.annotationType().getAnnotations());
      }
    }

    return subjectAware;
  }
}
