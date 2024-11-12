<script setup lang="ts" name="CButton">
import { computed } from 'vue'

/**
 * Represents the properties for the button component.
 *
 * @property {any} text - The text content of the button.
 * @property {boolean} isTiny - Determines if the button should be rendered as a tiny button.
 * @property {string} title - The title attribute of the button.
 * @property {string} className - The CSS class name for the button.
 * @property {boolean} disabled - Determines if the button is disabled.
 * @property {boolean} isLoading - Determines if the button is in a loading state.
 * @property {() => any} click - The event handler for the button's click event.
 * @property {(event: any) => Promise<void>} onPaste - The event handler for the button's paste event.
 * @property {string} id - The id attribute of the button.
 * @property {'button' | 'submit' | 'reset'} type - The type attribute of the button.
 */
interface Props {
  text?: any
  isTiny?: boolean
  title?: string
  className?: string
  disabled?: boolean
  isLoading?: boolean
  click?: () => any // Event action api ==> Not required
  onPaste?: (event: any) => Promise<void>
  id?: string
  type?: 'button' | 'submit' | 'reset'
}

const {
  isTiny = false,
  isLoading = false,
  className = '',
  disabled,
  title,
  text = '',
  type = 'button',
} = defineProps<Props>()

const classButton = computed(() => [
  'btn',
  className,
  {
    'h-8 px-3': !isTiny,
    'h-6 px-2': isTiny,
  },
])

interface FilterEmits {
  (e: 'click'): void
  (e: 'paste'): void
}

const emit = defineEmits<FilterEmits>()
</script>

<template>
  <button
    :type="type"
    :disabled="disabled || isLoading"
    :title="title ?? text"
    :class="classButton"
    @click="emit('click')"
    @paste="emit('paste')"
  >
    <div v-if="$slots.default && !isLoading">
      <slot />
    </div>
    <div v-if="isLoading" class="loading-indicator">Loading…</div>
    <span v-if="!isLoading">{{ text }}</span>
  </button>
</template>

<style scoped>
.loading-indicator {
  display: inline-block;
  font-size: 0.875rem;
  color: #555;
  /* Thêm hiệu ứng xoay tùy chọn nếu cần */
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
