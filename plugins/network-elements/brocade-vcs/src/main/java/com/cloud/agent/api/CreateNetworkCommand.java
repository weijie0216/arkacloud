// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.cloud.agent.api;

public class CreateNetworkCommand extends Command {

    private int _vlanId;
    private long _networkId;
    private String _ownerName;

    public CreateNetworkCommand(int vlanId, long networkId, String ownerName) {
        _vlanId = vlanId;
        _networkId = networkId;
        _ownerName = ownerName;
    }

    @Override
    public boolean executeInSequence() {
        return false;
    }

    public int getVlanId() {
        return _vlanId;
    }

    public long getNetworkId() {
        return _networkId;
    }

    public String getOwnerName() {
        return _ownerName;
    }

}
