<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <image-table
            v-if="images && images.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:images="images"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-images="getAllImages"
             >

            </image-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ImageTable from "@/components/ImageTable";
import ImageService from "../services/ImageService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ImageTable,
  },
  data() {
    return {
      images: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllImages(sortBy='imageId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ImageService.getAllImages(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.images.length) {
					this.images = response.data.images;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching images:", error);
        }
        
      } catch (error) {
        console.error("Error fetching image details:", error);
      }
    },
  },
  mounted() {
    this.getAllImages();
  },
  created() {
    this.$root.$on('searchQueryForImagesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllImages();
    })
  }
};
</script>
<style></style>
