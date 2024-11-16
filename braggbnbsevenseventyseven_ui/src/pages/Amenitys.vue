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
            <amenity-table
            v-if="amenitys && amenitys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:amenitys="amenitys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-amenitys="getAllAmenitys"
             >

            </amenity-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import AmenityTable from "@/components/AmenityTable";
import AmenityService from "../services/AmenityService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    AmenityTable,
  },
  data() {
    return {
      amenitys: [],
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
    async getAllAmenitys(sortBy='amenityId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await AmenityService.getAllAmenitys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.amenitys.length) {
					this.amenitys = response.data.amenitys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching amenitys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching amenity details:", error);
      }
    },
  },
  mounted() {
    this.getAllAmenitys();
  },
  created() {
    this.$root.$on('searchQueryForAmenitysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllAmenitys();
    })
  }
};
</script>
<style></style>
