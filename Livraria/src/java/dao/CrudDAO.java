/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;

/**
 *
 * @author John Peter
 * @param <E>
 */
public interface CrudDAO<E> {
    
    public E buscarId(int id);
    public List<E> buscarTodas();
    public List<E> buscarInstancia();
    public void remover(E entidade);
    public E persistir(E entidade);
    public void removeAll();
    
}
