package br.com.rcc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SqlUtil {
    private static Logger log = Logger.getLogger( SqlUtil.class.getCanonicalName() );

    private Map<String, String> data;
    private String[] files;

    public SqlUtil(String[] files) {
        this.files = files;
        loadXml();
    }

    // ---------------------------------

    public String get(String id) {
        return data.get(id);
    }

    // ---------------------------------

    /**
     * Method to read and create de Cache of Querys.
     */
    private void loadXml() {
        Map<String, String> data = new HashMap<>(60);

        for( String file : files ) {
            try {
                InputStream fileIn = SqlUtil.class.getClassLoader().getResourceAsStream( file );
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( fileIn );
                doc.getDocumentElement().normalize();

                NodeList sqlNodes = doc.getDocumentElement().getChildNodes();
                for( int i = 0; i < sqlNodes.getLength(); i++ ) {
                    Node nodeItem = sqlNodes.item( i );
                    if( !(nodeItem instanceof Element) ) continue;
                    Element element = (Element) nodeItem;
                    String id = element.getAttribute("id");
                    String content = element.getTextContent();
                    data.put(id, content);
                }
            } catch(SAXException ex) {
                log.log(Level.SEVERE, "Error reading '"+ file +"' file! It's need to be a XML file.", ex);
            } catch(IOException ex) {
                log.log(Level.SEVERE, "Error reading '"+ file +"' file!", ex);
            } catch(ParserConfigurationException ex) {
                log.log(Level.SEVERE, "Error reading '"+ file +"' file! Something wrong with SAX configuration.", ex);
            }
        }
        
        this.data = data;
    }


}