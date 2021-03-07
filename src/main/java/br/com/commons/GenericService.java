package br.com.commons;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public class GenericService <E, I, S extends JpaRepository<E, I>> {

    private S repository;
    private Class<E> tClass;

    public GenericService(S repository, Class<E> tClass){
        this.repository = repository;
        this.tClass = tClass;
    }

    protected String getEntityClassName(){ return tClass.getSimpleName().toLowerCase(); }

    protected S getRepository() { return this.repository; }

    public Optional<E> findById(I id) {
        return (Optional<E>) getRepository().findById(id);
    }

    public List<E> findAll(){
        return getRepository().findAll();
    }

    @Transactional
    public E save(E entity){
        return getRepository().save(entity);
    }
    
    @Transactional
    public void delete(E entity){
        getRepository().delete(entity);
    }

    @Transactional
    public List<E> saveAll(Iterable<E> entities) {
        return getRepository().saveAll(entities);
    }
}
