<template>
  <div id="app">
    <h1>Teams List</h1>
    <TeamsMaster  />
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import TeamsMaster  from '@/components/TeamsMaster.vue';

  export default defineComponent({
    name: 'TeamsView',
    components: {
      TeamsMaster
    }
  });
</script>
