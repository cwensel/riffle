/*
 * Copyright (c) 2007-2015 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.cascading.org/
 *
 * This file is part of the Cascading project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package riffle.process.scheduler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import riffle.process.ProcessChildren;
import riffle.process.ProcessCleanup;
import riffle.process.ProcessComplete;
import riffle.process.ProcessConfiguration;
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

  @ProcessConfiguration
  public Object getConfiguration()
    {
    return new Properties();
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
