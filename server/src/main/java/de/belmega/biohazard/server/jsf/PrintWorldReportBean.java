package de.belmega.biohazard.server.jsf;

import de.belmega.biohazard.server.ejb.WorldDAO;
import de.belmega.biohazard.server.persistence.entities.WorldEntity;
import net.sf.jasperreports.engine.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@ManagedBean
@RequestScoped
public class PrintWorldReportBean {

    @Inject
    WorldDAO dao;
    private long worldId;

    public void btnPrintClick() {
        WorldEntity world = dao.findWorld(worldId);


        String fileName = "/test.jasper";
        InputStream inputStream = getClass().getResourceAsStream(fileName);

        String outFileName = "test.pdf";
        Map<String, Object> map = new HashMap<>();

        JasperPrint print = null;

        try {
            print = JasperFillManager.fillReport(
                    inputStream,
                    map,
                    new JREmptyDataSource());
        } catch (JRException e) {

            e.printStackTrace();
        }

        JRExporter exporter =
                new net.sf.jasperreports.engine.export.JRPdfExporter();
        exporter.setParameter(
                JRExporterParameter.OUTPUT_FILE_NAME,
                outFileName);
        exporter.setParameter(
                JRExporterParameter.JASPER_PRINT, print);

        try {
            exporter.exportReport();
        } catch (JRException e) {
            e.printStackTrace();
        }

        System.out.println("Created file: " + outFileName);
    }

    public long getWorldId() {
        return worldId;
    }

    public void setWorldId(long worldId) {
        this.worldId = worldId;
    }
}
