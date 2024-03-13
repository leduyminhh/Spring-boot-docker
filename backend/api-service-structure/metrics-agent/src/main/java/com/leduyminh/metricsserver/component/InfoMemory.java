package com.leduyminh.metricsserver.component;

import com.leduyminh.metricsserver.service.inf.CommandLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/*Operation	HTTP method
	@ReadOperation   	GET
	
	@WriteOperation  	POST
	
	@DeleteOperation 	DELETE
*/
@Component
@Endpoint(id = "memory")
public class InfoMemory {
	
	@Autowired
    CommandLineService commandLineService;

	@ReadOperation
	public ResponseEntity<String> getMemory() {
		return ResponseEntity.ok(commandLineService.getMemory().toString());
	}

}