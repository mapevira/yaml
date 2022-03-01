
package com.mapfre.tron.cmn.yaml.bl.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mapfre.tron.cmn.yaml.bl.IBlExtract;
import com.mapfre.tron.cmn.yaml.model.SwaggerData;

import lombok.extern.slf4j.Slf4j;

/**
 * Extract the swagger information.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 28 Feb 2022 - 12:00:12
 */
@Slf4j
public class BlExtractImpl implements IBlExtract {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<SwaggerData> extract(File file) {
        List<SwaggerData> datas = new ArrayList<>();

        // Instantiating a new ObjectMapper as a YAMLFactory
        ObjectMapper om = new ObjectMapper(new YAMLFactory());

        try {
            // Mapping the employee from the YAML file to the Employee class
            LinkedHashMap yamlMap = om.readValue(file, LinkedHashMap.class);

            SwaggerData data;
            String lvPath;
            String lvProtocol;
            String lvTag;
            String lvSummary;
            Map<String, String> memTagMap = null;

            // loading tags in memory
            LinkedHashMap pathsMap = (LinkedHashMap) yamlMap.get("paths");
            if (yamlMap.get("tags") != null) {
                memTagMap = new HashMap<>();
                List<LinkedHashMap> tagsList = (ArrayList) yamlMap.get("tags");
                String name;
                String description;
                for (LinkedHashMap tagMap : tagsList) {
                    name = (String) tagMap.get("name");
                    description = (String) tagMap.get("description");
                    memTagMap.put(name, description);
                }
            }

            Set<String> pathsSet = pathsMap.keySet();
            String lvBasePath = (String) yamlMap.get("basePath");
            LinkedHashMap tagsMap;
            LinkedHashMap protocolsMap;
            for (String path : pathsSet) {
                lvPath = path;
                protocolsMap = (LinkedHashMap) pathsMap.get(path);
                Set<String> protocolsSet = protocolsMap.keySet();
                for (String protocol : protocolsSet) {
                    lvProtocol = protocol;
                    tagsMap = (LinkedHashMap) protocolsMap.get(protocol);
                    lvTag = (String) ((ArrayList) tagsMap.get("tags")).get(0);
                    lvSummary = (String) tagsMap.get("summary");
                    data = SwaggerData.builder()
                            .path(lvPath)
                            .protocol(lvProtocol)
                            .basePath(lvBasePath)
                            .tag(lvTag)
                            .summary(lvSummary)
                            .build();

                    if (memTagMap != null) {
                        data.setMemTagMap(memTagMap);
                    }

                    datas.add(data);
                }

            }

            // ordering data list by tag, basepath, protocol, summary
            datas.sort((o1,o2) -> {
                int cmp = o1.getTag().toLowerCase().compareTo(o2.getTag().toLowerCase());
                if (cmp == 0) {
                    cmp = o1.getBasePath().toLowerCase().compareToIgnoreCase(o2.getBasePath().toLowerCase());
                }
                if (cmp == 0) {
                    cmp = o1.getPath().toLowerCase().compareToIgnoreCase(o2.getPath().toLowerCase());
                }
                if (cmp == 0) {
                    cmp = o1.getSummary().toLowerCase().compareTo(o2.getSummary().toLowerCase());
                }
                if (cmp == 0) {
                    cmp = o1.getProtocol().toLowerCase().compareToIgnoreCase(o2.getProtocol().toLowerCase());
                }

                return cmp;
            });

        } catch (StreamReadException e) {
            log.equals(e);
        } catch (DatabindException e) {
            log.equals(e);
        } catch (IOException e) {
            log.equals(e);
        }

        return datas;
    }

}
