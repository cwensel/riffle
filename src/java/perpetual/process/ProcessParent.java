/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package perpetual.process;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 */
public class ProcessParent
  {
  Object process;

  public ProcessParent( Object process )
    {
    this.process = process;

    Process annotation = process.getClass().getAnnotation( Process.class );

    if( annotation == null )
      throw new IllegalArgumentException( "given process instance must declare the Process annotation" );
    }

  public Object getSink() throws ProcessException
    {
    return findInvoke( SinkResource.class );
    }

  public Object getSource() throws ProcessException
    {
    return findInvoke( SourceResource.class );
    }

  public void prepare() throws ProcessException
    {
    findInvoke( ProcessPrepare.class );
    }

  public void cleanup() throws ProcessException
    {
    findInvoke( ProcessCleanup.class );
    }

  public void start() throws ProcessException
    {
    findInvoke( ProcessStart.class );
    }

  public void complete() throws ProcessException
    {
    findInvoke( ProcessComplete.class );
    }

  public void stop() throws ProcessException
    {
    findInvoke( ProcessStop.class );
    }

  private Object findInvoke( Class<? extends Annotation> type ) throws ProcessException
    {
    Method method = null;
    try
      {
      method = findMethodWith( type );

      return invokeMethod( method );
      }
    catch( InvocationTargetException exception )
      {
      throw new ProcessException( type, method, exception );
      }
    }

  private Object invokeMethod( Method method ) throws InvocationTargetException
    {
    try
      {
      return method.invoke( process );
      }
    catch( IllegalAccessException exception )
      {
      throw new IllegalStateException( "unable to invoke method: " + method.getName(), exception );
      }
    catch( InvocationTargetException exception )
      {
      if( exception.getCause() instanceof Error )
        throw (Error) exception.getCause();

      if( exception.getCause() instanceof RuntimeException )
        throw (RuntimeException) exception.getCause();

      throw exception;
      }
    }

  private Method findMethodWith( Class<? extends Annotation> type )
    {
    Method[] methods = process.getClass().getMethods();

    for( Method method : methods )
      {
      if( method.getAnnotation( type ) == null )
        continue;

      int modifiers = method.getModifiers();

      if( Modifier.isAbstract( modifiers ) )
        throw new IllegalStateException( "given process method: " + method.getName() + " must not be abstract" );

      if( Modifier.isInterface( modifiers ) )
        throw new IllegalStateException( "given process method: " + method.getName() + " must be implemented" );

      if( !Modifier.isPublic( modifiers ) )
        throw new IllegalStateException( "given process method: " + method.getName() + " must be public" );

      return method;
      }

    throw new IllegalStateException( "no method found declaring annotation: " + type.getName() );
    }

  }
