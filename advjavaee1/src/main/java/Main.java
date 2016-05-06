import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.stream.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        try(FileInputStream fin = new FileInputStream(Paths.get("src", "main", "resources", "RU-NVS.osm.bz2").toString());
            BufferedInputStream in = new BufferedInputStream(fin);
            BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in)) {

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(Paths.get("src", "main", "resources", "OSMSchema.xsd").toString()));
//            Schema schema = schemaFactory.newSchema(new File("src/famous.xsd"));

            JaxbXmlReader jaxbXmlReader = new JaxbXmlReader(bzIn, schema);
            Controller controller = new Controller(jaxbXmlReader);
            controller.run();


        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
