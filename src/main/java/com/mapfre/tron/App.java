package com.mapfre.tron;

import java.util.List;

import com.mapfre.tron.bl.BlExtractImpl;
import com.mapfre.tron.bl.IBlExtract;
import com.mapfre.tron.model.SwaggerData;

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
        List<SwaggerData> datas = iBlExtract.getData();

        for (SwaggerData myData : datas) {
            log.debug("data: " + myData);
        }

        log.info("App.main: end execution");
    }

}
