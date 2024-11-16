import http from "../http-common"; 

class GuestService {
  getAllGuests(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/guest/guests`, searchDTO);
  }

  get(guestId) {
    return this.getRequest(`/guest/${guestId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/guest?field=${matchData}`, null);
  }

  addGuest(data) {
    return http.post("/guest/addGuest", data);
  }

  update(data) {
  	return http.post("/guest/updateGuest", data);
  }
  
  uploadImage(data,guestId) {
  	return http.postForm("/guest/uploadImage/"+guestId, data);
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

export default new GuestService();
