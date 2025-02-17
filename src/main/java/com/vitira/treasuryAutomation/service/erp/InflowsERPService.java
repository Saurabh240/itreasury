package com.vitira.treasuryAutomation.service.erp;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.client.api.domain.ClientEntity;
import org.springframework.stereotype.Service;

import com.vitira.treasuryAutomation.dto.InflowsRequest;
import com.vitira.treasuryAutomation.dto.InflowsResponse;
import com.vitira.treasuryAutomation.entity.InflowsEntity;
import com.vitira.treasuryAutomation.helpers.ODataConverter;

@Service
public class InflowsERPService extends ERPService {
	private final String ES_INFLOWS_NAME = "Inflows";
	
	public List<InflowsResponse> getAllInflows() {
		List<ClientEntity> entities = erpClientApp.getEntities(ES_INFLOWS_NAME);
        
        List<InflowsResponse> result = new ArrayList<>();
        for(ClientEntity ce : entities) {
        	ODataConverter<InflowsEntity> odataConverter = new ODataConverter<>(InflowsEntity.class);
        	try {
				InflowsEntity entity = odataConverter.clientEntityToPojo(ce);
				InflowsResponse response = mapModelToDto(entity);
				result.add(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        return result;
    }
	
	public InflowsResponse getInflowsById(InflowsRequest req) {
        ClientEntity ce = erpClientApp.readEntity(ES_INFLOWS_NAME, req.getId());
        InflowsResponse result = null;
    	ODataConverter<InflowsEntity> odataConverter = new ODataConverter<>(InflowsEntity.class);
    	try {
			InflowsEntity entity = odataConverter.clientEntityToPojo(ce);
			result = mapModelToDto(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return result;
    }

//    public InflowsEntity saveInflows(InflowsEntity inflowsEntity) {
//        return inflowsRepository.save(inflowsEntity);
//    }
//
//    public InflowsEntity getInflowsById(Long id) {
//        return inflowsRepository.findById(id).orElse(null);
//    }
//
//    public void deleteInflowsById(Long id) {
//        inflowsRepository.deleteById(id);
//    }
	
	private InflowsResponse mapModelToDto(InflowsEntity entity) {
    	
		InflowsResponse response = new InflowsResponse(
				entity.getId(), 
				entity.getDatetime(), 
				entity.getInflowsData());
    	
    	return response;
    }
	
}
