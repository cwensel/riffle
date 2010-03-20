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
 * Annotation ProcessComplete identifies a class method as implementing a means to start (invoke or execute) a process.
 * <p/>
 * This method should return only after the process completes (synchronously). The {@link ProcessStart} method
 * is the asynchronous (non-blocking) version of this method.
 * <p/>
 * The ProcessComplete method is called after the {@link ProcessPrepare} method.
 *
 * @see ProcessPrepare
 * @see ProcessStart
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProcessComplete
  {
  }
