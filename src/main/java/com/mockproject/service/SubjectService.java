
package com.mockproject.service;

import com.mockproject.model.Subject;
import com.mockproject.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService  {

    public final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    SubjectRepository repo;

    public void save(Subject subj) {
        subj.setCreateDate(df.format(new Date()));
        String id= subj.getIdSubject();
        subj.setIdSubject(id);
        repo.save(subj);
    }


    public void delete(String idSubj) {
        repo.deleteById(idSubj);
    }

    public void restore(String idSubj){
        Subject s = repo.getByIdNonFilter(idSubj);
        s.setStatus(true);
        repo.save(s);
    }

    public Optional<Subject> findByIdSubject(String idSubject) {
        return repo.findById(idSubject);
    }


    public List<Subject> searchByName(String name) {
        return repo.findByNameSubjectContains(name);
    }

    public List<Subject> findAllNonFilter(){
        return repo.findAll();
    }

    public List<Subject> searchByIdUser(int idUser) {
        return repo.findAllByIdUser(idUser);
    }
}
