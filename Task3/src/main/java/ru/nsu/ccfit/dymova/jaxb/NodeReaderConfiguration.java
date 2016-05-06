package ru.nsu.ccfit.dymova.jaxb;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Configuration
public class NodeReaderConfiguration {

    @Bean
    @Scope(scopeName = "prototype")
    JaxbXmlNodeReader nodeReader() throws IOException, JAXBException, XMLStreamException {
        FileInputStream fin = new FileInputStream(Paths.get("src", "main", "resources", "RU-NVS.osm.bz2").toString()); //todo who should close?
        BufferedInputStream in = new BufferedInputStream(fin);
        BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
        return new JaxbXmlNodeReader(bzIn);
    }

}

