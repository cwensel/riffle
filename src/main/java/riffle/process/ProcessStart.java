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
 * Annotation ProcessStart identifies a class method as implementing a means to start (invoke or execute) a process.
 * <p/>
 * This method should return immediately (asynchronously). The {@link ProcessComplete} method is the synchronous
 * (blocking) version of this method.
 * <p/>
 * The ProcessStart method is called after the {@link ProcessPrepare} method. It may be called by the
 * {@link ProcessComplete} method (but will block till the process is finished).
 * <p/>
 * This annotation is optional. Calling methods should test for its existence with the
 * {@link riffle.process.scheduler.ProcessWrapper#hasStart} method and should call the corresponding
 * ProcessComplete annotated method.
 *
 * @see ProcessPrepare
 * @see ProcessComplete
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProcessStart
  {
  }
