package com.vitira.treasuryAutomation.service.erp;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.client.api.domain.ClientEntity;
import org.springframework.stereotype.Service;

import com.vitira.treasuryAutomation.dto.OutflowsRequest;
import com.vitira.treasuryAutomation.dto.OutflowsResponse;
import com.vitira.treasuryAutomation.entity.OutflowsEntity;
import com.vitira.treasuryAutomation.helpers.ODataConverter;

@Service
public class OutflowsERPService extends ERPService {
	private final String ES_OUTFLOWS_NAME = "Outflows";
	
	public List<OutflowsResponse> getAllOutflows() {
		List<ClientEntity> entities = erpClientApp.getEntities(ES_OUTFLOWS_NAME);
        
        List<OutflowsResponse> result = new ArrayList<>();
        for(ClientEntity ce : entities) {
        	ODataConverter<OutflowsEntity> odataConverter = new ODataConverter<>(OutflowsEntity.class);
        	try {
				OutflowsEntity entity = odataConverter.clientEntityToPojo(ce);
				OutflowsResponse response = mapModelToDto(entity);
				result.add(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        
        return result;
    }
	
	public OutflowsResponse getInflowsById(OutflowsRequest req) {
        ClientEntity ce = erpClientApp.readEntity(ES_OUTFLOWS_NAME, req.getId());
        OutflowsResponse result = null;
    	ODataConverter<OutflowsEntity> odataConverter = new ODataConverter<>(OutflowsEntity.class);
    	try {
			OutflowsEntity entity = odataConverter.clientEntityToPojo(ce);
			result = mapModelToDto(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return result;
    }

//    public OutflowsEntity saveInflows(OutflowsEntity inflowsEntity) {
//        return inflowsRepository.save(inflowsEntity);
//    }
//
//    public OutflowsEntity getInflowsById(Long id) {
//        return inflowsRepository.findById(id).orElse(null);
//    }
//
//    public void deleteInflowsById(Long id) {
//        inflowsRepository.deleteById(id);
//    }
	
	private OutflowsResponse mapModelToDto(OutflowsEntity entity) {
    	
		OutflowsResponse response = new OutflowsResponse(
				entity.getId(), 
				entity.getDatetime(), 
				entity.getOutflowsData());
    	
    	return response;
    }
	
}
