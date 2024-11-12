// src/facades/RoleFacade.ts
import { ref, reactive, computed } from 'vue'
import axios from 'axios'
import { ElMessageBox } from 'element-plus'

interface Role {
  id?: string | null
  roleName: string
  description: string
}

export function useRoleFacade() {
  const roles = ref([])
  const role = reactive({
    id: null,
    roleName: '',
    description: '',
  })
  const initialRole = reactive({
    id: null,
    roleName: '',
    description: '',
  })
  const search = reactive({
    roleId: '',
    roleName: '',
  })
  const isRoleSelected = ref(false)
  const currentPage = ref(1)
  const pageSize = ref(10)

  const paginatedRoles = computed(() => {
    const start = (currentPage.value - 1) * pageSize.value
    const end = start + pageSize.value
    return roles.value.slice(start, end)
  })

  function fetchRoles() {
    const params = new URLSearchParams()
    if (search.roleId) params.append('id', search.roleId)
    if (search.roleName) params.append('roleName', search.roleName)

    axios
      .get(`http://localhost:8081/api/roles/search?${params.toString()}`)
      .then(response => {
        roles.value = response.data
      })
      .catch(error => {
        console.error('Error fetching roles:', error)
      })
  }

  function handlePageChange(page: number) {
    currentPage.value = page
  }

  function clearSearch() {
    if (search.roleId || search.roleName || roles.value.length > 0) {
      ElMessageBox.confirm('Are you sure you want to clear?', 'Confirmation', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning',
      })
        .then(() => {
          search.roleId = ''
          search.roleName = ''
          roles.value = []
          currentPage.value = 1
        })
        .catch(() => {})
    } else {
      search.roleId = ''
      search.roleName = ''
      roles.value = []
      currentPage.value = 1
    }
  }

  function cancelRole() {
    if (!role.roleName.trim() && !role.description.trim()) {
      role.roleName = ''
      role.description = ''
      isRoleSelected.value = false
      return
    }

    if (
      role.roleName !== initialRole.roleName ||
      role.description !== initialRole.description
    ) {
      ElMessageBox.confirm(
        'There have been changes, do you want to delete them?',
        'Confirm',
        {
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
          type: 'warning',
        },
      ).then(() => {
        resetFields()
        isRoleSelected.value = false
      })
    } else {
      resetFields()
      isRoleSelected.value = false
    }
  }

  function resetFields() {
    role.roleName = ''
    role.description = ''
  }

  function selectRole(selectedRole: Role) {
    Object.assign(role, selectedRole)
    Object.assign(initialRole, selectedRole)
    isRoleSelected.value = true
  }

  function saveRole() {
    const { id, roleName, description } = role

    if (!roleName || roleName.trim() === '') {
      role.roleName = ''
      ElMessageBox.alert('Role Name cannot be empty.', 'Warning', {
        confirmButtonText: 'OK',
        type: 'warning',
      })
      return
    }

    ElMessageBox.confirm(
      `Are you sure you want to ${isRoleSelected.value ? 'update' : 'create'} this role?`,
      'Confirmation',
      {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning',
      },
    )
      .then(() => {
        if (isRoleSelected.value) {
          axios
            .put(`http://localhost:8081/api/roles/update`, null, {
              params: {
                id,
                roleName,
                description,
              },
            })
            .then(() => {
              fetchRoles()
              resetFields()
              isRoleSelected.value = false
              ElMessageBox.alert('Role updated successfully.', 'Success', {
                confirmButtonText: 'OK',
                type: 'success',
              })
            })
            .catch(error => {
              console.error('Error updating role:', error)
            })
        } else {
          axios
            .post(`http://localhost:8081/api/roles/create`, null, {
              params: {
                roleName,
                description,
              },
            })
            .then(() => {
              fetchRoles()
              resetFields()
              ElMessageBox.alert('Role saved successfully.', 'Success', {
                confirmButtonText: 'OK',
                type: 'success',
              })
            })
            .catch(error => {
              console.error('Error saving role:', error)
            })
        }
      })
      .catch(() => {})
  }

  return {
    roles,
    role,
    initialRole,
    search,
    isRoleSelected,
    currentPage,
    pageSize,
    paginatedRoles,
    fetchRoles,
    handlePageChange,
    clearSearch,
    cancelRole,
    resetFields,
    selectRole,
    saveRole,
  }
}
