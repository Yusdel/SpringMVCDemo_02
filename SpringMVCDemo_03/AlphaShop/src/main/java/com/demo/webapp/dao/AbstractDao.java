package com.demo.webapp.dao;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;

//JPQL https://docs.oracle.com/javaee/7/tutorial/persistence-querylanguage.htm
//CRITERIA API https://docs.oracle.com/javaee/7/tutorial/persistence-criteria.htm

/*
 * STEP B-Using: TODO Hibernate and JPA Framework
 */
public abstract class AbstractDao<I extends Serializable, Id extends Serializable>
	implements GenericRepository<I, Id>
{
	@PersistenceContext
	protected EntityManager entityManager;
	
	protected final Class<I> entityClass;
	
	@SuppressWarnings("unchecked")
	public AbstractDao()
	{
		this.entityClass = (Class<I>) ((ParameterizedType) 
				this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public List<I> SelTutti()
	{
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<I> query = builder.createQuery(this.entityClass);

        return this.entityManager.createQuery(
        		query.select(query.from(this.entityClass))).getResultList();
	}
	
	@Override
	public I SelById(Id id)
	{
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<I> query = builder.createQuery(this.entityClass);
			
		return this.entityManager.createQuery(
					query.where(
							builder.equal(
									query.from(this.entityClass).
									get("id"), id))).
					getSingleResult();
	}
	
	@Override
	public void Inserisci(I entity)
	{
		this.entityManager.persist(entity);
		flushAndClear();
	}
	
	@Override
	public void Aggiorna(I entity)
	{
		this.entityManager.merge(entity); 
		flushAndClear();
	}
	
	@Override
	public void Elimina(I entity)
	{
		
		this.entityManager.remove(this.entityManager.contains(entity) ? entity : this.entityManager.merge(entity));
		flushAndClear();
		
	}
	
	@Override
	public void EliminaById(Id id)
	{
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaDelete<I> query = builder.createCriteriaDelete(this.entityClass);
		
		this.entityManager.createQuery(
				query.where(
						builder.equal(
								query.from(this.entityClass)
								.get("id"), id)
        )).executeUpdate();
		
		flushAndClear();
	}
	
	private void flushAndClear() 
	{
		/*
		 * Entities are synchronized to the connected at Transaction Commit time. If you only have
		 * n = 1 ongoing transaction, changes on one or more entities get written to the DB the moment you call flush()
		 * on the EntityManager instance. 
		 */
	    entityManager.flush();
	    
	    /*
	     * Simply detaches all managed entities from the current persistence Context,
	     * thus causing any changes on entities which have not been flushed (committed) to get lost.
	     */
	    entityManager.clear();
	}
}
