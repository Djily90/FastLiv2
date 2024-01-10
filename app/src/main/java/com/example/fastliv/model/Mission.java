package com.example.fastliv.model;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class Mission {
    private List<Livraison> livraisons;
    List<GeoPoint> itineaires = new ArrayList<>();

    public Mission() {

    }
    public Mission(List<Livraison> livraisons) {
        super();
        this.livraisons = livraisons;
    }

    public List<Livraison> getLivraisons() {
        return livraisons;
    }


    public void setLivraisons(Livraison livraison) {
        this.livraisons.add(livraison);
    }


    public List<GeoPoint> getItineaires() {
        return itineaires;
    }

    public void addItineaire() {
        for (Livraison liv: this.livraisons
        ) {
            //this.itineaires.add(liv.getLatLong());
        }
    }





}
