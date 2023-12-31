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
package com.cloud.api.query.dao;

import com.cloud.api.query.vo.UserVmJoinVO;
import org.apache.cloudstack.api.response.UserVmResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserVmJoinDaoImplTest extends GenericDaoBaseWithTagInformationBaseTest<UserVmJoinVO, UserVmResponse> {

    @InjectMocks
    private UserVmJoinDaoImpl _userVmJoinDaoImpl;

    private UserVmJoinVO userVm = new UserVmJoinVO();
    private UserVmResponse userVmResponse = new UserVmResponse();

    @Before
    public void setup() {
        prepareSetup();
    }

    @Override
    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testUpdateUserVmTagInfo(){
        testUpdateTagInformation(_userVmJoinDaoImpl, userVm, userVmResponse);
    }

}
