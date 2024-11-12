<template>
  <div id="app">
    <h1>Product List</h1>
    <ProductMaster  />
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import ProductMaster  from '@/components/ProductMaster.vue';

  export default defineComponent({
    name: 'App',
    components: {
      ProductMaster 
    }
  });
</script>
