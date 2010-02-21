/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package perpetual.process;

import java.util.Arrays;
import java.util.Collections;

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
    TestableProcess[] processes = new TestableProcess[10];

    for( int i = 0; i < processes.length; i++ )
      processes[ i ] = new TestableProcess( i, Integer.toString( i ), Integer.toString( i + 1 ), 500 );

    runTest( false, processes );
    }

  public void testProcessControllerSorting() throws Exception
    {
    TestableProcess[] processes = new TestableProcess[10];

    for( int i = 0; i < processes.length; i++ )
      processes[ i ] = new TestableProcess( i, Integer.toString( i ), Integer.toString( i + 1 ), 500 );

    Arrays.sort( processes, Collections.<Object>reverseOrder() );

    TestableProcess temp = processes[ 3 ];
    processes[ 3 ] = processes[ 7 ];
    processes[ 7 ] = temp;

    runTest( true, processes );
    }

  private void runTest( boolean sort, TestableProcess[] processes )
    {
    TestableProcess.lastId = -1;

    ProcessChain processChain = new ProcessChain( sort, processes );

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
