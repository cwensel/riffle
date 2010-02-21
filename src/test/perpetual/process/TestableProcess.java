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
public class TestableProcess implements Comparable<TestableProcess>
  {
  public static int lastId = -1;

  final int id;
  String incoming;
  String outgoing;
  long delay = 1000;

  boolean prepareCalled = false;
  boolean cleanupCalled = false;
  boolean startCalled = false;
  boolean completeCalled = false;
  boolean stopCalled = false;
  boolean finished = false;

  public TestableProcess( int id, String incoming, String outgoing, long delay )
    {
    this.id = id;
    this.incoming = incoming;
    this.outgoing = outgoing;
    this.delay = delay;
    }

  @ResourceOutgoing
  public String getOutgoing()
    {
    return outgoing;
    }

  @ResourceIncoming
  public String getIncoming()
    {
    return incoming;
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
