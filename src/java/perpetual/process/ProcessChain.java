/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package perpetual.process;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Class ProcessChain accepts an array of Object instances that declare the {@link Process} class annotation and
 * related method annotations and invoke each instance.
 * <p/>
 * This class will optionally order all process instances by dependencies, and then invoke them sequentially.
 *
 * @see Process
 * @see ProcessPrepare
 * @see ProcessStart
 * @see ProcessStop
 * @see ProcessComplete
 * @see ProcessCleanup
 * @see ResourceOutgoing
 * @see ResourceIncoming
 */
public class ProcessChain
  {
  /** Field processes */
  private ProcessParent[] processes;
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
    processes = new ProcessParent[objects.length];

    for( int i = 0; i < objects.length; i++ )
      processes[ i ] = new ProcessParent( objects[ i ] );

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

  private static ProcessParent[] topologicallyOrder( ProcessParent[] processes ) throws ProcessException
    {
    while( !isTopologicallyOrdered( processes ) )
      {
      List<ProcessParent> sorted = new LinkedList<ProcessParent>();

      sorted.add( processes[ 0 ] );

      for( int i = 1; i < processes.length; i++ )
        {
        ProcessParent process = processes[ i ];

        ListIterator<ProcessParent> iterator = sorted.listIterator();
        boolean inserted = false;

        while( iterator.hasNext() )
          {
          ProcessParent current = iterator.next();

          if( process.getSource().equals( current.getSource() ) )
            {
            iterator.add( process );
            inserted = true;
            break;
            }

          if( process.getSource().equals( current.getSink() ) )
            {
            iterator.add( process );
            inserted = true;
            break;
            }

          if( current.getSource().equals( process.getSink() ) )
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

      processes = sorted.toArray( new ProcessParent[sorted.size()] );
      }

    return processes;
    }

  private static boolean isTopologicallyOrdered( ProcessParent[] processes ) throws ProcessException
    {
    for( int i = 0; i < processes.length; i++ )
      {
      ProcessParent lhs = processes[ i ];

      for( int j = i; j < processes.length; j++ )
        {
        ProcessParent rhs = processes[ j ];

        if( lhs.getSource().equals( rhs.getSink() ) )
          return false;
        }
      }

    return true;
    }
  }
