/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
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
 * Annotation ProcessCleanup identifies a class method as implementing a means to cleanup a process after invocation.
 * <p/>
 * The ProcessCleanup method is guaranteed to be called after the {@link ProcessPrepare} method. And typically
 * after the {@link ProcessStart} or {@link ProcessComplete} methods are called. Neither will be called after
 * ProcessCleanup is called.
 * <p/>
 * This annotation is optional.
 *
 * @see ProcessPrepare
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProcessCleanup
  {
  }
