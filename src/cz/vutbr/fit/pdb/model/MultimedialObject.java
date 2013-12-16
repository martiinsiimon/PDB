/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.vutbr.fit.pdb.model;

import oracle.ord.im.OrdImage;
import oracle.ord.im.OrdImageSignature;

/**
 * Multimedialni objekt. Drzi obrazek, jeho signatury a prislusne metody
 *
 * @author Martin Simon
 */
public class MultimedialObject {

    private OrdImage image;
    private OrdImageSignature signature;

    MultimedialObject() {
        this.image = null;
        this.signature = null;
    }

    MultimedialObject(OrdImage img, OrdImageSignature sig) {
        this.image = img;
        this.signature = sig;
    }

    public OrdImage getImage() {
        return this.image;
    }

    public void setImage(OrdImage img) {
        this.image = img;
    }

    public OrdImageSignature getSignature() {
        return this.signature;
    }

    public void setSignature(OrdImageSignature sig) {
        this.signature = sig;
    }
}
