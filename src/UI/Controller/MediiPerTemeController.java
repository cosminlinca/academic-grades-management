package UI.Controller;

import Domain.NoteEntity;
import Domain.TemeEntity;
import Service.AccountService;
import Service.NotaService;
import Service.StudentService;
import Service.TemaService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import javax.swing.text.Document;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MediiPerTemeController {
    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;

    @FXML
    public BarChart<?,?> barChart;
    @FXML
    public CategoryAxis axaTeme;
    @FXML
    public NumberAxis axaMedii;

    public void setMediiTemeService(StudentService studentService, TemaService temaService, NotaService notaService) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;
        initModel();
    }

    private void initModel() {
        XYChart.Series series = new XYChart.Series();

        HashMap<Integer, Pair<Double, Integer>> map = new HashMap<>();
        Iterator<NoteEntity> noteEntityIterator = this.notaService.getGradesIterator();
        while(noteEntityIterator.hasNext()) {
            NoteEntity noteEntity = noteEntityIterator.next();
            map.put(noteEntity.getIdTema(), new Pair<>(0d,0));
        }
        noteEntityIterator = this.notaService.getGradesIterator();
        while(noteEntityIterator.hasNext()) {
            NoteEntity noteEntity = noteEntityIterator.next();
            Double v2 = map.get(noteEntity.getIdTema()).getKey();
            Integer c2 = map.get(noteEntity.getIdTema()).getValue();
            v2 = v2 + noteEntity.getValoare();
            c2 = c2+1;
            map.put(noteEntity.getIdTema(), new Pair<>(v2,c2));
        }

        for (Map.Entry<Integer, Pair<Double, Integer>> entry: map.entrySet()
             ) {
            Double medie = 0d;
            if(entry.getValue().getValue() != 0)
                medie = entry.getValue().getKey() / entry.getValue().getValue();

            TemeEntity temeEntity = this.temaService.getTemaById(entry.getKey());
            series.getData().add(new XYChart.Data<>(temeEntity.getDescriere(),medie));

        }

        barChart.getData().addAll(series);
        //barChart.setStyle("-fx-barr-fill:  #008B8B");
    }

    @FXML
    public void initialize() {

    }

}
