/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2010-2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.linkd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.opennms.core.test.snmp.annotations.JUnitSnmpAgent;
import org.opennms.core.test.snmp.annotations.JUnitSnmpAgents;
import org.opennms.netmgt.config.linkd.Package;
import org.opennms.netmgt.model.DataLinkInterface;
import org.opennms.netmgt.model.DataLinkInterface.DiscoveryProtocol;
import org.opennms.netmgt.model.OnmsNode;
import org.springframework.transaction.annotation.Transactional;

public class Nms101Test extends Nms101NetworkBuilder {
	
    @Before
    public void setUpForceDisvoeryOnEthernet() {
    for (Package pkg : Collections.list(m_linkdConfig.enumeratePackage())) {
            pkg.setForceIpRouteDiscoveryOnEthernet(true);
        }
    }

    @Test
    @Transactional
    public void testDefaultConfiguration() throws Exception {
    	m_nodeDao.save(getExampleCom());
    	m_nodeDao.save(getLaptop());
    	m_nodeDao.save(getCisco7200a());
    	m_nodeDao.save(getCisco7200b());
    	m_nodeDao.save(getCisco3700());
    	m_nodeDao.save(getCisco2691());
    	m_nodeDao.save(getCisco1700());
    	m_nodeDao.save(getCisco3600());
    	m_nodeDao.flush();

        assertEquals(true,m_linkdConfig.useBridgeDiscovery());
        assertEquals(true,m_linkdConfig.useOspfDiscovery());
        assertEquals(true,m_linkdConfig.useIpRouteDiscovery());
        assertEquals(true,m_linkdConfig.useLldpDiscovery());
        assertEquals(true,m_linkdConfig.useCdpDiscovery());
        assertEquals(true,m_linkdConfig.useIsIsDiscovery());
        
        assertEquals(true,m_linkdConfig.saveRouteTable());
        assertEquals(true,m_linkdConfig.saveStpNodeTable());
        assertEquals(true,m_linkdConfig.saveStpInterfaceTable());
        
        assertEquals(true, m_linkdConfig.isVlanDiscoveryEnabled());


        assertEquals(false, m_linkdConfig.isAutoDiscoveryEnabled());
        assertEquals(false, m_linkdConfig.forceIpRouteDiscoveryOnEthernet());

        assertEquals(false, m_linkdConfig.hasClassName(".1.3.6.1.4.1.2636.1.1.1.1.9"));
                
        assertEquals("org.opennms.netmgt.linkd.snmp.ThreeComVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.43.1.9.13.3.1"));
        assertEquals("org.opennms.netmgt.linkd.snmp.ThreeComVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.43.10.27.4.1.2.4"));
        assertEquals("org.opennms.netmgt.linkd.snmp.ThreeComVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.43.10.27.4.1.2.2"));
        assertEquals("org.opennms.netmgt.linkd.snmp.ThreeComVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.43.10.27.4.1.2.11"));
        assertEquals("org.opennms.netmgt.linkd.snmp.ThreeComVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.43.1.16.4.3.5"));
        assertEquals("org.opennms.netmgt.linkd.snmp.ThreeComVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.43.1.16.4.3.6"));

        assertEquals("org.opennms.netmgt.linkd.snmp.Dot1qStaticVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.43.1.8.43"));
        assertEquals("org.opennms.netmgt.linkd.snmp.Dot1qStaticVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.43.1.8.61"));

        assertEquals("org.opennms.netmgt.linkd.snmp.RapidCityVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.45.3.61.1"));
        assertEquals("org.opennms.netmgt.linkd.snmp.RapidCityVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.45.3.35.1"));
        assertEquals("org.opennms.netmgt.linkd.snmp.RapidCityVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.45.3.53.1"));
        
        assertEquals("org.opennms.netmgt.linkd.snmp.IntelVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.343.5.1.5"));

        assertEquals("org.opennms.netmgt.linkd.snmp.Dot1qStaticVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.11.2.3.7.11.1"));
        assertEquals("org.opennms.netmgt.linkd.snmp.Dot1qStaticVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.11.2.3.7.11.3"));
        assertEquals("org.opennms.netmgt.linkd.snmp.Dot1qStaticVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.11.2.3.7.11.7"));
        assertEquals("org.opennms.netmgt.linkd.snmp.Dot1qStaticVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.11.2.3.7.11.8"));
        assertEquals("org.opennms.netmgt.linkd.snmp.Dot1qStaticVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.11.2.3.7.11.11"));
        assertEquals("org.opennms.netmgt.linkd.snmp.Dot1qStaticVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.11.2.3.7.11.6"));
        assertEquals("org.opennms.netmgt.linkd.snmp.Dot1qStaticVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.11.2.3.7.11.50"));

        
        assertEquals("org.opennms.netmgt.linkd.snmp.CiscoVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.9.1.300"));
        assertEquals("org.opennms.netmgt.linkd.snmp.CiscoVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.9.1.122"));
        assertEquals("org.opennms.netmgt.linkd.snmp.CiscoVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.9.1.616"));
        assertEquals("org.opennms.netmgt.linkd.snmp.CiscoVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.9.5.42"));
        assertEquals("org.opennms.netmgt.linkd.snmp.CiscoVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.9.5.59"));

        assertEquals("org.opennms.netmgt.linkd.snmp.ExtremeNetworkVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.1916.2.11"));
        assertEquals("org.opennms.netmgt.linkd.snmp.ExtremeNetworkVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.1916.2.14"));
        assertEquals("org.opennms.netmgt.linkd.snmp.ExtremeNetworkVlanTable", m_linkdConfig.getVlanClassName(".1.3.6.1.4.1.1916.2.63"));

        assertEquals("org.opennms.netmgt.linkd.snmp.IpCidrRouteTable", m_linkdConfig.getDefaultIpRouteClassName());
        assertEquals("org.opennms.netmgt.linkd.snmp.IpRouteTable", m_linkdConfig.getIpRouteClassName(".1.3.6.1.4.1.3224.1.51"));
        assertEquals("org.opennms.netmgt.linkd.snmp.IpRouteTable", m_linkdConfig.getIpRouteClassName(".1.3.6.1.4.1.9.1.569"));
        assertEquals("org.opennms.netmgt.linkd.snmp.IpRouteTable", m_linkdConfig.getIpRouteClassName(".1.3.6.1.4.1.9.5.42"));
        assertEquals("org.opennms.netmgt.linkd.snmp.IpRouteTable", m_linkdConfig.getIpRouteClassName(".1.3.6.1.4.1.8072.3.2.255"));

        final OnmsNode laptop = m_nodeDao.findByForeignId("linkd", "laptop");
        final OnmsNode cisco3600 = m_nodeDao.findByForeignId("linkd", "cisco3600");
        
        assertTrue(m_linkd.scheduleNodeCollection(laptop.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco3600.getId()));

        SnmpCollection snmpCollLaptop = m_linkd.getSnmpCollection(laptop.getId(), laptop.getPrimaryInterface().getIpAddress(), laptop.getSysObjectId(), "example1");
        assertEquals(true, snmpCollLaptop.getCollectBridge());
        assertEquals(true, snmpCollLaptop.getCollectStp());
        assertEquals(true, snmpCollLaptop.getCollectCdp());
        assertEquals(true, snmpCollLaptop.getCollectIpRoute());
        assertEquals(true, snmpCollLaptop.getCollectOspf());
        assertEquals(true, snmpCollLaptop.getCollectLldp());

        assertEquals(false, snmpCollLaptop.collectVlanTable());
        
        assertEquals("org.opennms.netmgt.linkd.snmp.IpRouteTable", snmpCollLaptop.getIpRouteClass());
        assertEquals("example1", snmpCollLaptop.getPackageName());
        assertEquals(true, m_linkd.saveRouteTable("example1"));
        assertEquals(true, m_linkd.saveStpNodeTable("example1"));
        assertEquals(true, m_linkd.saveStpInterfaceTable("example1"));

        SnmpCollection snmpCollcisco3600 = m_linkd.getSnmpCollection(cisco3600.getId(), cisco3600.getPrimaryInterface().getIpAddress(), cisco3600.getSysObjectId(), "example1");

        assertEquals(true, snmpCollcisco3600.getCollectBridge());
        assertEquals(true, snmpCollcisco3600.getCollectStp());
        assertEquals(true, snmpCollcisco3600.getCollectCdp());
        assertEquals(true, snmpCollcisco3600.getCollectIpRoute());
        assertEquals(true, snmpCollcisco3600.getCollectOspf());
        assertEquals(true, snmpCollcisco3600.getCollectLldp());

        assertEquals(true, snmpCollcisco3600.collectVlanTable());
        assertEquals("org.opennms.netmgt.linkd.snmp.CiscoVlanTable", snmpCollcisco3600.getVlanClass());
        
        assertEquals("org.opennms.netmgt.linkd.snmp.IpCidrRouteTable", snmpCollcisco3600.getIpRouteClass());
        assertEquals("example1", snmpCollcisco3600.getPackageName());

        Package example1 = m_linkdConfig.getPackage("example1");
        assertEquals(true, example1.getForceIpRouteDiscoveryOnEthernet());
        
        final Enumeration<Package> pkgs = m_linkdConfig.enumeratePackage();
        example1 = pkgs.nextElement();
        assertEquals("example1", example1.getName());
        assertEquals(false, pkgs.hasMoreElements());
    }
    
    /*
     * cisco1700 --- cisco1700b ??????
     * cisco1700b clearly does not have relation with this net...it has the same address
     * of cisco2691......and the link is between cisco1700 and cisco2691
     * what a fake....very interesting test.....
     * CDP now fails....but iproute discovery found a right route information 
     * no way of fixing this in the actual code implementation
     * 
     */
	@Test
    @JUnitSnmpAgents(value={
        @JUnitSnmpAgent(host="10.1.5.1", port=161, resource="classpath:linkd/nms101/cisco1700b.properties"),
        @JUnitSnmpAgent(host="10.1.5.2", port=161, resource="classpath:linkd/nms101/cisco1700.properties")
    })
    public void testSimpleFakeConnection() throws Exception {
	m_nodeDao.save(getCisco1700());
	m_nodeDao.save(getCisco1700b());
	m_nodeDao.save(getExampleCom());
        m_nodeDao.flush();

        final OnmsNode cisco1700 = m_nodeDao.findByForeignId("linkd", CISCO1700_NAME);
        final OnmsNode cisco1700b = m_nodeDao.findByForeignId("linkd", CISCO1700B_NAME);

        assertTrue(m_linkd.scheduleNodeCollection(cisco1700.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco1700b.getId()));

        assertTrue(m_linkd.runSingleSnmpCollection(cisco1700.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco1700b.getId()));

        for (LinkableNode node: m_linkd.getLinkableNodesOnPackage("example1")) {
        	int nodeid = node.getNodeId();
        	printNode(m_nodeDao.get(nodeid));
        	for (RouterInterface route: node.getRouteInterfaces()) {
        		printRouteInterface(nodeid, route);
        	}
        	
        	for (CdpInterface cdp: node.getCdpInterfaces()) {
        		printCdpInterface(nodeid, cdp);
        	}
        		
        }
        assertTrue(m_linkd.runSingleLinkDiscovery("example1"));
        
        final List<DataLinkInterface> ifaces = m_dataLinkInterfaceDao.findAll();
        for (final DataLinkInterface link: ifaces) {
            printLink(link);
        }
        assertEquals("we should have found 1 data link", 1, ifaces.size());
    }

    /*
     *  Discover the following topology
     *  The CDP protocol must found all the links
     *  Either Ip Route must found links
     * 
     *  laptop
     *     |
     *  cisco7200a (2) --- (4) cisco7200b (1) --- (4) cisco2691 (2) --- (2) cisco1700
     *                     (2)                    (1)    
     *                      |                      |
     *                     (1)                    (2)
     *                  cisco3700  (3) --- (1)  cisco3600      
     */	
    @Test
    @JUnitSnmpAgents(value={
        @JUnitSnmpAgent(host="10.1.1.1", port=161, resource="classpath:linkd/nms101/cisco7200a.properties"),
        @JUnitSnmpAgent(host="10.1.2.2", port=161, resource="classpath:linkd/nms101/cisco7200b.properties")
    })
    public void testsimpleLinkCisco7200aCisco7200b() throws Exception {

    	m_nodeDao.save(getCisco7200a());
    	m_nodeDao.save(getCisco7200b());
    	m_nodeDao.flush();
    	
        final OnmsNode cisco7200a = m_nodeDao.findByForeignId("linkd", CISCO7200A_NAME);
        final OnmsNode cisco7200b = m_nodeDao.findByForeignId("linkd", CISCO7200B_NAME);
        assertTrue(m_linkd.scheduleNodeCollection(cisco7200a.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco7200b.getId()));
 
        assertTrue(m_linkd.runSingleSnmpCollection(cisco7200a.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco7200b.getId()));
 
        assertTrue(m_linkd.runSingleLinkDiscovery("example1"));
        
        final List<DataLinkInterface> ifaces = m_dataLinkInterfaceDao.findAll();
        for (final DataLinkInterface link: ifaces) {
            if (link.getProtocol() == DiscoveryProtocol.iproute)
                checkLink(cisco7200a, cisco7200b, 2, 4, link);
            else if (link.getProtocol() ==  DiscoveryProtocol.cdp)
                checkLink(cisco7200b, cisco7200a, 4, 2, link);
        }

        assertEquals("we should have found 2 data links", 2, ifaces.size());
    }

    /*
     *  Discover the following topology
     *  The CDP protocol must found all the links
     *  Either Ip Route must found links
     * 
     *  laptop
     *     |
     *  cisco7200a (2) --- (4) cisco7200b (1) --- (4) cisco2691 (2) --- (2) cisco1700
     *                     (2)                    (1)    
     *                      |                      |
     *                     (1)                    (2)
     *                  cisco3700  (3) --- (1)  cisco3600      
     */	
    @Test
    @JUnitSnmpAgents(value={
        @JUnitSnmpAgent(host="10.1.1.1", port=161, resource="classpath:linkd/nms101/cisco7200a.properties"),
        @JUnitSnmpAgent(host="10.1.1.2", port=161, resource="classpath:linkd/nms101/laptop.properties")
    })
    public void testsimpleLinkCisco7200alaptop() throws Exception {

    	m_nodeDao.save(getCisco7200a());
    	m_nodeDao.save(getLaptop());
    	m_nodeDao.flush();
    	
        final OnmsNode cisco7200a = m_nodeDao.findByForeignId("linkd", CISCO7200A_NAME);
        final OnmsNode laptop = m_nodeDao.findByForeignId("linkd", LAPTOP_NAME);
        assertTrue(m_linkd.scheduleNodeCollection(cisco7200a.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(laptop.getId()));
 
        assertTrue(m_linkd.runSingleSnmpCollection(cisco7200a.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(laptop.getId()));
 
        assertTrue(m_linkd.runSingleLinkDiscovery("example1"));
        
        final List<DataLinkInterface> ifaces = m_dataLinkInterfaceDao.findAll();
        for (final DataLinkInterface link: ifaces) {
            printLink(link);
        }

        assertEquals("we should have found 1 data links", 1, ifaces.size());
    }

    /*
     *  Discover the following topology
     *  The CDP protocol must found all the links
     *  Either Ip Route must found links
     * 
     *  laptop
     *     |
     *  cisco7200a (2) --- (4) cisco7200b (1) --- (4) cisco2691 (2) --- (2) cisco1700
     *                     (2)                    (1)    
     *                      |                      |
     *                     (1)                    (2)
     *                  cisco3700  (3) --- (1)  cisco3600      
     */	
    @Test
    @JUnitSnmpAgents(value={
            @JUnitSnmpAgent(host="10.1.3.2", port=161, resource="classpath:linkd/nms101/cisco3700.properties"),
            @JUnitSnmpAgent(host="10.1.6.2", port=161, resource="classpath:linkd/nms101/cisco3600.properties")
    })
    public void testsimpleLinkCisco3600aCisco3700() throws Exception {

    	m_nodeDao.save(getCisco3700());
    	m_nodeDao.save(getCisco3600());
    	m_nodeDao.flush();
    	
        final OnmsNode cisco3600 = m_nodeDao.findByForeignId("linkd", CISCO3600_NAME);
        final OnmsNode cisco3700 = m_nodeDao.findByForeignId("linkd", CISCO3700_NAME);
        assertTrue(m_linkd.scheduleNodeCollection(cisco3700.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco3600.getId()));
 
        assertTrue(m_linkd.runSingleSnmpCollection(cisco3700.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco3600.getId()));
 
        assertTrue(m_linkd.runSingleLinkDiscovery("example1"));
        
        final List<DataLinkInterface> ifaces = m_dataLinkInterfaceDao.findAll();
        for (final DataLinkInterface link: ifaces) {
            if (link.getProtocol() == DiscoveryProtocol.iproute)
                checkLink(cisco3600, cisco3700, 1, 3, link);
            else if (link.getProtocol() ==  DiscoveryProtocol.cdp)
                checkLink(cisco3600, cisco3700, 1, 3, link);
        }

        assertEquals("we should have found 2 data links", 2, ifaces.size());
    }


    /*
     *  Discover the following topology
     *  The CDP protocol must found all the links
     *  Either Ip Route must found links
     * 
     *  laptop
     *     |
     *  cisco7200a (2) --- (4) cisco7200b (1) --- (4) cisco2691 (2) --- (2) cisco1700
     *                     (2)                    (1)    
     *                      |                      |
     *                     (1)                    (2)
     *                  cisco3700  (3) --- (1)  cisco3600      
     */	
    @Test
    @JUnitSnmpAgents(value={
        @JUnitSnmpAgent(host="10.1.1.1", port=161, resource="classpath:linkd/nms101/cisco7200a.properties"),
        @JUnitSnmpAgent(host="10.1.1.2", port=161, resource="classpath:linkd/nms101/laptop.properties"),
        @JUnitSnmpAgent(host="10.1.2.2", port=161, resource="classpath:linkd/nms101/cisco7200b.properties"),
        @JUnitSnmpAgent(host="10.1.3.2", port=161, resource="classpath:linkd/nms101/cisco3700.properties"),
        @JUnitSnmpAgent(host="10.1.4.2", port=161, resource="classpath:linkd/nms101/cisco2691.properties"),
        @JUnitSnmpAgent(host="10.1.5.2", port=161, resource="classpath:linkd/nms101/cisco1700.properties"),
        @JUnitSnmpAgent(host="10.1.6.2", port=161, resource="classpath:linkd/nms101/cisco3600.properties")
    })
    public void testCiscoNetwork() throws Exception {

    	m_nodeDao.save(getExampleCom());
    	m_nodeDao.save(getLaptop());
    	m_nodeDao.save(getCisco7200a());
    	m_nodeDao.save(getCisco7200b());
    	m_nodeDao.save(getCisco3700());
    	m_nodeDao.save(getCisco2691());
    	m_nodeDao.save(getCisco1700());
    	m_nodeDao.save(getCisco3600());
    	m_nodeDao.flush();
    	
        final OnmsNode laptop = m_nodeDao.findByForeignId("linkd", LAPTOP_NAME);
        final OnmsNode cisco7200a = m_nodeDao.findByForeignId("linkd", CISCO7200A_NAME);
        final OnmsNode cisco7200b = m_nodeDao.findByForeignId("linkd", CISCO7200B_NAME);
        final OnmsNode cisco3700  = m_nodeDao.findByForeignId("linkd", CISCO3700_NAME);
        final OnmsNode cisco2691  = m_nodeDao.findByForeignId("linkd", CISCO2691_NAME);
        final OnmsNode cisco1700  = m_nodeDao.findByForeignId("linkd", CISCO1700_NAME);
        final OnmsNode cisco3600  = m_nodeDao.findByForeignId("linkd", CISCO3600_NAME);

        assertTrue(m_linkd.scheduleNodeCollection(laptop.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco7200a.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco7200b.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco3700.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco2691.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco1700.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco3600.getId()));

        assertTrue(m_linkd.runSingleSnmpCollection(laptop.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco7200a.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco7200b.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco3700.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco2691.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco1700.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco3600.getId()));

        assertTrue(m_linkd.runSingleLinkDiscovery("example1"));
        
        final List<DataLinkInterface> links = m_dataLinkInterfaceDao.findAll();
        int start = getStartPoint(links);
        for (final DataLinkInterface link: links) {
            int id = link.getId().intValue();
            if (id == start) {
                checkLink(laptop, cisco7200a, 10, 3, link);
                assertEquals(DiscoveryProtocol.iproute, link.getProtocol());
            } else if (id == start+1) {
                checkLink(cisco7200a, cisco7200b, 2, 4, link);
                assertEquals(DiscoveryProtocol.iproute, link.getProtocol());
            } else if (id == start+10) {
                checkLink(cisco7200b, cisco7200a, 4, 2, link);
                assertEquals(DiscoveryProtocol.cdp, link.getProtocol());
            } else if (id == start+2) {
                checkLink(cisco7200b, cisco2691, 1, 4, link);
                assertEquals(DiscoveryProtocol.iproute, link.getProtocol());
            } else if (id == start+8) {
                checkLink(cisco2691,cisco7200b , 4, 1, link);
                assertEquals(DiscoveryProtocol.cdp, link.getProtocol());
            } else if (id == start+3) {
                checkLink(cisco7200b, cisco3700, 2, 1, link);
                assertEquals(DiscoveryProtocol.iproute, link.getProtocol());
            } else if (id == start+9) {
                checkLink(cisco3700, cisco7200b, 1, 2, link);
                assertEquals(DiscoveryProtocol.cdp, link.getProtocol());
            } else if (id == start+4) {
                checkLink(cisco1700, cisco2691, 2, 2, link);
                assertEquals(DiscoveryProtocol.iproute, link.getProtocol());
            } else if (id == start+7) {
                checkLink(cisco1700, cisco2691, 2, 2, link);
                assertEquals(DiscoveryProtocol.cdp, link.getProtocol());
            } else if (id == start+6) {
                checkLink(cisco3600, cisco2691, 2, 1, link);
                assertEquals(DiscoveryProtocol.cdp, link.getProtocol());
            } else if (id == start+5) {
                checkLink(cisco3600, cisco3700, 1, 3, link);
                assertEquals(DiscoveryProtocol.iproute, link.getProtocol());
            } else if (id == start+11) {
                checkLink(cisco3600, cisco3700, 1, 3, link);
                assertEquals(DiscoveryProtocol.cdp, link.getProtocol());
            } else {
                assertEquals(false, true);
            }
        }

        assertEquals("we should have found 12 data links", 12, links.size());
    }
    
    
    /*
     *  Discover the following topology
     *  The CDP protocol must found all the links
     *  Either Ip Route must found links
     * 
     *  laptop
     *     |
     *  cisco7200a (2) --- (4) cisco7200b (1) --- (4) cisco2691 (2) --- (2) cisco1700
     *                     (2)                    (1)    
     *                      |                      |
     *                     (1)                    (2)
     *                  cisco3700  (3) --- (1)  cisco3600      
     */	
    @Test
    @JUnitSnmpAgents(value={
        @JUnitSnmpAgent(host="10.1.1.1", port=161, resource="classpath:linkd/nms101/cisco7200a.properties"),
        @JUnitSnmpAgent(host="10.1.2.2", port=161, resource="classpath:linkd/nms101/cisco7200b.properties")
    })
    public void testsimpleCdpLinkCisco7200aCisco7200b() throws Exception {

        for (Package pkg : Collections.list(m_linkdConfig.enumeratePackage())) {
            pkg.setUseIpRouteDiscovery(false);
            pkg.setUseOspfDiscovery(false);
            pkg.setUseLldpDiscovery(false);
            pkg.setUseBridgeDiscovery(false);
            pkg.setSaveRouteTable(false);
            pkg.setSaveStpNodeTable(false);
            pkg.setSaveStpInterfaceTable(false);
            pkg.setEnableVlanDiscovery(false);
            pkg.setUseIsisDiscovery(false);
        }

    	m_nodeDao.save(getCisco7200a());
    	m_nodeDao.save(getCisco7200b());
    	m_nodeDao.flush();
    	
        final OnmsNode cisco7200a = m_nodeDao.findByForeignId("linkd", CISCO7200A_NAME);
        final OnmsNode cisco7200b = m_nodeDao.findByForeignId("linkd", CISCO7200B_NAME);
        assertTrue(m_linkd.scheduleNodeCollection(cisco7200a.getId()));
        assertTrue(m_linkd.scheduleNodeCollection(cisco7200b.getId()));
 
        assertTrue(m_linkd.runSingleSnmpCollection(cisco7200a.getId()));
        assertTrue(m_linkd.runSingleSnmpCollection(cisco7200b.getId()));
 
        assertTrue(m_linkd.runSingleLinkDiscovery("example1"));
        
        final List<DataLinkInterface> ifaces = m_dataLinkInterfaceDao.findAll();
        for (final DataLinkInterface link: ifaces) {
            printLink(link);
        }

        assertEquals("we should have found 1 data links", 1, ifaces.size());
    }


}
