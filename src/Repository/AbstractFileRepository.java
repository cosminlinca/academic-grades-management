package Repository;

import Domain.HasID;

import java.io.*;
import java.util.Arrays;
import java.util.List;


public abstract class AbstractFileRepository<ID, E extends HasID<ID>> extends InMemoryCrudRepository<ID, E> {
    String fileName;
    public AbstractFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    /**
     * Load data from file to memory
     */
    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while((linie=br.readLine())!=null){
                List<String> attr= Arrays.asList(linie.split("\\|"));
                E e=extractEntity(attr);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract E extractEntity(List<String> attributes);

    @Override
    public E save(E entity){
        E e=super.save(entity);
        if (e==null)
        {
            writeToFile(entity);
        }
        return e;
    }

    @Override
    public E delete(ID id) {
        E e = super.delete(id);
        if(e != null) {
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(fileName);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            printWriter.close();
            writeToFile();
        }
        return e;
    }

    @Override
    public E update(E entity) {
        E e = super.update(entity);
        if(e == null) {
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(fileName);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            printWriter.close();
            writeToFile();
        }
        return e;
    }


    /**
     * Write data from memory to file
     */
    protected void writeToFile() {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            super.findAll().forEach(e -> {
                try {
                    bW.write(e.toString());
                    bW.newLine();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write(Append) an entity to file
     * @param entity
     */
    protected void writeToFile(E entity){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            bW.write(entity.toString());
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}