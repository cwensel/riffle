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
 * Annotation ProcessStop identifies a class method as implementing a means to stop (kill) a process after it
 * has been started (and typically before it completes).
 * <p/>
 * It is possible ProcessStop is called before {@link ProcessStart} or {@link ProcessComplete} are called, depending
 * on the scheduler implementation (allows for concurrent execution of independent processes).
 *
 * @see ProcessStart
 * @see ProcessComplete
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProcessStop
  {
  }
