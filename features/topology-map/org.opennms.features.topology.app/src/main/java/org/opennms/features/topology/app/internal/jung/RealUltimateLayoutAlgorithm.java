/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012 The OpenNMS Group, Inc.
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

package org.opennms.features.topology.app.internal.jung;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;

import org.apache.commons.collections15.Transformer;
import org.opennms.features.topology.api.GraphContainer;
import org.opennms.features.topology.app.internal.Edge;
import org.opennms.features.topology.app.internal.Graph;
import org.opennms.features.topology.app.internal.Vertex;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.SparseGraph;

public class RealUltimateLayoutAlgorithm extends AbstractLayoutAlgorithm {

    @Override
	public void updateLayout(GraphContainer graph) {
		
		Graph g = new Graph(graph);
		
		int szl = g.getSemanticZoomLevel();
		
		
		SparseGraph<Vertex, Edge> jungGraph = new SparseGraph<Vertex, Edge>();
		
		
		List<Vertex> vertices = g.getVertices(szl);
		
		for(Vertex v : vertices) {
			jungGraph.addVertex(v);
		}
		
		List<Edge> edges = g.getEdges(szl);
		
		for(Edge e : edges) {
			jungGraph.addEdge(e, e.getSource(), e.getTarget());
		}
		
		Dimension size = selectLayoutSize(g);
		Dimension paddedSize = new Dimension((int)(size.getWidth()*.75), (int)(size.getHeight()*75));
		
		doISOMLayout(jungGraph, size);
		doSpringLayout(jungGraph, size, LAYOUT_REPULSION);
		doFRLayout(jungGraph, paddedSize, (int)(size.getWidth()/8), (int)(size.getHeight()/8));
		doSpringLayout(jungGraph, size, LAYOUT_REPULSION);

		
	}

	private void doSpringLayout(SparseGraph<Vertex, Edge> jungGraph, Dimension size, int repulsion) {
		SpringLayout<Vertex, Edge> layout = new SpringLayout<Vertex, Edge>(jungGraph);
		layout.setInitializer(new Transformer<Vertex, Point2D>() {
            @Override
			public Point2D transform(Vertex v) {
				return new Point(v.getX(), v.getY());
			}
		});
		
		layout.setSize(size);
		layout.setRepulsionRange(repulsion);

		int count = 0;
		while(!layout.done() && count < 700) {
			layout.step();
			count++;
		}
		
		for(Vertex v : jungGraph.getVertices()) {
			v.setX((int)layout.getX(v));
			v.setY((int)layout.getY(v));
		}
	}
	
	private void doFRLayout(SparseGraph<Vertex, Edge> jungGraph, Dimension size, final int xOffset, final int yOffset) {
		FRLayout<Vertex, Edge> layout = new FRLayout<Vertex, Edge>(jungGraph);
		layout.setInitializer(new Transformer<Vertex, Point2D>() {
            @Override
			public Point2D transform(Vertex v) {
				return new Point(v.getX()-xOffset, v.getY()-yOffset);
			}
		});
		layout.setSize(size);
		
		while(!layout.done()) {
			layout.step();
		}
		
		
		for(Vertex v : jungGraph.getVertices()) {
			v.setX((int)layout.getX(v)+xOffset);
			v.setY((int)layout.getY(v)+yOffset);
		}
		
	}

	private void doISOMLayout(SparseGraph<Vertex, Edge> jungGraph, Dimension size) {
		ISOMLayout<Vertex, Edge> layout = new ISOMLayout<Vertex, Edge>(jungGraph);
		layout.setInitializer(new Transformer<Vertex, Point2D>() {
            @Override
			public Point2D transform(Vertex v) {
				return new Point(v.getX(), v.getY());
			}
		});
		layout.setSize(size);
		
		while(!layout.done()) {
			layout.step();
		}
		
		
		for(Vertex v : jungGraph.getVertices()) {
			v.setX((int)layout.getX(v));
			v.setY((int)layout.getY(v));
		}
		
	}


}