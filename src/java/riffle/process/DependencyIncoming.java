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
 * Annotation DependencyIncoming identifies a class method as implementing a 'getter' that returns an {link Object}
 * or {@link java.util.Collection<Object>} that represents the dependencies that must be satisfied by upstream processes
 * before the current process can be executed.
 * <p/>
 * If no other process under consideration for the workflow declares it satisfies the dependency, it will be assumed
 * the dependency has been satisfied externally (the file already exists in the system, etc).
 *
 * @see DependencyOutgoing
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DependencyIncoming
  {
  }
