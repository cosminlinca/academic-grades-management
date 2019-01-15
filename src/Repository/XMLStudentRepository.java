package Repository;

import Domain.Student;
import Domain.StudentiEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLStudentRepository extends XMLAbstractRepository<Integer, StudentiEntity> {
    public XMLStudentRepository(String fileName) {
        super(fileName);
    }

    @Override
    protected Element createElementFromObject(Document document, StudentiEntity student) {
        Element e = document.createElement("student");
        e.setAttribute("studentId", student.getID().toString());

        Element nume = document.createElement("name");
        nume.setTextContent(student.getNume());
        e.appendChild(nume);

        Element grupa = document.createElement("grupa");
        grupa.setTextContent(student.getGrupa().toString());
        e.appendChild(grupa);

        Element email = document.createElement("email");
        email.setTextContent(student.getEmail());
        e.appendChild(email);

        Element present = document.createElement("present");
        present.setTextContent(new Boolean(student.isPrezent()).toString());
        e.appendChild(present);

        return e;
    }

    @Override
    protected StudentiEntity createObjectFromElement(Element node) {
        Integer studentId = Integer.parseInt(node.getAttribute("studentId"));
        String name = node.getElementsByTagName("name").item(0).getTextContent();
        Integer grupa = Integer.parseInt(node.getElementsByTagName("grupa").item(0).getTextContent());
        String email = node.getElementsByTagName("email").item(0).getTextContent();
        boolean present = Boolean.parseBoolean(node.getElementsByTagName("present").item(0).getTextContent());
        StudentiEntity student = new StudentiEntity(studentId, name, grupa, email);
        student.setPrezent(present);
        return student;
    }
}
