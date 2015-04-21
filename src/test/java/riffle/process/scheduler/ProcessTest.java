/*
 * Copyright (c) 2007-2014 Concurrent, Inc. All Rights Reserved.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

/**
 *
 */
public class ProcessTest extends TestCase
  {
  public ProcessTest()
    {
    super( "process tests" );
    }

  public void testProcessController() throws Exception
    {
    TestableProcess[] processes = new TestableProcess[ 10 ];

    for( int i = 0; i < processes.length; i++ )
      processes[ i ] = new SingleResourceTestableProcess( i, Integer.toString( i ), Integer.toString( i + 1 ), 500 );

    runTest( false, processes );
    }

  public void testProcessControllerMulti() throws Exception
    {
    TestableProcess[] processes = new TestableProcess[ 10 ];

    for( int i = 0; i < processes.length; i++ )
      {
      List<String> sources = new ArrayList<String>();
      sources.add( Integer.toString( i ) );
      sources.add( Integer.toString( i * 100 ) );

      List<String> sinks = new ArrayList<String>();
      sinks.add( Integer.toString( i + 1 ) );

      processes[ i ] = new MultiResourceTestableProcess( i, sources, sinks, 500 );
      }

    runTest( false, processes );
    }

  public void testProcessControllerSorting() throws Exception
    {
    TestableProcess[] processes = new TestableProcess[ 10 ];

    for( int i = 0; i < processes.length; i++ )
      processes[ i ] = new SingleResourceTestableProcess( i, Integer.toString( i ), Integer.toString( i + 1 ), 500 );

    Arrays.sort( processes, Collections.<Object>reverseOrder() );

    TestableProcess temp = processes[ 3 ];
    processes[ 3 ] = processes[ 7 ];
    processes[ 7 ] = temp;

    runTest( true, processes );
    }

  public void testProcessControllerSortingMulti() throws Exception
    {
    TestableProcess[] processes = new TestableProcess[ 10 ];

    for( int i = 0; i < processes.length; i++ )
      {
      List<String> sources = new ArrayList<String>();
      sources.add( Integer.toString( i ) );
      sources.add( Integer.toString( i * 100 ) );

      List<String> sinks = new ArrayList<String>();
      sinks.add( Integer.toString( i + 1 ) );

      processes[ i ] = new MultiResourceTestableProcess( i, sources, sinks, 500 );
      }

    Arrays.sort( processes, Collections.<Object>reverseOrder() );

    TestableProcess temp = processes[ 3 ];
    processes[ 3 ] = processes[ 7 ];
    processes[ 7 ] = temp;

    runTest( true, processes );
    }

  public void testCounters() throws Exception
    {
    SingleResourceTestableProcess testableProcess = new SingleResourceTestableProcess( 1, "in", "out", 0L );

    ProcessWrapper processWrapper = new ProcessWrapper( testableProcess );
    assertTrue( processWrapper.hasCounters() );
    assertNotNull( processWrapper.getCounters() );
    }

  public void testConfiguration() throws Exception
    {
    SingleResourceTestableProcess testableProcess = new SingleResourceTestableProcess( 1, "in", "out", 0L );

    ProcessWrapper processWrapper = new ProcessWrapper( testableProcess );

    assertNotNull( processWrapper.getConfiguration() );
    }

  public void testChildren() throws Exception
    {
    SingleResourceTestableProcess testableProcess = new SingleResourceTestableProcess( 1, "in", "out", 0L );

    ProcessWrapper processWrapper = new ProcessWrapper( testableProcess );
    assertTrue( processWrapper.hasChildren() );
    assertNotNull( processWrapper.getChildren() );
    assertEquals( 1, processWrapper.getChildren().size() );
    }

  private void runTest( boolean sort, TestableProcess[] processes )
    {
    TestableProcess.lastId = -1;

    ProcessChain processChain = new ProcessChain( sort, (Object[]) processes );

    processChain.start();
    processChain.complete();

    for( TestableProcess process : processes )
      {
      assertTrue( "prepare", process.prepareCalled );
      assertTrue( "start", process.startCalled );
      assertTrue( "complete", process.completeCalled );
      assertTrue( "cleanup", process.cleanupCalled );
      assertTrue( "finished", process.finished );
      }
    }
  }
