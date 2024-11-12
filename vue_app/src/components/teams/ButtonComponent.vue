<template>
    <el-button class="buttonSearch" type="primary" @Click="handleSearch"> {{ props.title }}</el-button>
</template>

<script lang="ts" setup>

const props = defineProps({
    title: String
})

const emit = defineEmits(['hanlde-search-team'])
const handleSearch = () => {
    emit('hanlde-search-team')
}
</script>

<style scoped>
.buttonSearch {
    background-color: #337ecc;
    color: #FFFFFF;
}
</style>
