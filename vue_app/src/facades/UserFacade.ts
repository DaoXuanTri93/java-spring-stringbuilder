import type { ColumnCommon } from '@/interfaces/ColumnCommon'
import type { User } from '@/interfaces/User'
import axios from 'axios'
import type { FormInstance, FormRules } from 'element-plus'
import { ref } from 'vue'
import { reactive } from 'vue'

export const useUserFacade = () => {
  const pageSize = ref(10)
  const userNameSearchTerm = ref('')
  const userIdSearchTerm = ref('')
  const selectedRoleId = ref(null)
  const selectedTeamId = ref(null)
  const users = ref([])
  const teams = ref([])
  const roles = ref([])
  const currentPage = ref(1)
  const totalElements = ref(0)
  const rules: FormRules<User> = {
    userName: [
      { required: true, message: 'Please input User Name', trigger: 'blur' },
    ],
    name: [
      {
        required: true,
        message: 'Please input Full Name',
        trigger: 'blur',
      },
    ],
    teamId: [
      { required: true, message: 'Please select Team', trigger: 'blur' },
    ],
    roleId: [
      { required: true, message: 'Please select Role', trigger: 'blur' },
    ],
  }

  const reactiveRules = reactive(rules)

  const userForm = reactive({
    id: null,
    name: '',
    userName: '',
    roleId: null,
    teamId: null,
    description: null,
  })

  function handleEdit(row: User): void {
    Object.assign(userForm, row)
  }

  const columns: ColumnCommon[] = [
    { prop: 'userName', label: 'User Name', align: 'center' },
    { prop: 'name', label: 'Full Name' },
    { prop: 'teamName', label: 'Team' },
    { prop: 'roleName', label: 'Role' },
    { prop: 'description', label: 'Description' },
    { prop: 'createdBy', label: 'Created By' },
    { prop: 'createdDate', label: 'Created Date', align: 'center' },
    { prop: 'updatedBy', label: 'Updated By' },
    { prop: 'updatedDate', label: 'Updated Date', align: 'center' },
  ]

  const userFormRef = ref()

  const clearUserForm = () => {
    userForm.id = null
    userForm.name = ''
    userForm.userName = ''
    userForm.roleId = null
    userForm.teamId = null
    userForm.description = null
  }

  function fetchUsers(page = 1) {
    let queryString = `?page=${page - 1}&size=${pageSize.value}`

    if (userNameSearchTerm.value) {
      queryString += `&userName=${userNameSearchTerm.value}`
    }
    if (userIdSearchTerm.value) {
      queryString += `&userId=${userIdSearchTerm.value}`
    }
    if (selectedRoleId.value) {
      queryString += `&roleId=${selectedRoleId.value}`
    }
    if (selectedTeamId.value) {
      queryString += `&teamId=${selectedTeamId.value}`
    }

    fetch(`http://localhost:8081/api/users${queryString}`)
      .then(response => response.json())
      .then(data => {
        users.value = data.users
        totalElements.value = data.totalElements
        currentPage.value = page
        userFormRef.value?.resetFields()
        clearUserForm()
      })
  }
  function fetchRolesAndTeams() {
    fetch('http://localhost:8081/api/users/select-items')
      .then(response => response.json())
      .then(data => {
        teams.value = data.teams
        roles.value = data.roles
      })
  }

  const submitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl) return
    await formEl.validate((valid: unknown, fields: unknown) => {
      if (valid) {
        if (userForm.id) {
          updateUser()
        } else {
          createUser()
        }
      } else {
        console.log('error submit!', fields)
      }
    })
  }
  function createUser() {
    axios.post('http://localhost:8081/api/users', userForm).then(() => {
      fetchUsers()
    })
  }
  function updateUser() {
    axios
      .put(`http://localhost:8081/api/users/${userForm.id}`, userForm)
      .then(() => {
        fetchUsers()
      })
  }
  function deleteUser(id: string) {
    axios.delete(`http://localhost:8081/api/users/${id}`).then(() => {
      fetchUsers()
    })
  }
  function handlePageChange(newPage: number) {
    fetchUsers(newPage)
  }

  return {
    submitForm,
    fetchRolesAndTeams,
    fetchUsers,
    userFormRef,
    columns,
    users,
    teams,
    roles,
    currentPage,
    totalElements,
    pageSize,
    reactiveRules,
    userNameSearchTerm,
    userIdSearchTerm,
    selectedRoleId,
    selectedTeamId,
    userForm,
    handleEdit,
    deleteUser,
    handlePageChange,
  }
}
