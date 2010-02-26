/*
 * Copyright 2010 Concurrent, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package perpetual.process;

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
 *
 * @see ProcessPrepare
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProcessCleanup
  {
  }
