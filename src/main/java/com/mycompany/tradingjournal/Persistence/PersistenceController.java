
package com.mycompany.tradingjournal.Persistence;

import com.mycompany.tradingjournal.Logic.Operation;
import java.util.List;

public class PersistenceController {
    private OperationRepository opRepo = null;
    
    public PersistenceController() {
        opRepo = new OperationRepository();
    }
    
    public void save(Operation op) {
        opRepo.save(op);
    }
    
    public List<Operation> getAll() {
        return opRepo.findAll();
    }
    
    public void delete(int id) {
        opRepo.delete(id);
    }
}
