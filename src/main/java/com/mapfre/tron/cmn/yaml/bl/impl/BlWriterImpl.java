
package com.mapfre.tron.bl.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.mapfre.tron.bl.IBlWriter;
import com.mapfre.tron.model.SwaggerData;

import lombok.extern.slf4j.Slf4j;

/**
 * The writer service implementation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 28 Feb 2022 - 15:16:10
 */
@Slf4j
public class BlWriterImpl implements IBlWriter {

    /**
     * Write a text file from the giving data.
     *
     * @param filename -> The name of the file
     * @param data     -> The data to write
     * @param apiTitle -> The title of the api
     */
    @Override
    public void givingWritingDataToFile(final String filename, final List<SwaggerData> data, final String apiTitle) {

        if (data != null && !data.isEmpty()) {
            File file = new File("out/" + filename);
            
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(file)) ){
                
                printWriter.println("<html lang=\"en\">");
                printHead(printWriter);
                printWriter.println("");
                printWriter.println("<body>");
                printWriter.println("");
                printWriter.println("\t<div id=\"swagger-ui\">");
                printWriter.println("\t\t<section data-reactroot=\"\" class=\"swagger-ui swagger-container\">");
                printWriter.println("\t\t\t<div class=\"swagger-ui\">");
                printWriter.println("\t\t\t\t<div>");
                printWriter.println("");
                infoContainer(apiTitle, printWriter);
                printWriter.println("");

                printWriter.println("\t\t\t\t\t<div class=\"wrapper\">");
                printWriter.println("\t\t\t\t\t\t<section class=\"block col-12 block-desktop col-12-desktop\">");
                printWriter.println("\t\t\t\t\t\t\t<div>");
                printWriter.println("");

                String beforeTag = null;
                int k = 0;
                for (SwaggerData myData : data) {
                    if (k == 0) {
                        // initTag
                        beforeTag = data.get(k).getTag();
                        initTagSection(myData, printWriter);
                    }

                    if (!data.get(k).getTag().equalsIgnoreCase(beforeTag)) {
                        // endTag
                        endTagSection(printWriter);
                        printWriter.println("");

                        // initTag
                        beforeTag = myData.getTag();
                        initTagSection(myData, printWriter);
                    }

                    // processing protocol, path and summary
                    switch (myData.getProtocol().toUpperCase()) {
                    case "PUT":
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t<div class=\"opblock opblock-put\">");
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t\t<div class=\"opblock-summary opblock-summary-put\">");
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"opblock-summary-method\">PUT</span>");
                        break;
                        
                    case "POST":
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t<div class=\"opblock opblock-post\">");
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t\t<div class=\"opblock-summary opblock-summary-post\">");
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"opblock-summary-method\">POST</span>");
                        break;

                    case "DELETE":
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t<div class=\"opblock opblock-delete\">");
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t\t<div class=\"opblock-summary opblock-summary-delete\">");
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"opblock-summary-method\">DELETE</span>");
                        break;

                    case "GET":
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t<div class=\"opblock opblock-get\">");
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t\t<div class=\"opblock-summary opblock-summary-get\">");
                        printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"opblock-summary-method\">GET</span>");
                        break;

                    default:
                        break;
                    }

                    printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"opblock-summary-path\">");
                    printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t\t<span>");
                    printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + myData.getBasePath().concat(myData.getPath()));
                    printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t\t</span>");
                    printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t</span>");
                    printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"opblock-summary-description\">");
                    printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t\t" + myData.getSummary());
                    printWriter.println("\t\t\t\t\t\t\t\t\t\t\t\t</div>");

                    printWriter.println("\t\t\t\t\t\t\t\t\t\t\t</div>");
                    printWriter.println("\t\t\t\t\t\t\t\t\t\t</div>");

                    if (k == data.size() -1) {
                        // endTag
                        endTagSection(printWriter);
                    }

                    k++;
                }

                printWriter.println("");
                printWriter.println("\t\t\t\t\t\t\t</div>");
                printWriter.println("\t\t\t\t\t\t</section>");
                printWriter.println("\t\t\t\t\t</div>");

                printWriter.println("");
                printWriter.println("\t\t\t\t</div>");
                printWriter.println("\t\t\t</div>");
                printWriter.println("\t\t</section>");
                printWriter.println("\t</div>");
                printWriter.println("");
                printWriter.println("</body>");
                printWriter.println("</html>");

            } catch (IOException e) {
                log.error(e.getMessage());
            }

        }

    }

    private void endTagSection(PrintWriter printWriter) {
        printWriter.println("\t\t\t\t\t\t\t\t\t</div>");
        printWriter.println("\t\t\t\t\t\t\t\t</div>");
    }

    private void initTagSection(final SwaggerData mydata, PrintWriter printWriter) {
        printWriter.println("\t\t\t\t\t\t\t\t<div class=\"opblock-tag-section is-open\">");
        printWriter.println("\t\t\t\t\t\t\t\t\t<h4 class=\"opblock-tag\" id=\"operations-tag-Policy\">");
        printWriter.println("\t\t\t\t\t\t\t\t\t\t<span>");
        printWriter.println("\t\t\t\t\t\t\t\t\t\t\t" + mydata.getTag());
        printWriter.println("\t\t\t\t\t\t\t\t\t\t</span>");
        printWriter.println("\t\t\t\t\t\t\t\t\t</h4>");
        printWriter.println("\t\t\t\t\t\t\t\t\t<div style=\"height: auto; border: none; margin: 0px; padding: 0px;\">");
    }

    private void infoContainer(final String apiTitle, PrintWriter printWriter) {
        printWriter.println("\t\t\t\t\t<div class=\"information-container wrapper\">");
        printWriter.println("\t\t\t\t\t\t<section class=\"block col-12\">");
        printWriter.println("\t\t\t\t\t\t\t<div class=\"info\">");
        printWriter.println("\t\t\t\t\t\t\t\t<hgroup class=\"main\">");
        printWriter.println("\t\t\t\t\t\t\t\t\t<h2 class=\"title\">");
        printWriter.println("\t\t\t\t\t\t\t\t\t\t" + apiTitle);
        printWriter.println("\t\t\t\t\t\t\t\t\t</h2>");
        printWriter.println("\t\t\t\t\t\t\t\t</hgroup>");
        printWriter.println("\t\t\t\t\t\t\t</div>");
        printWriter.println("\t\t\t\t\t\t</section>");
        printWriter.println("\t\t\t\t\t</div>");
    }

    private void printHead(PrintWriter printWriter) {
        printWriter.println("<head>");
        printWriter.println("<meta charset=\"UTF-8\">");
        printWriter.println("");
        printWriter.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EDGE\">");
        printWriter.println("<title>Swagger UI</title>");
        printWriter.println("");
        printWriter.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"swagger-ui.css\">");
        printWriter.println("<style>");
        printWriter.println("html {");
        printWriter.println("\tbox-sizing: border-box;");
        printWriter.println("\toverflow: -moz-scrollbars-vertical;");
        printWriter.println("\toverflow-y: scroll;");
        printWriter.println("}");
        printWriter.println("");
        printWriter.println("*, *:before, *:after {");
        printWriter.println("\tbox-sizing: inherit;");
        printWriter.println("}");
        printWriter.println("");
        printWriter.println("body {");
        printWriter.println("\tmargin: 0;");
        printWriter.println("\tbackground: #fafafa;");
        printWriter.println("}");
        printWriter.println("</style>");
        printWriter.println("</head>");
    }

}
