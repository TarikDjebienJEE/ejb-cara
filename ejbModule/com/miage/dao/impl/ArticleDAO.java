package com.miage.dao.impl;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.miage.dao.IArticleDAO;
import com.miage.entity.Article;

/**
 * Classe DAO pour les articles
 * @author tarik
 * @author Eric
 */
@Stateful
public class ArticleDAO implements IArticleDAO{
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public Article findById(final Long id) {
		final Article result = entityManager.find(Article.class, id);
		return result;
	}

	@Override
	public void delete(Article entity) {
		Article attached = entityManager.merge(entity);
		entityManager.remove(attached);
	}

	@Override
	public Article save(Article entity) {
		final Article savedArticlentity = entityManager.merge(entity);
		return savedArticlentity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Article> findAll() {
		return entityManager
				.createQuery("select a from Article a")
				.getResultList();
	}

	@Override
	public int countAll() {
		return findAll().size() ; 
	}
}
