<template>
  <!-- ============ Table ============ -->
  <div class="container mx-auto pt-18 px-2">
    
    <!-- table -->
    <table class="table-fixed cursor-pointer" >
      <caption class="table-title font-bold text-gray-700  pb-2">Cryptocurrency and US Stock Exchange (USD) </caption>
      <!-- head -->
      <thead>
        <tr class="relative text-left text-gray-600 text-sm" >
          <td class="p-2"> 
            Search: 
            <input type="text" placeholder="Product Name ..." class="border border-gray-500 rounded p-2"
              v-model="search"
            />
          </td>          
        </tr>
        <tr class="text-left bg-gray-100 text-gray-600 text-sm">
          <th class="w-1/6 p-4">Product Name</th>
          <th class="w-1/6">Product Type</th>
          <th class="w-1/6">Market Price(Real Time)</th>
          <th class="w-1/6">24 Hours % Change</th>
          <!-- <th class="w-1/6 hidden sm:table-cell">24 Hours High</th>
          <th class="w-1/6 hidden sm:table-cell">24 Hours Low</th> -->
          <th class="w-1/6 hidden sm:table-cell">Market Capitalization</th>
        </tr>
      </thead>
      
      <!-- body -->
      <tbody class="divide-y" :key="matchedNames">
        <!-- 1 -->
        <tr class="text-sm hover:bg-gray-100 transition duration-300"
         v-for="product in matchedNames" :key="product.name"
        >
          <td class="p-4 flex items-center">
            <!-- <img
              :src="product.logo"
              alt="product logo"
              class="w-7 h-7 rounded-full mr-1"
            /> -->
            <p class="font-bold p-1 mr-1">{{product.name}}</p>
            <p class="uppercase text-gray-500 hidden sm:table-cell">
              {{product.productId}}
            </p>
          </td>
          <td class=" font-bold">
            <p class="mr-2">{{product.productType}}</p>
          </td>
          <td class="font-bold text-gray-600">
            ${{ $filters.comma_separator(product.currentPrice) }}
          </td>
          <td class=" font-bold">
            <div class="text-red-500" v-if="$filters.price_negative(product.priceChangePct)">
              <fa icon="caret-down" class="mr-1" />{{product.priceChangePct}}%
            </div>
            <div v-else class="text-green-500" >
              <fa icon="caret-up" class="mr-1" />{{product.priceChangePct}}%
            </div>
          </td>
          <!-- <td class="hidden sm:table-cell">
            <p style="color:rgb(26, 137, 165)">${{ $filters.comma_separator(product.high_24h) }} </p>
          </td>
          <td class="pr-10 hidden sm:table-cell">
            <p style="color:rgb(26, 137, 165)">${{ $filters.comma_separator(product.low_24h) }} </p>
          </td> -->
          <td class="pr-10 hidden sm:table-cell">
            <p style="color:rgb(26, 137, 165)">${{ $filters.comma_separator(product.marketCap) }} </p>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import "./assets/tailwind.css";
import { computed, ref, watchEffect } from "vue";

import axios from 'axios'

export default {
  name: "App",
  setup() {
    const products = ref([]);
    const search = ref('')
    // api call
    const retrieveProducts = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8092/data/api/v1/products"
        );
        products.value = response.data.data; // ApiResponse's data object reponse.data
        console.log(products.value);
      } catch (err) {
        console.log(err);
      }
    };
    // fetch timer, invoke backend service in every 2 seconds 
    setInterval(() => {
      retrieveProducts();
    }, 2000);
    // search and sort
    const matchedNames = computed(() => {
      const lowercaseSearch = search.value.toLowerCase(); // Convert search value to lowercase

      if (lowercaseSearch === '') {
        return products.value; // Return all products when search value is empty
      }

      const matchedProducts = new Set(); // Use a Set to store matched products

      products.value.forEach((product) => {
        if (product.name.toLowerCase().includes(lowercaseSearch)) {
          matchedProducts.add(product); // Add matched products to the Set
        }
      });

      const sortedProducts = Array.from(matchedProducts).sort((a, b) => b.cap - a.cap); // Sort matched products by cap size in descending order

      return sortedProducts;
    });

    return { matchedNames, search };
  },
};
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
.table-title {
  font-size : 30px;
  bottom: 30;
  height: 1.5em;
}
</style>
