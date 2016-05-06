package ru.ccfit.dymova;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.nio.file.Paths;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class.getName());
    public static void main(String[] args) {

        try (FileInputStream fin = new FileInputStream(Paths.get("src", "main", "resources", "RU-NVS.osm.bz2").toString());
             BufferedInputStream in = new BufferedInputStream(fin);
             BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in)) {

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new StreamSource(ClassLoader.getSystemResourceAsStream("OSMSchema.xsd")));

            JaxbXmlReader jaxbXmlReader = new JaxbXmlReader(bzIn, schema);
            Controller controller = new Controller(jaxbXmlReader);

            controller.run();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
