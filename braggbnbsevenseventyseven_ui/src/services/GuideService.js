import http from "../http-common"; 

class GuideService {
  getAllGuides(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/guide/guides`, searchDTO);
  }

  get(guideId) {
    return this.getRequest(`/guide/${guideId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/guide?field=${matchData}`, null);
  }

  addGuide(data) {
    return http.post("/guide/addGuide", data);
  }

  update(data) {
  	return http.post("/guide/updateGuide", data);
  }
  
  uploadImage(data,guideId) {
  	return http.postForm("/guide/uploadImage/"+guideId, data);
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

export default new GuideService();
