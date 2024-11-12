// src/facades/ProductFacade.ts
import type { Teams } from '../store/Types'
import { reactive, ref } from 'vue'
import myService from '../services/apiService'
import type { FormInstance } from 'element-plus'
import { validateRequired, validateLength } from '@/utils/validation-rules'
export const useTeamFacade = () => {
  const isLoading = ref(true)
  const pageSize4 = ref(10)
  const searchTeamId = ref('')
  const searchTeamName = ref('')
  const teams = ref([])

  const checkIdCreateAndEdit = ref('')
  const ruleFormTeam = ref()
  const background = ref(false)
  const ruleForm = reactive({
    teamId: '',
    teamName: '',
    description: '',
  })

  const centerDialogVisible = ref(false)
  const objectPage = reactive({
    pageNo: 1,
    pageSize: 10,
    total: 0,
    totalLenght: 0,
    search: ''
  });
  const rules = reactive({
    teamName: [
      validateRequired(),
      validateLength(3,255)
    ],
    description: [
      validateLength(0,255)
    ]
  });

  const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.resetFields()
    ruleForm.teamId = '';
    ruleForm.teamName = '';
    ruleForm.description = '';
    checkIdCreateAndEdit.value = ''
  }

  return {
    isLoading,
    pageSize4,
    searchTeamId,
    searchTeamName,
    teams,
    checkIdCreateAndEdit,
    ruleFormTeam,
    background,
    centerDialogVisible,
    ruleForm,
    objectPage,
    rules,
    resetForm,
    getAllTeamsAndSearch: async (searchTeamName?: string, searchTeamId?: string) => {
      try {
        const response = await myService.getAll('teams/allpage',
          {
            searchTeamName: searchTeamName,
            searchTeamId: searchTeamId,
            pageNumber: objectPage.pageNo,
            pageSize: objectPage.pageSize,
          })
        return response
      }
      catch (error) {
        console.log('Failed to load teams:', error)
        return { data: [], message: 'Error loading teams' }
      }
    },
    addTeam: async (team: Teams) => {
      try {
        return (await myService.create('teams', team)).data
      } catch (error) {
        console.error('Failed to load teams:', error)
        return { error }
      }
    },
    updateTeam: async (team: Teams) => {
      try {
        return (await myService.update('teams/', checkIdCreateAndEdit.value, team)).data
      } catch (error) {
        console.error('Failed to load teams:', error)
      }
    },
    deleteTeam: async (teamId: string) => {
      try {
        return (await myService.delete('teams', teamId)).data
      } catch (error) {
        console.error('Failed to load teams:', error)
      }
    },
    getTeamById: async (teamId: string) => {
      try {
        return (await myService.getById('teams', teamId)).data

      } catch (error) {
        console.error('Failed to load teams:', error)
      }
    }


  }
}
