<template>
  <el-select v-model="internalValue" placeholder="" style="width: 220px" :id="id">
    <el-option
      v-for="item in items"
      :key="item.value"
      :label="item.label"
      :value="item.value"
    />
  </el-select>
</template>
<script setup lang="ts">
import { SelectItem } from '@/interfaces/SelectItem'
import { ref, watch } from 'vue'
interface DropdownProps {
  items: SelectItem[];
  modelValue: number | string | null;
  id?: string;
}

const props = defineProps<DropdownProps>();
const internalValue = ref(props.modelValue)
const emit = defineEmits<{
  (e: 'update:modelValue', value: number | string | null): void
}>()

watch(internalValue, newValue => {
  emit('update:modelValue', newValue)
})

watch(
  () => props.modelValue,
  newValue => {
    internalValue.value = newValue
  },
)
</script>
<style scoped></style>
