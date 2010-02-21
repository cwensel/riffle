/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package perpetual.process;

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
      processes[ i ] = new TestableProcess( i, Integer.toString( i ), Integer.toString( i + 1 ), 1000 );

    ProcessChain processChain = new ProcessChain( processes );

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
