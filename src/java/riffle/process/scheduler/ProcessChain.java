/*
 * Copyright (c) 2007-2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package riffle.process.scheduler;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Class ProcessChain accepts an array of Object instances that declare the {@link riffle.process.Process} class annotation and
 * related method annotations and invoke each instance.
 * <p/>
 * This class will optionally order all process instances by dependencies, and then invoke them sequentially.
 *
 * @see riffle.process.Process
 * @see riffle.process.ProcessPrepare
 * @see riffle.process.ProcessStart
 * @see riffle.process.ProcessStop
 * @see riffle.process.ProcessComplete
 * @see riffle.process.ProcessCleanup
 * @see riffle.process.DependencyOutgoing
 * @see riffle.process.DependencyIncoming
 */
public class ProcessChain
  {
  /** Field processes */
  private ProcessWrapper[] processes;
  /** Field thread */
  private Thread thread;
  /** Field processRunner */
  private ProcessRunner processRunner;

  private class ProcessRunner implements Runnable
    {
    private boolean stop;
    private int runningIndex = 0;

    @Override
    public void run()
      {
      try
        {
        for( int i = 0; i < processes.length; i++ )
          processes[ i ].prepare();

        for( runningIndex = 0; runningIndex < processes.length; runningIndex++ )
          {
          if( !stop )
            processes[ runningIndex ].complete();
          }
        }
      catch( ProcessException exception )
        {
        // ignore
        }
      finally
        {
        for( int i = 0; i < processes.length; i++ )
          {
          try
            {
            processes[ i ].cleanup();
            }
          catch( ProcessException exception )
            {
            // ignore
            }
          }
        }
      }

    public synchronized void stop()
      {
      if( stop )
        return;

      stop = true;

      for( int i = runningIndex; i < processes.length; i++ )
        {
        try
          {
          processes[ i ].stop();
          }
        catch( ProcessException exception )
          {
          // ignore
          }
        }
      }
    }

  public ProcessChain( boolean topologicallyOrder, Object... objects )
    {
    processes = new ProcessWrapper[objects.length];

    for( int i = 0; i < objects.length; i++ )
      processes[ i ] = new ProcessWrapper( objects[ i ] );

    if( topologicallyOrder )
      {
      try
        {
        processes = topologicallyOrder( processes );
        }
      catch( ProcessException exception )
        {
        throw new IllegalStateException( "unable to sort processes", exception.getCause() );
        }
      }
    }

  public void start()
    {
    processRunner = new ProcessRunner();
    thread = new Thread( processRunner );
    thread.start();
    }

  public void complete()
    {
    try
      {
      thread.join();
      }
    catch( InterruptedException exception )
      {
      // ignore
      }
    }

  public void stop()
    {
    if( processRunner != null )
      processRunner.stop();
    }

  private static ProcessWrapper[] topologicallyOrder( ProcessWrapper[] processes ) throws ProcessException
    {
    while( !isTopologicallyOrdered( processes ) )
      {
      List<ProcessWrapper> sorted = new LinkedList<ProcessWrapper>();

      sorted.add( processes[ 0 ] );

      for( int i = 1; i < processes.length; i++ )
        {
        ProcessWrapper process = processes[ i ];

        ListIterator<ProcessWrapper> iterator = sorted.listIterator();
        boolean inserted = false;

        while( iterator.hasNext() )
          {
          ProcessWrapper current = iterator.next();

          if( equalsOrContains( process.getDependencyIncoming(), current.getDependencyIncoming() ) )
            {
            iterator.add( process );
            inserted = true;
            break;
            }

          if( equalsOrContains( process.getDependencyIncoming(), current.getDependencyOutgoing() ) )
            {
            iterator.add( process );
            inserted = true;
            break;
            }

          if( equalsOrContains( current.getDependencyIncoming(), process.getDependencyOutgoing() ) )
            {
            iterator.remove();
            iterator.add( process );
            iterator.add( current );
            inserted = true;
            break;
            }
          }

        if( !inserted )
          sorted.add( 0, process );
        }

      processes = sorted.toArray( new ProcessWrapper[sorted.size()] );
      }

    return processes;
    }

  private static boolean equalsOrContains( Object lhs, Object rhs )
    {
    if( !( lhs instanceof Collection ) && !( rhs instanceof Collection ) )
      return lhs.equals( rhs );

    if( lhs instanceof Collection && !( rhs instanceof Collection ) )
      return ( (Collection) lhs ).contains( rhs );

    if( !( lhs instanceof Collection ) && rhs instanceof Collection )
      return ( (Collection) rhs ).contains( lhs );

    return !Collections.disjoint( (Collection) lhs, (Collection) rhs );
    }

  private static boolean isTopologicallyOrdered( ProcessWrapper[] processes ) throws ProcessException
    {
    for( int i = 0; i < processes.length; i++ )
      {
      ProcessWrapper lhs = processes[ i ];

      for( int j = i; j < processes.length; j++ )
        {
        ProcessWrapper rhs = processes[ j ];

        if( equalsOrContains( lhs.getDependencyIncoming(), rhs.getDependencyOutgoing() ) )
          return false;
        }
      }

    return true;
    }
  }
