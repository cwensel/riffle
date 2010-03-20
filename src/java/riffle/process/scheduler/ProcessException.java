/*
 * Copyright (c) 2007-2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package riffle.process.scheduler;

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
