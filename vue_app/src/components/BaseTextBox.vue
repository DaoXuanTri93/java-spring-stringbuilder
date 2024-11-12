<template>
  <el-input
    v-model="internalValue"
    :type="isTextArea ? 'textarea' : 'text'"
    :rows="rows"
    style="width: 220px"
    :id="id"
  />
</template>
<script setup lang="ts">
import { ref, watch } from 'vue'
interface TextBoxProps {
  modelValue: number | string | null;
  isTextArea?: boolean;
  rows?: number;
  id?: string;
}

const props = defineProps<TextBoxProps>();
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
