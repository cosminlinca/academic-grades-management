package Repository;

import Domain.Tema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLTemaRepository extends XMLAbstractRepository<Integer, Tema> {
    public XMLTemaRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected Element createElementFromObject(Document document, Tema tema) {
        Element e = document.createElement("tema");
        e.setAttribute("temaId", tema.getID().toString());

        Element descriere = document.createElement("descriere");
        descriere.setTextContent(tema.getDescriere());
        e.appendChild(descriere);

        Element deadline = document.createElement("deadline");
        deadline.setTextContent(tema.getDeadline().toString());
        e.appendChild(deadline);

        Element primire = document.createElement("primire");
        primire.setTextContent(tema.getPrimireTema().toString());
        e.appendChild(primire);

        return e;
    }

    @Override
    protected Tema createObjectFromElement(Element node) {
        Integer temaId = Integer.parseInt(node.getAttribute("temaId"));
        String descriere = node.getElementsByTagName("descriere").item(0).getTextContent();
        Integer deadline = Integer.parseInt(node.getElementsByTagName("deadline").item(0).getTextContent());
        Integer primire = Integer.parseInt(node.getElementsByTagName("primire").item(0).getTextContent());

        return new Tema(temaId, descriere, deadline, primire);
    }
}
