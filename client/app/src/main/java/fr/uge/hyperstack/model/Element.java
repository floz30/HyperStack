package fr.uge.hyperstack.model;

import android.view.View;

/**
 * Représente un élément que l'utilisateur peut ajouter sur les calques de ses présentations.
 */
public interface Element {
    /**
     * Renvoie la position du coin haut gauche de l'élement sur l'axe des abscisses.
     *
     * @return la position du coin haut gauche sur l'axe des abscisses
     */
    int getPositionX();

    /**
     * Renvoie la position du coin haut gauche de l'élément sur l'axe des ordonnées.
     *
     * @return la position du coin haut gauche sur l'axe des ordonnées.
     */
    int getPositionY();

    /**
     * Renvoie la largeur de l'élément.
     *
     * @return la largeur de l'élément.
     */
    int getWidth();

    /**
     * Renvoie la hauteur de l'élément.
     *
     * @return la hauteur de l'élément.
     */
    int getHeight();

    /**
     * Renvoie le composant de l'interface graphique.
     *
     * @return le composant de l'interface graphique.
     */
    View getContent();
}
