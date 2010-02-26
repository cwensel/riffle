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
 * Annotation ProcessStart identifies a class method as implementing a means to start (invoke or execute) a process.
 * <p/>
 * This method should return immediately (asynchronously). The {@link ProcessComplete} method is the synchronous
 * (blocking) version of this method.
 * <p/>
 * The ProcessStart method is called after the {@link ProcessPrepare} method. It may be called by the
 * {@link ProcessComplete} method (but will block till the process is finished).
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
