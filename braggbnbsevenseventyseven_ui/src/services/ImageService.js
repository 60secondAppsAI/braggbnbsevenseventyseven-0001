import http from "../http-common"; 

class ImageService {
  getAllImages(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/image/images`, searchDTO);
  }

  get(imageId) {
    return this.getRequest(`/image/${imageId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/image?field=${matchData}`, null);
  }

  addImage(data) {
    return http.post("/image/addImage", data);
  }

  update(data) {
  	return http.post("/image/updateImage", data);
  }
  
  uploadImage(data,imageId) {
  	return http.postForm("/image/uploadImage/"+imageId, data);
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

export default new ImageService();
