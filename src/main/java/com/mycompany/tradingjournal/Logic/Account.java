
package com.mycompany.tradingjournal.Logic;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int ID;
    private String user, password;
    
    @OneToMany
    private ArrayList<Operation> operations = null;
    
    public Account(String user, String password) {
        this.user = user;
        this.password = password;
        operations = new ArrayList<>();
    }
}
