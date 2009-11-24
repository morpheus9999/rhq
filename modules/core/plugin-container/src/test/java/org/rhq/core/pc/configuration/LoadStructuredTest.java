/*
 * RHQ Management Platform
 * Copyright (C) 2005-2008 Red Hat, Inc.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License, version 2, as
 * published by the Free Software Foundation, and/or the GNU Lesser
 * General Public License, version 2.1, also as published by the Free
 * Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License and the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and the GNU Lesser General Public License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.rhq.core.pc.configuration;

import org.jmock.Expectations;
import org.rhq.core.domain.configuration.Configuration;
import org.rhq.core.domain.configuration.PropertySimple;
import org.rhq.core.pc.util.ComponentService;
import org.rhq.core.pc.util.FacetLockType;
import org.rhq.core.pluginapi.configuration.ResourceConfigurationFacet;
import static org.testng.Assert.assertNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoadStructuredTest extends ConfigManagementTest {

    ComponentService componentService;

    ConfigurationUtilityService configUtilityService;

    ResourceConfigurationFacet configFacet;

    LoadStructured loadStructured;

    @BeforeMethod
    public void setup() {
        componentService = context.mock(ComponentService.class);
        configUtilityService = context.mock(ConfigurationUtilityService.class);

        configFacet = context.mock(ResourceConfigurationFacet.class);

        loadStructured = new LoadStructured();
        loadStructured.setComponentService(componentService);
        loadStructured.setConfigurationUtilityService(configUtilityService);
    }

    @Test
    public void structuredConfigShouldGetLoaded() throws Exception {
        Configuration config = new Configuration();
        config.put(new PropertySimple("x", "1"));
        config.put(new PropertySimple("y", "2"));

        addDefaultExpectations(config);

        Configuration loadedConfig = loadStructured.execute(resourceId);

        assertStructuredLoaded(config, loadedConfig);
    }

    @Test
    public void theConfigNotesShouldGetSet() throws Exception {
        final Configuration config = new Configuration();
        config.setNotes(null);

        addDefaultExpectations(config);

        Configuration loadedConfig = loadStructured.execute(resourceId);

        assertNotesSetToDefault(loadedConfig);
    }

    @Test
    public void nullShouldBeReturnedWhenStructuredIsNull() throws Exception {
        final Configuration config = null;

        context.checking(new Expectations() {{
            atLeast(1).of(componentService).getComponent(resourceId,
                                                         ResourceConfigurationFacet.class,
                                                         FacetLockType.READ,
                                                         ConfigManagement.FACET_METHOD_TIMEOUT,
                                                         daemonThread,
                                                         onlyIfStarted);
            will(returnValue(configFacet));

            allowing(componentService).getResourceType(resourceId); will(returnValue(resourceType));

            atLeast(1).of(configFacet).loadStructuredConfiguration(); will(returnValue(null));    
        }});

        Configuration loadedConfig = loadStructured.execute(resourceId);

        assertNull(loadedConfig, "Expected null to be returned when facet returns null for structured.");
    }

    private void addDefaultExpectations(final Configuration config) throws Exception {
        context.checking(new Expectations() {{
            atLeast(1).of(componentService).getComponent(resourceId,
                                                         ResourceConfigurationFacet.class,
                                                         FacetLockType.READ,
                                                         ConfigManagement.FACET_METHOD_TIMEOUT,
                                                         daemonThread,
                                                         onlyIfStarted);
            will(returnValue(configFacet));

            allowing(componentService).getResourceType(resourceId); will(returnValue(resourceType));

            atLeast(1).of(configFacet).loadStructuredConfiguration(); will(returnValue(config));

            atLeast(1).of(configUtilityService).normalizeConfiguration(config, getResourceConfigDefinition());

            atLeast(1).of(configUtilityService).validateConfiguration(config, getResourceConfigDefinition());
        }});
    }

}
