package de.belmega.biohazard.server.jsf;

import de.belmega.biohazard.server.ejb.WorldDAO;
import de.belmega.biohazard.server.persistence.entities.WorldEntity;
import de.belmega.biohazard.server.printing.PrintWorldList;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@ManagedBean
@RequestScoped
public class PrintWorldReportBean {

    @Inject
    WorldDAO dao;

    private long worldId;

    public void btnPrintClick() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
        response.setHeader("Content-disposition", "attachment; filename=\"name.pdf\""); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.

        OutputStream output = response.getOutputStream();

        fillResponseOutput(output);

        facesContext.responseComplete();
    }

    private void fillResponseOutput(OutputStream output) throws IOException {
        WorldEntity world = dao.findWorld(worldId);

        String fileName = "/test2.jasper";
        InputStream inputStream = getClass().getResourceAsStream(fileName);

        Map<String, Object> map = new HashMap<>();

        JRBeanCollectionDataSource dataSource = PrintWorldList.createDataSource();
        try {
            JasperPrint print = JasperFillManager.fillReport(
                    inputStream,
                    map,
                    dataSource);
            JasperExportManager.exportReportToPdfStream(print, output);
        } catch (JRException e) {

            e.printStackTrace();
        } finally {
            output.close();
        }
    }

    public long getWorldId() {
        return worldId;
    }

    public void setWorldId(long worldId) {
        this.worldId = worldId;
    }
}
