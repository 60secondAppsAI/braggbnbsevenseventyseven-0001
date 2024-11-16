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
            <guide-table
            v-if="guides && guides.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:guides="guides"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-guides="getAllGuides"
             >

            </guide-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import GuideTable from "@/components/GuideTable";
import GuideService from "../services/GuideService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    GuideTable,
  },
  data() {
    return {
      guides: [],
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
    async getAllGuides(sortBy='guideId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await GuideService.getAllGuides(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.guides.length) {
					this.guides = response.data.guides;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching guides:", error);
        }
        
      } catch (error) {
        console.error("Error fetching guide details:", error);
      }
    },
  },
  mounted() {
    this.getAllGuides();
  },
  created() {
    this.$root.$on('searchQueryForGuidesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllGuides();
    })
  }
};
</script>
<style></style>
