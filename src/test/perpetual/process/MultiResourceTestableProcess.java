/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package perpetual.process;

import java.util.Collection;

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

  @ResourceOutgoing
  public Collection<String> getOutgoing()
    {
    return outgoing;
    }

  @ResourceIncoming
  public Collection<String> getIncoming()
    {
    return incoming;
    }
  }
