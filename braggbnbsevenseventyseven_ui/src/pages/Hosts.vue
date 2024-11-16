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
            <host-table
            v-if="hosts && hosts.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:hosts="hosts"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-hosts="getAllHosts"
             >

            </host-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import HostTable from "@/components/HostTable";
import HostService from "../services/HostService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    HostTable,
  },
  data() {
    return {
      hosts: [],
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
    async getAllHosts(sortBy='hostId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await HostService.getAllHosts(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.hosts.length) {
					this.hosts = response.data.hosts;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching hosts:", error);
        }
        
      } catch (error) {
        console.error("Error fetching host details:", error);
      }
    },
  },
  mounted() {
    this.getAllHosts();
  },
  created() {
    this.$root.$on('searchQueryForHostsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllHosts();
    })
  }
};
</script>
<style></style>
