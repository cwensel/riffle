/*
 * Copyright (c) 2007-2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package riffle.process.scheduler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import riffle.process.ProcessChildren;
import riffle.process.ProcessCleanup;
import riffle.process.ProcessComplete;
import riffle.process.ProcessCounters;
import riffle.process.ProcessPrepare;
import riffle.process.ProcessStart;
import riffle.process.ProcessStop;

/**
 *
 */
@riffle.process.Process
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

  @ProcessCounters
  public Map<String, Map<String, Long>> getCounters()
    {
    return Collections.emptyMap();
    }

  @ProcessChildren
  public List<Object> getChildren() throws Exception
    {
    return Arrays.<Object>asList( new SingleResourceTestableProcess( 1, "input", "output", 0L ) );
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
