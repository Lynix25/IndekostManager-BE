package com.indekos.services;

import com.indekos.common.helper.exception.InvalidUserCredentialException;
import com.indekos.dto.request.CreateMasterCategory;
import com.indekos.model.MasterService;
import com.indekos.repository.MasterServiceRepository;
import com.indekos.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;

@org.springframework.stereotype.Service
public class MasterServiceService {

    @Autowired
    MasterServiceRepository masterServiceRepository;

    public MasterService register(CreateMasterCategory createMasterCategory){
        MasterService masterService = new MasterService();

        // Reqeust input define
        masterService.setId(Utils.UUID4());
        masterService.setServiceName(createMasterCategory.getServiceName());
        masterService.setVariant(createMasterCategory.getVariant());
        masterService.setPrice(createMasterCategory.getPrice());

        masterServiceRepository.save(masterService);
        return masterService;
    }

    public List<MasterService> getAll(){
        return masterServiceRepository.findAll();
    }

    public MasterService getByID(String id){
        try {
            return masterServiceRepository.findById(id).get();
        }catch (NoSuchElementException e){
            throw new InvalidUserCredentialException("Invalid ID");
        }
    }
}
