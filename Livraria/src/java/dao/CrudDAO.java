package dao;

import java.util.List;

public interface CrudDAO<E> {
    
    public E buscarId(int id);
    public List<E> buscarTodas();
    public List<E> buscarInstancia();
    public void remover(E entidade);
    public E persistir(E entidade);
    public void removeAll();
    
}
