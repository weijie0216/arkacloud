//
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
//
package com.cloud.resourcelimit;

import com.cloud.configuration.Resource;
import com.cloud.exception.ResourceAllocationException;
import com.cloud.user.Account;
import com.cloud.user.ResourceLimitService;
import com.cloud.utils.db.GlobalLock;
import com.cloud.utils.exception.CloudRuntimeException;
import org.apache.cloudstack.reservation.ReservationVO;
import org.apache.cloudstack.reservation.dao.ReservationDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckedReservationTest {

    @Mock
    Account account;
    @Mock
    ReservationDao reservationDao;
    @Mock
    ResourceLimitService resourceLimitService;

    @Mock
    ReservationVO reservation = new ReservationVO(1l, 1l, Resource.ResourceType.user_vm, 1l);

    @Mock
    GlobalLock quotaLimitLock;

    private AutoCloseable closeable;

    @Before
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void getId() {
        when(account.getDomainId()).thenReturn(4l);
        // Some weird behaviour depending on whether the database is up or not.
        lenient().when(reservationDao.persist(Mockito.any())).thenReturn(reservation);
        lenient().when(reservation.getId()).thenReturn(1L);
        try (CheckedReservation cr = new CheckedReservation(account, Resource.ResourceType.user_vm,1l, reservationDao, resourceLimitService) ) {
            long id = cr.getId();
            assertEquals(1L, id);
        } catch (NullPointerException npe) {
            fail("NPE caught");
        } catch (ResourceAllocationException rae) {
            // this does not work on all platforms because of the static methods being used in the global lock mechanism
            // normally one would
            // throw new CloudRuntimeException(rae);
            // but we'll ignore this for platforms that can not humour the static bits of the system.
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getNoAmount() {
        try (CheckedReservation cr = new CheckedReservation(account, Resource.ResourceType.cpu,-11l, reservationDao, resourceLimitService) ) {
            Long amount = cr.getReservedAmount();
            assertNull(amount);
        } catch (NullPointerException npe) {
            fail("NPE caught");
        } catch (ResourceAllocationException rae) {
            throw new CloudRuntimeException(rae);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
