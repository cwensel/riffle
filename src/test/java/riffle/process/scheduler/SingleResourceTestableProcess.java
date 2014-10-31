/*
 * Copyright (c) 2007-2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package riffle.process.scheduler;

import riffle.process.DependencyIncoming;
import riffle.process.DependencyOutgoing;

/**
 *
 */
public class SingleResourceTestableProcess extends TestableProcess
  {
  String incoming;
  String outgoing;

  public SingleResourceTestableProcess( int id, String incoming, String outgoing, long delay )
    {
    super( id, delay );
    this.incoming = incoming;
    this.outgoing = outgoing;
    }

  @DependencyOutgoing
  public String getOutgoing()
    {
    return outgoing;
    }

  @DependencyIncoming
  public String getIncoming()
    {
    return incoming;
    }
  }