/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2011-2012 The OpenNMS Group, Inc.
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

package org.opennms.serviceregistration;

public class ServiceRegistrationFactory {
	private static ServiceRegistrationStrategy s;
	private static final String CLASS_PROPERTY = "org.opennms.serviceregistration.strategy";
    private static final String[] classes = {
            "org.opennms.serviceregistration.strategies.AppleStrategy",
            "org.opennms.serviceregistration.strategies.JMDNSStrategy",
            "org.opennms.serviceregistration.strategies.NullStrategy"
    };
	
	private ServiceRegistrationFactory() {
	}

	public static synchronized ServiceRegistrationStrategy getStrategy() throws Exception {
		if (s == null) {
		    if (System.getProperty(CLASS_PROPERTY) != null) {
		        try {
	                s = (ServiceRegistrationStrategy)(Class.forName(System.getProperty(CLASS_PROPERTY)).newInstance());
		        } catch (NoClassDefFoundError e) {
		            System.err.println("unable to load class specified in " + CLASS_PROPERTY + ": " + e.getMessage());
		        }
		    }
            if (s == null) {
                for (String className : classes) {
		            try {
		                s = (ServiceRegistrationStrategy)(Class.forName(className).newInstance());
		            } catch (NoClassDefFoundError e) {
		                // fall through silently for now
		            } catch (UnsatisfiedLinkError e) {
                        // fall through silently for now
		            }
		            if (s != null) {
		                break;
		            }
		        }
		    }
		}
		
		if (s == null) {
		    System.err.println("an error occurred finding any service registration strategy");
		}
		return s;
	}
	
    @Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Singletons cannot be cloned.");
	}

}