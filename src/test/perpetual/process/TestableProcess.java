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
public class TestableProcess
  {
  final int id;
  String source;
  String sink;
  long delay = 1000;

  boolean prepareCalled = false;
  boolean cleanupCalled = false;
  boolean startCalled = false;
  boolean completeCalled = false;
  boolean stopCalled = false;
  boolean finished = false;

  public TestableProcess( int id, String source, String sink, long delay )
    {
    this.id = id;
    this.source = source;
    this.sink = sink;
    this.delay = delay;
    }

  @SinkResource
  public String getSink()
    {
    return sink;
    }

  @SourceResource
  public String getSource()
    {
    return source;
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
  }
