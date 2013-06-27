package org.opennms.netmgt.model.topology;

import java.net.InetAddress;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import static org.opennms.core.utils.InetAddressUtils.str;

@Entity
@DiscriminatorValue("OSPF")
public final class OspfElementIdentifier extends ElementIdentifier {

	public final static String OSPF_IDENTIFIER_DISPLAY = "open short path first";
	private InetAddress m_ospfRouterId; 

	public OspfElementIdentifier(InetAddress ospfRouterid,Integer sourceNode) {
		super(sourceNode);
		m_ospfRouterId= ospfRouterid;
	}

    @Type(type="org.opennms.netmgt.model.InetAddressUserType")
	public InetAddress getOspfRouterId() {
		return m_ospfRouterId;
	}

	public void setOspfRouterId(InetAddress ospfRouterId) {
		m_ospfRouterId = ospfRouterId;
	}

	@Override
	public boolean equals(ElementIdentifier elementIdentifier) {
		if (elementIdentifier instanceof OspfElementIdentifier) 
			return (m_ospfRouterId.equals(((OspfElementIdentifier)elementIdentifier).getOspfRouterId()));
		return false;
	}
	
	/**
	 * <p>toString</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		return new ToStringBuilder(this)
			.append("ospfRouterId", str(m_ospfRouterId))
			.append("lastPoll", m_lastPoll)
			.append("sourceNode", m_sourceNode)
			.toString();
	}

	@Override
	public String displayElementidentifierType() {
		return OSPF_IDENTIFIER_DISPLAY;
	}

}
