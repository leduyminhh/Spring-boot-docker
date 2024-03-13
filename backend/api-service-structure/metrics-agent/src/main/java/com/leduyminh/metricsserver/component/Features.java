package com.leduyminh.metricsserver.component;

import com.leduyminh.metricsserver.service.inf.ServerInfoService;

/*Operation	HTTP method
	@ReadOperation   	GET
	
	@WriteOperation  	POST
	
	@DeleteOperation 	DELETE
*/
@Component
@Endpoint(id = "server-information")
public class Features {

	@Autowired
    ServerInfoService serverInfoService;
	
	@ReadOperation
	public ResponseEntity<Object> updateInfoFeatures() {
		JSONObject updateServerInfor = serverInfoService.updateServerInfo();
		
		if (updateServerInfor.isNull("Exception")) {
			DataResponse response = DataResponse.ok().withMessage("Lấy thông tin thành công!").withResult(updateServerInfor.toString()).build();
			return ResponseEntity.ok().body(response);
		} else {
			DataResponse response = DataResponse.withCode(500).withMessage("Lấy thông tin thất bại!").withResult(updateServerInfor.toString()).build();
			return ResponseEntity.internalServerError().body(response);
		}
	}
}
