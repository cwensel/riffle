/*
 * Copyright (c) 2010 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.concurrentinc.com/
 */

package perpetual.process;

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

  @ResourceOutgoing
  public String getOutgoing()
    {
    return outgoing;
    }

  @ResourceIncoming
  public String getIncoming()
    {
    return incoming;
    }
  }