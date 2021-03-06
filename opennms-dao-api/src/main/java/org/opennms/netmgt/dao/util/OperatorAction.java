/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2012 The OpenNMS Group, Inc.
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

package org.opennms.netmgt.dao.util;

import java.util.List;

import org.opennms.netmgt.model.events.Constants;
import org.opennms.netmgt.xml.event.Operaction;

/**
 * This is an utility class used to format the event operator actions info - to
 * be inserted into the 'events' table. This class only uses the operator action
 * and operator state information - the operator menu goes into a separate
 * database field
 *
 * @author <A HREF="mailto:sowmya@opennms.org">Sowmya Nataraj </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * @author <A HREF="mailto:sowmya@opennms.org">Sowmya Nataraj </A>
 * @author <A HREF="http://www.opennms.org/">OpenNMS </A>
 * @version $Id: $
 */
public class OperatorAction {
    /**
     * Format each operator action entry
     *
     * @param opact
     *            the entry
     * @return the formatted string
     */
    public static String format(Operaction opact) {
        String text = opact.getContent();
        String state = opact.getState();

        return Constants.escape(text, Constants.DB_ATTRIB_DELIM) + Constants.DB_ATTRIB_DELIM + state;

    }

    /**
     * Format the list of operator action entries of the event
     *
     * @param opacts
     *            the list
     * @param sz
     *            the size to which the formatted string is to be limited
     *            to(usually the size of the column in the database)
     * @return the formatted string
     */
    public static String format(List<Operaction> opacts, int sz) {
        StringBuffer buf = new StringBuffer();
        boolean first = true;

        for (Operaction opact : opacts) {
            if (!first) {
                buf.append(Constants.MULTIPLE_VAL_DELIM);
            } else {
                first = false;
            }

            buf.append(Constants.escape(format(opact), Constants.MULTIPLE_VAL_DELIM));
        }

        if (buf.length() >= sz) {
            buf.setLength(sz - 4);
            buf.append(Constants.VALUE_TRUNCATE_INDICATOR);
        }

        return buf.toString();
    }
}
