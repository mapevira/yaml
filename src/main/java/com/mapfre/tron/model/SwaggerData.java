
package com.mapfre.tron.model;

import lombok.Builder;
import lombok.Data;

/**
 * The swagger data properties.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 28 Feb 2022 - 10:58:57
 */
@Data
@Builder
public class SwaggerData {

    /** The basePath property. */
    private String basePath;

    /** The relative path property. */
    private String path;

    /** The protocol property.*/
    private String protocol;

    /** The tag property.*/
    private String tag;

    /** The summary property*/
    private String summary;

}
