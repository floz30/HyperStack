package fr.uge.hyperstack.model;


/**
 * Représente un élément que l'utilisateur peut ajouter sur les calques de ses présentations.
 */
public interface Element {
    /**
     * Renvoie la position du coin haut gauche de l'élement sur l'axe des abscisses.
     *
     * @return la position du coin haut gauche sur l'axe des abscisses
     */
    float getPositionX();

    /**
     * Renvoie la position du coin haut gauche de l'élément sur l'axe des ordonnées.
     *
     * @return la position du coin haut gauche sur l'axe des ordonnées.
     */
    float getPositionY();

    /**
     * Renvoie la largeur de l'élément.
     *
     * @return la largeur de l'élément.
     */
    float getWidth();

    /**
     * Renvoie la hauteur de l'élément.
     *
     * @return la hauteur de l'élément.
     */
    float getHeight();


}
