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
 * Annotation DependencyOutgoing identifies a class method as implementing a 'getter' that returns an {link Object}
 * or {@link java.util.Collection<Object>} that represents the dependencies that will be satisfied by this process
 * when it is executed.
 *
 * @see DependencyIncoming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DependencyOutgoing
  {
  }
