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