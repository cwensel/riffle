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

package perpetual.process.scheduler;

import perpetual.process.ProcessCleanup;
import perpetual.process.ProcessComplete;
import perpetual.process.ProcessPrepare;
import perpetual.process.ProcessStart;
import perpetual.process.ProcessStop;

/**
 *
 */
@perpetual.process.Process
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
