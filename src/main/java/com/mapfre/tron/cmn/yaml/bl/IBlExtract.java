
package com.mapfre.tron.cmn.yaml.bl;

import java.io.File;
import java.util.List;

import com.mapfre.tron.cmn.yaml.model.SwaggerData;

/**
 * Extract the swagger information.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 28 Feb 2022 - 11:56:29
 */
public interface IBlExtract {

    /**
     * Get the swagger data from the yaml.
     *
     * @param file -> The yaml file 
     * @return     -> The swagger data list
     */
    List<SwaggerData> extract(File file);

}
