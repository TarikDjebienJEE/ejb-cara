package com.miage.ejb.impl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.miage.dao.IArticleDAO;
import com.miage.dao.IUtilisateurDAO;
import com.miage.ejb.IArticleServiceRemote;
import com.miage.entity.Article;
import com.miage.entity.User;

/**
 * @author RAKOTOBE Sitraka Eric
 */
@Stateless(mappedName="ArticleService")
public class ArticleService implements IArticleServiceRemote{

	@EJB
	private IArticleDAO articleDAO ;
	
	@EJB
	private IUtilisateurDAO utilisateurDAO ;
	

	@Override
	public Article ajouterArticle(String nomVendeur, String description, Float prixVente) {
		Article article = new Article() ; 
		article.setDescription(description);
		article.setPrixVente(prixVente);
		
		User utilisateur =  utilisateurDAO.findById(nomVendeur);
		article.setVendeur(utilisateur);
		
		Collection<Article> articlesVendues = utilisateur.getArticlesVendues() ; 
		if (articlesVendues != null && ! articlesVendues.isEmpty()){
			articlesVendues.add(article);
		}else{
			articlesVendues = new ArrayList<Article>();
			articlesVendues.add(article);
		}
		
		utilisateur.setArticlesVendues(articlesVendues);
		
		utilisateurDAO.save(utilisateur);
		return articleDAO.save(article) ; 
	}


	@Override
	public List<Article> getAllArticlesEnVente() {
		List<Article> articles = articleDAO.findAll();
		List<Article> result = new ArrayList<Article>();
		for (Article article : articles) {
			if (article.getVendeur() == null){
				result.add(article);
			}
		}
		return result ; 
	} 
	
	
	
	
}
