package com.sellanding.ledger_system.domain;

import java.util.ArrayList;
import java.util.List;

import com.sellanding.ledger_system.domain.enums.Sector;

import jakarta.persistence.*;

@Entity
public class Category {
    
    @Id @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    private Sector sector;

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
        joinColumns = @JoinColumn(name = "CATEGORY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Asset> assets = new ArrayList<Asset>();

    
}
