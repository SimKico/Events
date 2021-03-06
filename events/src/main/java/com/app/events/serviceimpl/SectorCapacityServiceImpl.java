package com.app.events.serviceimpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.repository.SectorCapacityRepository;
import com.app.events.service.SectorCapacityService;
import com.app.events.service.SectorService;

@Service
public class SectorCapacityServiceImpl implements SectorCapacityService {

    @Autowired
    private SectorCapacityRepository sectorCapacityRepository;

    @Autowired
    private SectorService sectorService;

    @Override
    public SectorCapacity findOne(Long id) throws ResourceNotFoundException{
        return this.sectorCapacityRepository.findById(id)
                    .orElseThrow(
                        ()-> new ResourceNotFoundException("Sector capacity")
                    ); 
    }

    @Override
    public SectorCapacity create(SectorCapacity sectorCapacity) throws Exception{
        sectorCapacity.setId(null);
        Sector sector = sectorService.findOne(sectorCapacity.getSector().getId());
        sectorCapacity.setSector(sector);
        sectorCapacity.setFree(sectorCapacity.getCapacity());
        return this.sectorCapacityRepository.save(sectorCapacity);
    }

    @Override
    public SectorCapacity update(SectorCapacity sectorCapacity) throws Exception {
        SectorCapacity sectorCapacityToUpdate = this.findOne(sectorCapacity.getId());
        sectorCapacityToUpdate.setFree(sectorCapacity.getFree());
        sectorCapacityToUpdate.setCapacity(sectorCapacity.getCapacity());
	    return this.sectorCapacityRepository.save(sectorCapacityToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.sectorCapacityRepository.deleteById(id);
    }
    
    @Override
	public Collection<SectorCapacity> findSectorCapacityBySectorId(Long id) {
		return sectorCapacityRepository.findBySectorId(id);

	}
}
