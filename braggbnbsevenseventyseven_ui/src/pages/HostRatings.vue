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
            <hostRating-table
            v-if="hostRatings && hostRatings.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:hostRatings="hostRatings"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-host-ratings="getAllHostRatings"
             >

            </hostRating-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import HostRatingTable from "@/components/HostRatingTable";
import HostRatingService from "../services/HostRatingService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    HostRatingTable,
  },
  data() {
    return {
      hostRatings: [],
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
    async getAllHostRatings(sortBy='hostRatingId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await HostRatingService.getAllHostRatings(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.hostRatings.length) {
					this.hostRatings = response.data.hostRatings;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching hostRatings:", error);
        }
        
      } catch (error) {
        console.error("Error fetching hostRating details:", error);
      }
    },
  },
  mounted() {
    this.getAllHostRatings();
  },
  created() {
    this.$root.$on('searchQueryForHostRatingsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllHostRatings();
    })
  }
};
</script>
<style></style>
