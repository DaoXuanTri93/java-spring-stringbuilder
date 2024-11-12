<template>
        <el-header class="centered-header" :class="{ 'custom-align-left': props.styleDemo }">
            {{ props.title }}
        </el-header>
</template>

<script lang="ts" setup>

const props = defineProps({
    title: String,
    styleDemo: String
})

</script>

<style scoped>
.centered-header {
    background-color: #337ecc;
    color: #FFFFFF;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
    height: 40px;
    font-size: 15px;
    margin: 20px 0;
}

.custom-align-left {
    justify-content: left;
}
</style>
