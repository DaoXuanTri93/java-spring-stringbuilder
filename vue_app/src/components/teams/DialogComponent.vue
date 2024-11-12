<template>
    <el-dialog title="Notice" width="30%" destroy-on-close center>
        <span>
            {{ props.title }}
        </span>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="closeDialog">Cancel</el-button>
                <el-button type="danger" @click="deleteRow()">
                    Confirm
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>


<script lang="ts" setup>

const props = defineProps({
    title : {
        type: String
    }
})

const emit = defineEmits(['close-dialog', 'delete-row'])

const closeDialog = () => {
    emit('close-dialog')
}
const deleteRow = () => {
    emit('delete-row', false)
}
</script>