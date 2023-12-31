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
package com.cloud.utils.validation;

import com.cloud.utils.script.Script;
import org.apache.cloudstack.utils.security.DigestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import java.io.File;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class ChecksumUtilTest {

    @Test
    public void invalidFileForCheckSumValidationTest() {
        MockedStatic<Script> scriptMocked = Mockito.mockStatic(Script.class);
        Mockito.when(Script.findScript(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        try {
            ChecksumUtil.calculateCurrentChecksum(Mockito.anyString(), Mockito.anyString());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Unable to find cloudScripts path, cannot update SystemVM"));
        }
        scriptMocked.close();
    }

    @Test
    public void generateChecksumTest() {
        MockedStatic<Script> scriptMocked = Mockito.mockStatic(Script.class);
        MockedStatic<DigestHelper> digestHelperMocked = Mockito.mockStatic(DigestHelper.class);
        Mockito.when(Script.findScript(Mockito.anyString(), Mockito.anyString())).thenReturn("/dummyPath");
        Mockito.when(DigestHelper.calculateChecksum(Mockito.any(File.class))).thenReturn("dummy-checksum");
        try {
            ChecksumUtil.calculateCurrentChecksum(Mockito.anyString(), Mockito.anyString());
        } catch (Exception e) {
            fail("Failed to generate checksum");
        }
        scriptMocked.close();
        digestHelperMocked.close();
    }
}
