package Service;

import Domain.ConturiEntity;
import Repository.CrudRepository;
import Utils.PasswordSecurity;

import java.io.*;
import java.util.Iterator;

public class AccountService {

    CrudRepository<Integer, ConturiEntity> repository;

    public AccountService(CrudRepository<Integer, ConturiEntity> repository) {
        this.repository = repository;
    }

    public ConturiEntity addAccount(String username, String password, String rights, Integer id) {
        String securedPasswored = PasswordSecurity.encrypt(password);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("resources/conturi_id.txt"));
            Integer idA = Integer.parseInt(br.readLine());
            br.close();

            ConturiEntity conturiEntity = new ConturiEntity();
            conturiEntity.setID(idA);
            conturiEntity.setUsername(username);
            conturiEntity.setPassword(securedPasswored);
            conturiEntity.setDrepturi(rights);
            conturiEntity.setIdUtilizator(id);

            ConturiEntity valid = this.repository.save(conturiEntity);

            idA++;
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/conturi_id.txt"));
            writer.write(idA.toString());
            writer.close();

            return valid;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ConturiEntity deleteAccount(Integer idUser, String right) {
        ConturiEntity conturiEntity = null;
        for (ConturiEntity entity: this.repository.findAll()
             ) {
            if(entity.getIdUtilizator().equals(idUser) && entity.getDrepturi().equals(right)) {
                conturiEntity = this.repository.delete(entity.getID());
                return conturiEntity;
            }
        }
        return null;
    }
    public ConturiEntity getByUsername(String username) {
        Iterable<ConturiEntity> all = this.repository.findAll();
        for (ConturiEntity entity: all
             ) {
            if(entity.getUsername().equalsIgnoreCase(username)) return entity;
        }
        return null;
    }

    public ConturiEntity findById(Integer id) {
        Iterable<ConturiEntity> all = this.repository.findAll();
        for (ConturiEntity conturiEntity: all
             ) {
            if(conturiEntity.getIdUtilizator().equals(id))
                return conturiEntity;
        }
        return null;
    }
}

