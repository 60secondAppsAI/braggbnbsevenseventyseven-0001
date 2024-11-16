import http from "../http-common"; 

class AmenityService {
  getAllAmenitys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/amenity/amenitys`, searchDTO);
  }

  get(amenityId) {
    return this.getRequest(`/amenity/${amenityId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/amenity?field=${matchData}`, null);
  }

  addAmenity(data) {
    return http.post("/amenity/addAmenity", data);
  }

  update(data) {
  	return http.post("/amenity/updateAmenity", data);
  }
  
  uploadImage(data,amenityId) {
  	return http.postForm("/amenity/uploadImage/"+amenityId, data);
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

export default new AmenityService();
