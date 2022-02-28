
package com.mapfre.tron.bl;

import java.util.List;

import com.mapfre.tron.model.SwaggerData;

/**
 * Extract the swagger information.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 28 Feb 2022 - 11:56:29
 */
public interface IBlExtract {

    /**
     * Get the swagger data.
     *
     * @return -> The swagger data list
     */
    List<SwaggerData> getData();

}
