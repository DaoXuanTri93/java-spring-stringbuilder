<template>
  <label class="el-form-item__label" :for="forEl">{{ text }}</label>
</template>
<script setup lang="ts">
interface LabelProps {
  text: string;
  forEl?: string;
}

defineProps<LabelProps>()
</script>
<style scoped>
</style>