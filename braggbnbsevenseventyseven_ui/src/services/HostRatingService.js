import http from "../http-common"; 

class HostRatingService {
  getAllHostRatings(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/hostRating/hostRatings`, searchDTO);
  }

  get(hostRatingId) {
    return this.getRequest(`/hostRating/${hostRatingId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/hostRating?field=${matchData}`, null);
  }

  addHostRating(data) {
    return http.post("/hostRating/addHostRating", data);
  }

  update(data) {
  	return http.post("/hostRating/updateHostRating", data);
  }
  
  uploadImage(data,hostRatingId) {
  	return http.postForm("/hostRating/uploadImage/"+hostRatingId, data);
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

export default new HostRatingService();
