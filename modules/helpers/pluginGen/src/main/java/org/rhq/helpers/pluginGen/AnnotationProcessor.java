/*
 * RHQ Management Platform
 * Copyright (C) 2005-2013 Red Hat, Inc.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */

package org.rhq.helpers.pluginGen;

import java.util.List;

/**
 * Processor that scans a directory for annotated classes and generates metrics etc. from them.
 * @author Heiko W. Rupp
 */
public class AnnotationProcessor {

    private List<Class> classList;
    private final DirectoryClassLoader classLoader;

    public AnnotationProcessor(String baseDirectory) {
        classLoader = new DirectoryClassLoader();
        classLoader.setBaseDir(baseDirectory);
    }

    public void populate(Props props) {
        classList = classLoader.findClasses();

        props.populateMetrics(classList);
        props.populateOperations(classList);
    }
}
