package com.miage.ejb.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.miage.dao.IArticleDAO;
import com.miage.dao.IUtilisateurDAO;
import com.miage.ejb.IUtilisateurServiceRemote;
import com.miage.ejb.exception.UserAlreadyExistException;
import com.miage.ejb.exception.UserBadLoginException;
import com.miage.entity.Article;
import com.miage.entity.User;

/**
 * @author tarik
 * Session Bean implementation class IUtilisateurService
 */
@Stateless(mappedName="UtilisateurService")
public class UtilisateurService implements IUtilisateurServiceRemote {
	
	@EJB 
	private IUtilisateurDAO utilisateurDAO;
	
	@EJB 
	private IArticleDAO articleDAO;	

	@Override
	public User inscrire(String nom, String adresse, String mail, String password) 
			throws UserAlreadyExistException {
		
		if(utilisateurDAO.findById(nom) != null){
			throw new UserAlreadyExistException(nom);
		}
		
		User user = new User();
		user.setNom(nom);
		user.setAdressePostale(adresse);
		user.setAdresseElectronique(mail);
		user.setMotDePasse(password);
		
		return utilisateurDAO.save(user);
	}

	@Override
	public User identifier(String nom, String password)
			throws UserBadLoginException {
		
		User utilisateur = utilisateurDAO.findById(nom);
		
		if( utilisateur == null || !utilisateur.getMotDePasse().equals(password)){
			throw new UserBadLoginException(nom, password);
		}
		
		return utilisateur;
	}

	@Override
	public void surrencherir(String nomAcheteur, Long idArticle, Float montantDerniereEnchere) {
		User acheteur = utilisateurDAO.findById(nomAcheteur);
		Article article = articleDAO.findById(idArticle);
		
		
		article.setMontantDerniereEnchere(montantDerniereEnchere);
		article.setAcheteur(acheteur);
		articleDAO.save(article);
	}

}
