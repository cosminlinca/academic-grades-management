package Repository;

import Domain.HasID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public abstract class XMLAbstractRepository<ID, E extends HasID<ID>> extends InMemoryCrudRepository<ID, E> {
    private String fileName;

    public XMLAbstractRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try {
            //create an document using xml file
            Document document = DocumentBuilderFactory
                                .newInstance()
                                .newDocumentBuilder()
                                .parse(this.fileName);

            //get elements from document
            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for(int i=0; i<children.getLength(); ++i) {
                Node node = children.item(i);
                if(node instanceof Element) {
                    E e = createObjectFromElement((Element)node);
                    super.save(e);
                }
            }

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }


    }

    public void writeToFile() {
        try {
            //create an empty Document
            Document document = DocumentBuilderFactory
                                .newInstance()
                                .newDocumentBuilder()
                                .newDocument();

            //add the list elements to Document
            Element root = document.createElement("root");
            document.appendChild(root);
            super.findAll().forEach(e->{
                Element element = createElementFromObject(document, e);
                root.appendChild(element);
            });

            //write Document to file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(this.fileName));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E save(E entity) {
        E e = super.save(entity);
        if(e == null) {
            writeToFile();
        }
        return e;
    }

    @Override
    public E delete(ID id) {
        E e = super.delete(id);
        if(e != null) {
            writeToFile();
        }
        return e;
    }

    @Override
    public E update(E entity) {
        E e = super.update(entity);
        if(e != null) {
            writeToFile();
        }
        return e;
    }

    /**
     *
     * @param document
     * @param e
     * @return an object of type Element from an entity e
     */
    protected abstract Element createElementFromObject(Document document, E e);

    /**
     *
     * @param node
     * @return an entity from an Object of type Element
     */
    protected abstract E createObjectFromElement(Element node);




}
