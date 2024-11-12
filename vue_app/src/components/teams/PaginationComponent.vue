<template>
    <el-pagination style="justify-content: end" 
    :current-page="props.currentPage"
    :page-size="props.pageSize"
    :page-sizes="[10, 20, 30, 40]" 
    :background="true" 
    layout="total, sizes, prev, pager, next, jumper"
    :total="props.total" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
</template>

<script lang="ts" setup>

interface IPagination {
    currentPage: number,
    pageSize: number,
    total: number
}
const props = defineProps<IPagination>()
const emit = defineEmits(['handle-size-change', 'handle-current-change'])

const handleSizeChange = (val: number) => {
    emit('handle-size-change', val)
}

const handleCurrentChange = (val: number) => {
    emit('handle-current-change', val)
}
</script>

<style scoped>
.buttonSearch {
    background-color: #337ecc;
    color: #FFFFFF;
}
</style>
