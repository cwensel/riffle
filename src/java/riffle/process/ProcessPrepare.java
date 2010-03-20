/*
 * Copyright (c) 2007-2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package riffle.process;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation ProcessPrepare identifies a class method as implementing a means to prepare a process before invocation.
 * <p/>
 * The ProcessPrepare method is guaranteed to be called before the {@link ProcessCleanup} method.
 *
 * @see ProcessCleanup
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProcessPrepare
  {
  }
