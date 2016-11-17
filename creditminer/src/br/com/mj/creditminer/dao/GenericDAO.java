package br.com.mj.creditminer.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import br.com.mj.creditminer.model.Entidade;

public class GenericDAO<E extends Entidade> {

	private final static String UNIT_NAME = "CreditMiner";

	private EntityManager manager;

	public GenericDAO() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(UNIT_NAME);
		manager = factory.createEntityManager();
	}

	/**
	 * Método genérico que persiste a entidade no banco de dados.
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void save(E entity) throws Exception {
		try {
			manager.getTransaction().begin();
			if (entity.getId() == null) {
				manager.persist(entity);
			} else {
				manager.merge(entity);
			}
			manager.getTransaction().commit();
			manager.refresh(entity);
		} catch (Exception e) {
			manager.getTransaction().rollback();
			throw new Exception("Falha ao salvar", e);
		}
	}

	/**
	 * Método genérico que deleta a entidade do banco de dados.
	 * 
	 * @param entity
	 * @throws Exception
	 */
	protected void delete(E entity) throws Exception {
		try {
			Object remove;
			remove = manager.find(entity.getClass(), entity.getId());
			manager.remove(remove);
			manager.flush();
		} catch (Exception e) {
			throw new Exception("Falha ao Deletar", e);
		}
	}

	/**
	 * Método genérico que faz uma consulta no banco de acordo com a string
	 * passada como parâmetro, retorna somente uma entidade
	 * 
	 * @param sql
	 * @return getSingleResult
	 */
	@SuppressWarnings("unchecked")
	protected E getBySql(String sql) {
		return (E) manager.createQuery(sql).getSingleResult();
	}

	/**
	 * Método genérico que lista todos os registros do banco referente a classe
	 * passada como parâmetro
	 * 
	 * @param clazz
	 * @return
	 */
	protected List<E> listAll(Class<E> clazz) {
		CriteriaQuery<E> criteria = manager.getCriteriaBuilder().createQuery(clazz);

		return manager.createQuery(criteria.select(criteria.from(clazz))).getResultList();
	}

	/**
	 * Método genérico que busca um registro no banco de acordo com a classe e
	 * id passados como parâmetros.
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	protected E findById(Class<E> clazz, Object id) {
		return manager.find(clazz, id);
	}

	/**
	 * Método genérico que lista uma quantidade de registros do banco de acordo
	 * com a classe, primeiro item e limite de retorno de registros
	 * 
	 * @param clazz
	 * @param firstItem
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<E> listWithPageSize(Class<E> clazz, int firstItem, int pageSize) {
		String sql = "SELECT C FROM " + clazz.getName() + " C ";

		Query query = manager.createQuery(sql);
		query.setFirstResult(firstItem);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	/**
	 * Método genérico que lista registros do banco de dados de acordo com a
	 * query passada como parâmetro, retorna uma lista de registros
	 * 
	 * @param query
	 * @return getResultList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<E> listByQuery(String query) throws Exception {
		List<E> list = null;
		try {
			list = manager.createQuery(query).getResultList();
		} catch (Exception e) {
			throw new Exception("Falha ao listar metodo listByQuery: ", e);
		}
		return list;
	}

}