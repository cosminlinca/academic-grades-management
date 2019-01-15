package Repository;

import Domain.Tema;

import java.util.List;

public class InFileTemaRepository extends AbstractFileRepository<Integer, Tema> {
    public InFileTemaRepository(String fileName) {
        super(fileName);
    }

    @Override
    public Tema extractEntity(List<String> attributes) {
        int id = Integer.parseInt(attributes.get(0).split("=")[1]);
        String descriere = attributes.get(1).split("=")[1];
        int deadline = Integer.parseInt(attributes.get(2).split("=")[1]);
        int primire = Integer.parseInt(attributes.get(3).split("=")[1]);

        Tema tema = new Tema(id, descriere, deadline, primire);
        return tema;
    }
}
