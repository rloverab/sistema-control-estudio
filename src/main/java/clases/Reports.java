/*
 * Copyright (C) 2021 Roger Lovera <roger.lovera>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package clases;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import servicios.ConnectionDB;

/**
 *
 * @author Roger Lovera <roger.lovera>
 */
public class Reports {

    private final ConnectionDB conn;

    public Reports(ConnectionDB conn) {
        this.conn = conn;
    }

    private void generateReport(
            String reportSource,
            Map<String, Object> params) {
        JasperReport jasperReport;
        JasperPrint jasperPrint;

        try {
            jasperReport = JasperCompileManager.compileReport(reportSource);
            if (jasperReport != null) {
                jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        params,
                        conn.getConnection());

                JasperViewer.viewReport(jasperPrint, false);
            }
        } catch (JRException ex) {
            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateReportPlanEstudio(
            Integer carrera_id,
            Integer nivel_id,
            Integer resolucion_id) {
        String reportSource;
        Map<String, Object> params;

        reportSource = "./templates/plan_estudio.jrxml";
        params = new HashMap<>();

        params.put("CARRERA_ID", carrera_id);
        params.put("NIVEL_ID", nivel_id);
        params.put("RESOLUCION_ID", resolucion_id);

        generateReport(reportSource, params);
    }
}
