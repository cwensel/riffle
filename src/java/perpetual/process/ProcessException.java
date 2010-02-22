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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 *
 */
public class ProcessException extends Exception
  {
  String annotationName;
  String methodName;

  public ProcessException( Class<? extends Annotation> type, Method method, Throwable cause )
    {
    super( cause );
    annotationName = type.getName();
    methodName = method.getName();
    }

  public ProcessException( String string )
    {
    super( string );
    }

  public ProcessException( String string, Throwable throwable )
    {
    super( string, throwable );
    }

  public ProcessException( Throwable throwable )
    {
    super( throwable );
    }

  public String getAnnotationName()
    {
    return annotationName;
    }

  public String getMethodName()
    {
    return methodName;
    }
  }
