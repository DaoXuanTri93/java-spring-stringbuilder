<template>
  <el-input v-model="searchName" placeholder="search name...">
  </el-input>
  <el-button class="mt-5" @click="hanldeSearch">
    search
  </el-button>
  <el-table :data="teams" style="width: 100%" height="500">
    <el-table-column fixed label="Team Code" prop="teamId" />
    <el-table-column prop="teamName" label="Team Name" />
    <el-table-column prop="description" label="Price" header-align="center" align="right" width="100" />
    <el-table-column prop="createdDate" label="Create At" />
    <el-table-column prop="updateDate" label="Update At" />
    <el-table-column label="Action" #default="scoped">
      <el-button type="success" @click="findByIdTeam(scoped.row.teamId)"> Edit </el-button>
      <el-button type="danger" @click="deleteRow(scoped.row.teamId)"> Remove </el-button>
    </el-table-column>
  </el-table>
  <el-pagination style="justify-content: center" :page-size=Number(objectPage.pageSize)
    :total=Number(objectPage.totalLenght) layout="prev, pager, next" @current-change="callParam" />
  <el-row :gutter="20">
    <el-col :span="12" :offset="6">
      <div class="grid-content ep-bg-purple">
        <el-form ref="ruleFormRef" style="max-width: 600px" :model="ruleForm" :rules="rules" label-width="auto"
          class="demo-ruleForm" status-icon>
          <el-form-item label="Team name" prop="name">
            <el-input v-model="ruleForm.teamName" />
          </el-form-item>
          <el-form-item label="Price" prop="price">
            <el-input v-model.number="ruleForm.description" autocomplete="off" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)">
              Create
            </el-button>
            <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-col>
  </el-row>

</template>

<script lang="ts">
import { defineComponent, onMounted, ref, reactive, computed } from 'vue';
import axios from 'axios';
import { ENDPOINTSTEAMS } from '@/config/api-config';
import type FormInstance from "element-plus";
import FormRules from "element-plus";

interface RuleFormTeam {
  teamId?: string;
  teamName: string;
  description: string;
}

export default defineComponent({
  name: 'TeamsMaster',
  setup() {
    const searchName = ref('')
    const teams = ref<RuleFormTeam[]>([]);
    const checkIdCreateAndEdit = ref('');
    const ruleFormRef = ref<FormInstance>();
    const ruleForm = reactive<RuleFormTeam>({
      teamName: "",
      description: "",
    });
    const objectPage = reactive({
      pageNo: '1',
      pageSize: '10',
      total: '',
      totalLenght: '',
      search: ''
    })

    const rules = reactive<FormRules<RuleFormTeam>>({
      name: [
        { required: true, message: "Vui lòng nhập tên", trigger: "blur" },
        { min: 3, max: 200, message: "Tên ít nhất 3 kí tự", trigger: "blur" },
      ],
      price: [
        { required: true, message: "Nhập giá lớn hơn 0", trigger: "blur" },
        { type: 'number', message: "Nhập giá trị số" }
      ],
    });

    const resetForm = (formEl: FormInstance | undefined) => {
      if (!formEl) return;
      formEl.resetFields();
    };
    // Load data from API
    const loadteams = async () => {
      try {
        // debugger; 
        const response = await axios.get(
          ENDPOINTSTEAMS.GET_ALL_TEAMS,
          {
            params: {
              pageNumber: objectPage.pageNo,
              pageSize: objectPage.pageSize,
            },
            withCredentials: true
          }, // Thêm credentials vào yêu cầu          
        );
        // debugger; 
        teams.value = response.data.teams;
        objectPage.total = response.data.total;
        objectPage.totalLenght = response.data.totalLenght;

        // debugger;
      } catch (error) {
        console.error('Error fetching teams:', error);
      }
    };
    //delete Team
    const deleteRow = async (id: string) => {
      await axios.delete(ENDPOINTSTEAMS.DELETE_TEAM + id)
        .then(() => {
          // teams.value = teams.value.filter(x => x.teamId !== id)
          loadteams()
        })
    };

    // search name Team
    const hanldeSearch = async () => {
      if (searchName.value == "") {
        loadteams();
      } else {
        await axios
          .get(ENDPOINTSTEAMS.SEARCH_NAME_TEAM, {
            params: {
              search: searchName.value
            },
          })
          .then((res) => {

            teams.value = res.data.teams
            console.log("search", res)
            objectPage.pageSize = res.data.totalSize
            objectPage.totalLenght = res.data.totalLenght
          });
      }

    }
    //Find by Id Team
    const findByIdTeam = async (id: string) => {
      await axios.get(ENDPOINTSTEAMS.FIND_BY_ID_TEAM + id)
        .then((res) => {
          checkIdCreateAndEdit.value = id;
          ruleForm.teamName = res.data.teamName;
          ruleForm.description = res.data.description;
        })
    }


    // create and update Team
    const submitForm = async (formEl: FormInstance | undefined) => {
      if (!formEl) return;
      if (checkIdCreateAndEdit.value == '') {
        await formEl.validate((valid: string, fields: string) => {
          if (valid) {
            axios
              .post(ENDPOINTSTEAMS.CREATE_TEAM, ruleForm, {
                withCredentials: true,
              })
              .then((res) => {
                teams.value = [...teams.value, res.data].sort((a, b) => b.updateAt - a.updateAt)
                ruleForm.teamName = ''
                ruleForm.description = ''
              });
          } else {
            console.log("error submit!", fields);
          }
        });
      } else {
        await axios.put(ENDPOINTSTEAMS.UPDATE_TEAM + checkIdCreateAndEdit.value, ruleForm, { withCredentials: true })
          .then((res) => {
            let objectIndex = teams.value.findIndex(x => x.teamId == checkIdCreateAndEdit.value)
            teams.value[objectIndex].teamName = res.data.name;
            teams.value[objectIndex].description = res.data.price;
            checkIdCreateAndEdit.value = ''
            ruleForm.teamName = ''
            ruleForm.description = ''
          })
      }

    };


    // send page number param
    const callParam = computed(() => async (pageNoNew: string) => {
      await axios
        .get(ENDPOINTSTEAMS.GET_ALL_TEAMS, {
          params: {
            search: searchName.value == '' ? '' : searchName.value,
            pageNumber: pageNoNew,
            pageSize: objectPage.pageSize,
          },
        })
        .then((res) => {
          objectPage.pageNo = pageNoNew;

          teams.value = res.data.teams;
        });
    });
    onMounted(() => {
      loadteams();
    });

    return {
      teams,
      searchName,
      checkIdCreateAndEdit,
      ruleFormRef,
      ruleForm,
      objectPage,
      rules,
      resetForm,
      deleteRow,
      submitForm,
      findByIdTeam,
      hanldeSearch,
      callParam
    };
  }
});
</script>

<style scoped>
/* Add any style if needed */
</style>
