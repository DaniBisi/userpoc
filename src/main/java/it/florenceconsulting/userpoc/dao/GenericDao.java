/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author daniele
 */


public interface GenericDao<T, ID extends Serializable> {

	/** Restituisce il numero di oggetti nel database. */
	public int count();

	/** Cancella un oggetto dal database dato il suo ID. */
	public void deleteById(ID id);

	/** Cancella un oggetto dal database. */
	public void delete(T entity);

	/** Cancella dal database gli oggetti specificati del loro ID. */
	public void deleteById(ID... ids);

	/** Cancella gli oggetti specificati dal database. */
	public void deleteEntities(T... entities);
	/** Cancella un oggetto dal database. */
	public void deleteEntity(T entity);

	public boolean exists(ID id);

	/** Restituisce tutti gli oggetti. */
	public List<T> getAll();

	/** Restituisce maxResult oggetti. */
	public List<T> getAll(int maxResult);

	public List<T> getAll(int start, int size);

	/**
	 * Restituisce tutti gli oggetti ordinati per il campo specificato in senzo
	 * ascendente/discendente.
	 */
	public List<T> getAllSortedByField(String field, boolean asc );
	
	/**
	 * Restituisce una lista di oggetti a partire dalla collezione di ID. Se la
	 * collezione e' vuota restituisce null.
	 */
	public List<T> getById(Collection<ID> ids);

	/**
	 * Restituisce un oggetto dal suo ID. Se l'oggetto non e' presente nel
	 * database restituisce null.
	 */
	public T getById(ID id);

	/**
	 * Restituisce una lista di oggetti a partire dall'array di ID. Se l'array
	 * e' vuoto restituisce null.
	 */
	public List<T> getById(ID... ids);


	/** Effettua il merge di un oggetto. */
	public T merge(T entity);

	/** Salva un oggetto. */
	public T save(T entity);

	/** Aggiorna un oggetto. */
	public T update(T entity);
        
        
}
