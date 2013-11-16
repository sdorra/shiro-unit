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

//~--- JDK imports ------------------------------------------------------------

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@link SubjectAware} annotation starts the shiro security system before
 * a test methods starts. The annotation can be used on class, method level
 * or in a custom annotation.
 *
 * @author Sebastian Sdorra
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface SubjectAware
{

  /**
   * Username for authentication. The username should be configured in the
   * configuration ini.
   *
   * @return username for authentication
   */
  String username()      default "";

  /**
   * Password for authentication.
   *
   * @return  password for authentication
   */
  String password()      default "";

  /**
   * Classpath path to shiro ini configuration, if the ini is not specified an
   * empty configuration is loaded.
   *
   * @return path to shiro ini
   */
  String configuration() default "";
}
