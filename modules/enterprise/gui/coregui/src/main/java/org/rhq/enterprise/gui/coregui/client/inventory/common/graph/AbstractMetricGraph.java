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
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package org.rhq.enterprise.gui.coregui.client.inventory.common.graph;


import java.util.Date;

import com.smartgwt.client.widgets.layout.VLayout;

import org.rhq.core.domain.measurement.MeasurementDefinition;
import org.rhq.enterprise.gui.coregui.client.CoreGUI;
import org.rhq.enterprise.gui.coregui.client.Messages;
import org.rhq.enterprise.gui.coregui.client.UserSessionManager;
import org.rhq.enterprise.gui.coregui.client.components.measurement.AbstractMeasurementRangeEditor;
import org.rhq.enterprise.gui.coregui.client.inventory.common.AbstractD3GraphListView;
import org.rhq.enterprise.gui.coregui.client.util.Log;
import org.rhq.enterprise.gui.coregui.client.util.message.Message;
import org.rhq.enterprise.gui.coregui.client.util.preferences.MeasurementUserPreferences;

/**
 * Common Metric Graph capability used across multiple metric rendering graphs.
 * The MetricGraphData delegate is wrapped for JSNI access via d3 charts.
 * Also, by delegating and directly extending MetricGraphData we have more
 * control over the visibility of what we want graphs to 'see'.
 *
 * @see MetricGraphData
 *
 * @author Mike Thompson
 */
public abstract class AbstractMetricGraph extends VLayout implements HasD3MetricJsniChart {

    private static final Messages MSG = CoreGUI.getMessages();
    private MetricGraphData metricGraphData;

    /**
     * Just so we have a handle to the the topmost view to call redraw graphs on a view
     * from this graph.
     */
    private AbstractD3GraphListView graphListView;

    public MetricGraphData getMetricGraphData() {
        return metricGraphData;
    }

    public void setMetricGraphData(MetricGraphData metricGraphData) {
        this.metricGraphData = metricGraphData;
    }

    public int getEntityId() {
        return metricGraphData.getEntityId();
    }

    @Override
    public void setEntityId(int entityId) {
        metricGraphData.setEntityId(entityId);
    }


    @Override
    public void setEntityName(String  entityName) {
        metricGraphData.setEntityName(entityName);
    }

    public int getDefinitionId() {
        return metricGraphData.getDefinitionId();
    }

    public MeasurementDefinition getDefinition() {
        return metricGraphData.getDefinition();
    }

    @Override
    public void setDefinition(MeasurementDefinition definition) {
        metricGraphData.setDefinition(definition);
    }

    public String getChartId() {
        return metricGraphData.getChartId();
    }

    public boolean showBarAvgTrendLine(){
        return metricGraphData.showBarAvgTrendLine();
    }

    public String getChartTitleMinLabel() {
        return metricGraphData.getChartTitleMinLabel();
    }

    public String getChartTitleAvgLabel() {
        return metricGraphData.getChartTitleAvgLabel();
    }

    public String getChartTitlePeakLabel() {
        return metricGraphData.getChartTitlePeakLabel();
    }

    public String getChartDateLabel() {
        return metricGraphData.getChartDateLabel();
    }

    public String getChartDownLabel() {
        return metricGraphData.getChartDownLabel();
    }

    public String getChartTimeLabel() {
        return metricGraphData.getChartTimeLabel();
    }

    public String getChartUnknownLabel() {
        return metricGraphData.getChartUnknownLabel();
    }

    public String getChartNoDataLabel() {
        return metricGraphData.getChartNoDataLabel();
    }

    public String getChartSingleValueLabel() {
        return metricGraphData.getChartSingleValueLabel();
    }

    public String getChartHoverStartLabel() {
        return metricGraphData.getChartHoverStartLabel();
    }

    public String getChartHoverEndLabel() {
        return metricGraphData.getChartHoverEndLabel();
    }

    public String getChartHoverPeriodLabel() {
        return metricGraphData.getChartHoverPeriodLabel();
    }

    public String getChartHoverBarLabel() {
        return metricGraphData.getChartHoverBarLabel();
    }

    public String getChartHoverTimeFormat() {
        return metricGraphData.getChartHoverTimeFormat();
    }

    public String getChartHoverDateFormat() {
        return metricGraphData.getChartHoverDateFormat();
    }

    public String getButtonBarDateTimeFormat(){
       return MSG.common_buttonbar_datetime_format_moment_js();
    }

    public int getChartHeight() {
        return metricGraphData.getChartHeight();
    }

    public void setChartHeight(Integer chartHeight) {
        metricGraphData.setChartHeight(chartHeight);
    }

    public String getChartTitle() {
        return metricGraphData != null ? metricGraphData.getChartTitle() : "";
    }
    public boolean isPortalGraph(){
        return metricGraphData.isPortalGraph();
    }


    public String getYAxisUnits() {
        return metricGraphData.getYAxisUnits();
    }

    public String getXAxisTimeFormatHours() {
        return metricGraphData.getXAxisTimeFormatHours();
    }

    public String getXAxisTimeFormatHoursMinutes() {
        return metricGraphData.getXAxisTimeFormatHoursMinutes();
    }

    public String getXAxisTitle() {
        return metricGraphData.getXAxisTitle();
    }

    public String getJsonMetrics() {
        return metricGraphData.getJsonMetrics();
    }

    public int getPortalId() {
        return metricGraphData.getPortalId();
    }

    public boolean isHideLegend(){
        return metricGraphData.isHideLegend();
    }

    public void setGraphListView(AbstractD3GraphListView graphListView) {
        this.graphListView = graphListView;
    }

    /**
     * Whenever we make a change to the date range save it here so it gets propogated to
     * the correct places.
     *
     * @param startTime double because JSNI doesnt support long
     * @param endTime   double because JSNI doesnt support long
     */
    public void saveDateRange(double startTime, double endTime) {
        MeasurementUserPreferences measurementUserPrefs = new MeasurementUserPreferences(UserSessionManager.getUserPreferences());

        Log.debug("Saving Date range: "+new Date((long)startTime) +  " - "+ new Date((long)endTime));
        final boolean advanced = true;
        AbstractMeasurementRangeEditor.MetricRangePreferences prefs = measurementUserPrefs.getMetricRangePreferences();
        prefs.explicitBeginEnd = advanced;
        prefs.begin = (long) startTime;
        prefs.end = (long) endTime;
        if (null != prefs.begin && null != prefs.end && prefs.begin > prefs.end) {
            CoreGUI.getMessageCenter().notify(new Message(MSG.view_measureTable_startBeforeEnd()));
        } else {
            measurementUserPrefs.setMetricRangePreferences(prefs);
        }

    }

    public void redrawGraphs(){
       graphListView.refreshData();
    }
}
