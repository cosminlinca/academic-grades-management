package Repository;

import Domain.Nota;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InFileNotaRepository extends AbstractFileRepository<Pair<Integer, Integer>, Nota>{
    public InFileNotaRepository(String fileName) {
        super(fileName);
    }


    @Override
    public Nota extractEntity(List<String> attributes) {
        int idStudent = Integer.parseInt(attributes.get(0).split("=")[1]);
        int idTema = Integer.parseInt(attributes.get(1).split("=")[1]);
        int idCadruDidactic = Integer.parseInt(attributes.get(2).split("=")[1]);
        double valoare = Double.parseDouble(attributes.get(3).split("=")[1]);
        String data = attributes.get(4).split("=")[1];
        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(data, dataFormat);
        //String feedback = attributes.get(5).split("=")[1];

        Nota nota = new Nota(idStudent, idTema,idCadruDidactic, valoare, dateTime);
        return nota;
    }

}
