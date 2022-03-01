package com.mapfre.tron.cmn.yaml;

import java.io.File;
import java.util.List;

import com.mapfre.tron.cmn.yaml.bl.IBlExtract;
import com.mapfre.tron.cmn.yaml.bl.IBlWriter;
import com.mapfre.tron.cmn.yaml.bl.impl.BlExtractImpl;
import com.mapfre.tron.cmn.yaml.bl.impl.BlWriterImpl;
import com.mapfre.tron.cmn.yaml.model.SwaggerData;

import lombok.extern.slf4j.Slf4j;

/**
 * Application class.
 *
 * @author architecture - ${user}
 * @since 1.8
 * @version 28 feb 2022 9:21:00
 */
@Slf4j
public class App {

    public static void main(String[] args) {

        log.info("App.main: init execution");

        IBlExtract iBlExtract = new BlExtractImpl();
        IBlWriter iBlWriter = new BlWriterImpl();

        // Deleting files of the "out" folder
        File dirOut = new File("out");
        for (File file : dirOut.listFiles()) {
            if (!file.isDirectory() && !"swagger-ui.css".equals(file.getName())) {
                file.delete();
            }
        }

        // Loading the YAML files from the "in" folder
        File dir = new File("in");
        String name = null;
        List<SwaggerData> datas = null;

        for (File file : dir.listFiles()) {
            name = getFileNameWithoutExtension(file);

            // Extracting the data
            log.info("Extracting the file: " + file.getName());
            datas = iBlExtract.extract(file);

            if (log.isDebugEnabled()) {
                for (SwaggerData myData : datas) {
                    log.debug("data: " + myData);
                }
            }

            // Writing the HTML
            log.info("Trying to write the file: " + name.concat(".html"));
            iBlWriter.givingWritingDataToFile(name.concat(".html"), datas, name);
            log.info("File[" + name.concat(".html") + "] wrote");
        }

        log.info("App.main: end execution");
    }

    private static String getFileNameWithoutExtension(File file) {
        String fileName = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fileName = "";
        }

        return fileName;
    }

}
