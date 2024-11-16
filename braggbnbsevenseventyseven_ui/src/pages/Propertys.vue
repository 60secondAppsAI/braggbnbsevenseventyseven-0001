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
            <property-table
            v-if="propertys && propertys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:propertys="propertys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-propertys="getAllPropertys"
             >

            </property-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import PropertyTable from "@/components/PropertyTable";
import PropertyService from "../services/PropertyService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PropertyTable,
  },
  data() {
    return {
      propertys: [],
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
    async getAllPropertys(sortBy='propertyId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PropertyService.getAllPropertys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.propertys.length) {
					this.propertys = response.data.propertys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching propertys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching property details:", error);
      }
    },
  },
  mounted() {
    this.getAllPropertys();
  },
  created() {
    this.$root.$on('searchQueryForPropertysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPropertys();
    })
  }
};
</script>
<style></style>
