/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package perpetual.process;

/**
 *
 */
@Process
public abstract class TestableProcess implements Comparable<TestableProcess>
  {
  public static int lastId = -1;

  final int id;
  long delay = 1000;

  boolean prepareCalled = false;
  boolean cleanupCalled = false;
  boolean startCalled = false;
  boolean completeCalled = false;
  boolean stopCalled = false;
  boolean finished = false;

  public TestableProcess( int id, long delay )
    {
    this.id = id;
    this.delay = delay;
    }

  @ProcessPrepare
  public void prepare()
    {
    prepareCalled = true;
    }

  @ProcessCleanup
  public void cleanup()
    {
    cleanupCalled = true;
    }

  @ProcessStart
  public void start()
    {
    startCalled = true;

    try
      {
      Thread.sleep( delay );

      if( lastId >= id )
        throw new IllegalStateException( "processes executed out of order, id:" + id + ", lastId: " + lastId );

      lastId = id;

      finished = true;
      }
    catch( InterruptedException exception )
      {
      // ignore
      }
    }

  @ProcessComplete
  public void complete()
    {
    start();
    completeCalled = true;
    }

  @ProcessStop
  public void stop()
    {
    stopCalled = true;
    }

  @Override
  public String toString()
    {
    return "TestableProcess{" + "id=" + id + '}';
    }

  @Override
  public int compareTo( TestableProcess testableProcess )
    {
    return id - testableProcess.id;
    }
  }
