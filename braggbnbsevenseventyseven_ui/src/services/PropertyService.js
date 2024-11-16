import http from "../http-common"; 

class PropertyService {
  getAllPropertys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/property/propertys`, searchDTO);
  }

  get(propertyId) {
    return this.getRequest(`/property/${propertyId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/property?field=${matchData}`, null);
  }

  addProperty(data) {
    return http.post("/property/addProperty", data);
  }

  update(data) {
  	return http.post("/property/updateProperty", data);
  }
  
  uploadImage(data,propertyId) {
  	return http.postForm("/property/uploadImage/"+propertyId, data);
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

export default new PropertyService();
