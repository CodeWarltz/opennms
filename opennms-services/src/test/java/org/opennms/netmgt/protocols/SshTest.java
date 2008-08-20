/*
 * This file is part of the OpenNMS(R) Application.
 *
 * OpenNMS(R) is Copyright (C) 2007 The OpenNMS Group, Inc.  All rights reserved.
 * OpenNMS(R) is a derivative work, containing both original code, included code and modified
 * code that was published under the GNU General Public License. Copyrights for modified
 * and included code are below.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * Modifications:
 * 
 * Created: October 2, 2007
 *
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * For more information contact:
 *      OpenNMS Licensing       <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 */
package org.opennms.netmgt.protocols;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.opennms.netmgt.model.PollStatus;
import org.opennms.netmgt.poller.monitors.TimeoutTracker;
import org.opennms.netmgt.protocols.ssh.Ssh;
import org.opennms.netmgt.protocols.ssh.Sshv1;
import org.opennms.netmgt.protocols.ssh.Sshv2;

import junit.framework.TestCase;

/**
 * 
 * @author <a href="mailto:brozow@opennms.org">Mathew Brozowski</a>
 * @author <a href="mailto:ranger@opennms.org">Ben Reed</a>
 */
public class SshTest extends TestCase {
    private static final String GOOD_HOST = "www.opennms.org";
    private static final String BAD_HOST = "1.1.1.1";
    private static final int PORT = 22;
    private static final int TIMEOUT = 2000;
    private TimeoutTracker tt;
    Ssh v1;
    Ssh v2;
    InetAddress good, bad;
    
    public void setUp() throws Exception {
        Map<String, String> parameters = new HashMap<String,String>();
        parameters.put("retries", "0");
        parameters.put("port", "22");
        parameters.put("timeout", Integer.toString(TIMEOUT));
        
        tt = new TimeoutTracker(parameters, 0, TIMEOUT);
        v1 = new Sshv1();
        v2 = new Sshv2();
        v1.setPort(PORT);
        v1.setTimeout(TIMEOUT);
        v2.setPort(PORT);
        v2.setTimeout(TIMEOUT);

        try {
            good = InetAddress.getByName(GOOD_HOST);
            bad  = InetAddress.getByName(BAD_HOST);
        } catch (UnknownHostException e) {
            throw e;
        }
    }
    
    public void testSshGoodV2() throws Exception {
        v2.setAddress(good);
        assertTrue(v2.poll(tt).isAvailable());
    }
    
    public void testSshBadV1() throws Exception {
        Date start = new Date();
        v1.setAddress(bad);
        assertFalse(v1.poll(tt).isAvailable());
        Date end = new Date();

        // give it 2.5 seconds to time out
        assertTrue(end.getTime() - start.getTime() < 2500);
    }
    
    public void testSshBadV2() throws Exception {
        Date start = new Date();
        v2.setAddress(bad);
        assertFalse(v2.poll(tt).isAvailable());
        Date end = new Date();

        // give it 2.5 seconds to time out
        assertTrue(end.getTime() - start.getTime() < 2500);
    }
    
    public void testSshWrongVersion() throws Exception {
        v1.setAddress(good);
        PollStatus result = v1.poll(tt);
        assertFalse(result.isAvailable());
        assertTrue(result.getReason().contains("does not support version"));
    }
}
