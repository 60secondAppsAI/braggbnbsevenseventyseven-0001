import http from "../http-common"; 

class HostService {
  getAllHosts(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/host/hosts`, searchDTO);
  }

  get(hostId) {
    return this.getRequest(`/host/${hostId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/host?field=${matchData}`, null);
  }

  addHost(data) {
    return http.post("/host/addHost", data);
  }

  update(data) {
  	return http.post("/host/updateHost", data);
  }
  
  uploadImage(data,hostId) {
  	return http.postForm("/host/uploadImage/"+hostId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new HostService();
