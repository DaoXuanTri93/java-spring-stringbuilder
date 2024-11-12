<template>
  <el-pagination
    size="small"
    background
    @current-change="onCurrentChange"
    :current-page="internalCurrentPage"
    :page-size="pageSize"
    :total="totalElements"
    layout="total, prev, pager, next, jumper"
    style="--el-color-primary: rgb(49, 114, 200)"
    class="justify-content-end"
  />
</template>
<script setup lang="ts">
import { ref, watch } from 'vue'
interface PaginationProps {
  currentPage: number
  pageSize: number
  totalElements: number
}

const props = defineProps<PaginationProps>()
const emit = defineEmits<{
  (e: 'update:currentPage', newPage: number): void;
}>()
const internalCurrentPage = ref(props.currentPage)

watch(
  () => props.currentPage,
  (newPage) => {
    internalCurrentPage.value = newPage
  }
)

function onCurrentChange(newPage: number) {
  internalCurrentPage.value = newPage
  emit('update:currentPage', newPage)
}
</script>