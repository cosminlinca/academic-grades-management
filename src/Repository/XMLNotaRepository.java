package Repository;

import Domain.Nota;
import Domain.NoteEntity;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDate;

public class XMLNotaRepository extends XMLAbstractRepository<String, NoteEntity> {
    public XMLNotaRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected Element createElementFromObject(Document document, NoteEntity nota) {
        Element e = document.createElement("nota");

        Element idStudent = document.createElement("idStudent");
        idStudent.setTextContent(nota.getIdStudent().toString());
        e.appendChild(idStudent);

        Element idTema = document.createElement("idTema");
        idTema.setTextContent(nota.getIdTema().toString());
        e.appendChild(idTema);

        Element idCadruDidactic = document.createElement("idCadruDidactic");
        idCadruDidactic.setTextContent(nota.getIdProfesor().toString());
        e.appendChild(idCadruDidactic);

        Element valoare = document.createElement("valoare");
        valoare.setTextContent(nota.getValoare().toString());
        e.appendChild(valoare);

        Element data = document.createElement("data");
        data.setTextContent(nota.getData().toString());
        e.appendChild(data);

        return e;
    }

    @Override
    protected NoteEntity createObjectFromElement(Element node) {
        Integer idStudent = Integer.parseInt(node.getElementsByTagName("idStudent").item(0).getTextContent());
        Integer idTema = Integer.parseInt(node.getElementsByTagName("idTema").item(0).getTextContent());
        Integer idCadruDidactic = Integer.parseInt(node.getElementsByTagName("idCadruDidactic").item(0).getTextContent());
        Double valoare = Double.parseDouble(node.getElementsByTagName("valoare").item(0).getTextContent());
        LocalDate localDate = LocalDate.parse(node.getElementsByTagName("data").item(0).getTextContent());

        return new NoteEntity(idStudent, idTema, idCadruDidactic, valoare, localDate);
    }
}
