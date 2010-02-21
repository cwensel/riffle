/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package perpetual.process;

/**
 *
 */
public class ProcessChain
  {
  private ProcessParent[] processes;
  private Thread thread = null;
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

  public ProcessChain( Object... objects )
    {
    processes = new ProcessParent[objects.length];

    for( int i = 0; i < objects.length; i++ )
      processes[ i ] = new ProcessParent( objects[ i ] );
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
  }
