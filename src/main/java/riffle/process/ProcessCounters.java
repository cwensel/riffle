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
 * Annotation ProcessCounters identifies a class method as implementing a means to retrieve metrics or counters
 * for a {@link riffle.process.Process} instance.
 * <p/>
 * The method should return a Map<String, Map<String,Long>>.
 * <p/>
 * If a process has children the counters on the current Process instance should be a roll up of all the
 * immediate child counters.
 * <p/>
 * Individual child counters can be exposed by child processes via a {@link riffle.process.ProcessChildren} method.
 * <p/>
 * This annotation is optional.
 *
 * @see riffle.process.ProcessChildren
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface ProcessCounters
  {
  }
