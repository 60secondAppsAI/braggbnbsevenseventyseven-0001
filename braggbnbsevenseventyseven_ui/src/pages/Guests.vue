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
            <guest-table
            v-if="guests && guests.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:guests="guests"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-guests="getAllGuests"
             >

            </guest-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import GuestTable from "@/components/GuestTable";
import GuestService from "../services/GuestService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    GuestTable,
  },
  data() {
    return {
      guests: [],
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
    async getAllGuests(sortBy='guestId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await GuestService.getAllGuests(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.guests.length) {
					this.guests = response.data.guests;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching guests:", error);
        }
        
      } catch (error) {
        console.error("Error fetching guest details:", error);
      }
    },
  },
  mounted() {
    this.getAllGuests();
  },
  created() {
    this.$root.$on('searchQueryForGuestsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllGuests();
    })
  }
};
</script>
<style></style>
