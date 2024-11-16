<template>
  <a-layout style="min-height: 100vh">
    <!-- Header -->
    <a-layout-header style="background: #fff; padding: 0">
      <app-header />
    </a-layout-header>

    <!-- Main layout with sider and content -->
    <a-layout>
      <!-- Collapsible Sider with Controlled Open Keys for Nested Menu -->
      <a-layout-sider width="200px" collapsible style="background: #fff">
        <a-menu
          mode="inline"
          :openKeys="openKeys"
          @openChange="onOpenChange"
          style="height: 100%; border-right: 0"
        >
<!--          <a-sub-menu key="sub1" title="Menu 1">
            <a-menu-item key="1">Option 1</a-menu-item>
            <a-menu-item key="2">Option 2</a-menu-item>
            <a-sub-menu key="sub1-1" title="Nested Menu">
              <a-menu-item key="3">Option 3</a-menu-item>
              <a-menu-item key="4">Option 4</a-menu-item>
            </a-sub-menu>
          </a-sub-menu>
          <a-sub-menu key="sub2" title="Menu 2">
            <a-menu-item key="5">Option 5</a-menu-item>
            <a-menu-item key="6">Option 6</a-menu-item>
          </a-sub-menu>
-->>

	 
			
				<a-menu-item>
					<router-link to="/propertys">
						<span class="label">Propertys</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/users">
						<span class="label">Users</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/bookings">
						<span class="label">Bookings</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/reviews">
						<span class="label">Reviews</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/payments">
						<span class="label">Payments</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/amenitys">
						<span class="label">Amenitys</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/hosts">
						<span class="label">Hosts</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/guests">
						<span class="label">Guests</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/listings">
						<span class="label">Listings</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/messages">
						<span class="label">Messages</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/images">
						<span class="label">Images</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/offers">
						<span class="label">Offers</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/wishlists">
						<span class="label">Wishlists</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/feedbacks">
						<span class="label">Feedbacks</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/notifications">
						<span class="label">Notifications</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/complaints">
						<span class="label">Complaints</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/transactions">
						<span class="label">Transactions</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/supportTickets">
						<span class="label">Support Tickets</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/hostRatings">
						<span class="label">Host Ratings</span>
					</router-link>
				</a-menu-item>
		
				<a-menu-item>
					<router-link to="/guides">
						<span class="label">Guides</span>
					</router-link>
				</a-menu-item>
		
	
        </a-menu>
      </a-layout-sider>
      
      <!-- Content area -->
      <a-layout-content style="padding: 16px">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
import { ref } from "vue";
import AppHeader from "@/components/AppHeader.vue";

export default {
  name: "DefaultLayout",
  components: {
    AppHeader,
  },
  setup() {
    const openKeys = ref(["sub1"]); // default open submenu
    const onOpenChange = (keys) => {
      // Open only the last clicked submenu, for single open behavior
      openKeys.value = keys.length ? [keys[keys.length - 1]] : [];
    };

    return {
      openKeys,
      onOpenChange,
    };
  },
};
</script>
<style>
.ant-layout {
    margin-top: 20px;
}
</style>

