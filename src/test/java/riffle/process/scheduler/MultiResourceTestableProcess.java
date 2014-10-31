/*
 * Copyright (c) 2007-2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package riffle.process.scheduler;

import java.util.Collection;

import riffle.process.DependencyIncoming;
import riffle.process.DependencyOutgoing;

/**
 *
 */
public class MultiResourceTestableProcess extends TestableProcess
  {
  Collection<String> incoming;
  Collection<String> outgoing;

  public MultiResourceTestableProcess( int id, Collection<String> incoming, Collection<String> outgoing, long delay )
    {
    super( id, delay );
    this.incoming = incoming;
    this.outgoing = outgoing;
    }

  @DependencyOutgoing
  public Collection<String> getOutgoing()
    {
    return outgoing;
    }

  @DependencyIncoming
  public Collection<String> getIncoming()
    {
    return incoming;
    }
  }
