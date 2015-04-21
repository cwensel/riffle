/*
 * Copyright (c) 2007-2015 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.cascading.org/
 *
 * This file is part of the Cascading project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * <p/>
 * This annotation is not optional.
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
