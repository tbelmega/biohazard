package de.belmega.biohazard.server.printing;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.ArrayList;

/**
 * @author tbelmega on 08.01.2017.
 */
public class PrintWorldList {

    public static JRBeanCollectionDataSource createDataSource() {
        ArrayList<TestBean> beanCollection = new ArrayList<>();
        beanCollection.add(new TestBean("was geht"));

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(beanCollection);
        return beanCollectionDataSource;
    }

}
