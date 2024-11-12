<template>
  <div style="margin-right: 20px;">
    <!-- header -->
    <HeaderComponent title="Header Master" />


    <el-row>
      <el-col :span="6">
        <div class="grid-content ep-bg-purple">

        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content ep-bg-purple-light">

        </div>
      </el-col>
    </el-row>
    <div style="margin: 10px 0;">
      <el-text> Team Code :</el-text>
      <el-input type="text" class="searchInput" maxlength="50" show-word-limit size="small" v-model="searchTeamId"
        placeholder="Search Team Code..." style="width: 150px" />

      <el-text> Team Name :</el-text>
      <el-input class="searchInput" maxlength="50" show-word-limit prop="teamName" size="small" v-model="searchTeamName"
        placeholder="Search Team Name..." style="width: 150px" />
      <div>
        <!-- button search -->
        <ButtonComponent title="Search" @hanlde-search-team="hanldeSearch" />

      </div>

      <HeaderComponent title="Search Results" style-demo="left " />

    </div>
    <!--start table -->
    <div style="overflow: auto; display: flex; justify-content: center; width: 100%;">
      <el-skeleton v-if="isLoading" :rows="5" animated />
      <el-table v-else :data="teams" style="width: 100%" height="400" border
        :header-cell-style="{ background: '#337ecc', color: '#FFFFFF' }">
        <el-table-column fixed="left" label="Team Code" width="100" prop="teamId" header-align="center">
          <template #default="scoped">
            <el-text class="el-text-link" tag="ins" @click="findByIdTeam(scoped.row.teamId)">
              {{ scoped.row.teamId }}
            </el-text>
          </template>

        </el-table-column>
        <el-table-column prop="teamName" label="Team Name" header-align="center" />
        <el-table-column prop="description" label="Description" width="300" align="center" header-align="center">
          <template #default="{ row }">
            <div style="text-align: justify;" class="text-hidden" @click="showDescription($event)">
              {{ row.description }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="createBy" label="Create By" width="150" header-align="center" />
        <el-table-column prop="createDate" label="Create At" width="150" header-align="center" />
        <el-table-column prop="updateBy" label="Update By" width="150" header-align="center" />
        <el-table-column prop="updateDate" label="Update At" width="150" header-align="center" />
        <el-table-column fixed="right" label="Action" align="center" width="150">
          <template #default="scoped">
            <el-button type="danger" @click="handleDialog(scoped.row.teamId)"> Remove </el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>
    <!-- end table -->

    <!-- start pagination -->
    <div class="pagination">
      <PaginationComponent :current-page="objectPage.pageNo" :page-size="objectPage.pageSize"
        :total="objectPage.totalLenght" @handle-size-change="handleSizeChange"
        @handle-current-change="handleCurrentChange" />
    </div>
    <!-- end pagination -->

    <!-- start Vùng input -->
    <HeaderComponent title="Input" style-demo="left" />

    <el-form ref="ruleFormTeam" :inline="true" :model="ruleForm" :rules="rules" label-width="auto"
      class="demo-form-inline" status-icon>
      <el-row>
        <el-col :span="8">
          <div class="grid-content ep-bg-purple">
            <el-form-item label="Team Code" label-position="top">
              <el-input size="small" readonly disabled v-model="ruleForm.teamId"
                style="width: 200px; font-size: 16px; " />
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content ep-bg-purple-light">
            <el-form-item label="Team Name" label-position="top" prop="teamName">
              <el-input size="small" maxlength="50" show-word-limit v-model="ruleForm.teamName"
                style="width: 200px; font-size: 16px;" />
            </el-form-item>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="grid-content ep-bg-purple">
            <el-form-item label="description" label-position="top" prop="description" style="width: 80%; ">
              <el-input :rows="3" size="small" maxlength="255" show-word-limit v-model="ruleForm.description"
                style="font-size: 16px;" type="textarea" />
            </el-form-item>
          </div>
        </el-col>
      </el-row>

      <!-- submit form -->
      <div>
        <el-form-item>
          <el-button type="primary" @click="submitForm(ruleFormTeam)" v-if="checkIdCreateAndEdit == ''">Save</el-button>
          <el-button type="success" @click="submitForm(ruleFormTeam)" v-else>Update</el-button>
          <el-button type="warning" @click="resetForm(ruleFormTeam)">Reset</el-button>
        </el-form-item>
      </div>

      <!-- End vùng input -->

      <!-- dialog -->

      <DialogComponent title="Are you sure you want to delete ?" @delete-row="deleteRow(ruleFormTeam)"
        @close-dialog="closeDialog" v-model="centerDialogVisible" />
    </el-form>
  </div>
</template>


<script lang="ts" setup>
import { ElNotification, FormInstance } from 'element-plus';
import { onMounted, onUnmounted } from 'vue';
import { useTeamFacade } from '../facades/TeamFacade';
import HeaderComponent from '@/components/teams/HeaderTeams.vue'
import ButtonComponent from '@/components/teams/ButtonComponent.vue'
import PaginationComponent from '@/components/teams/PaginationComponent.vue';
import DialogComponent from '@/components/teams/DialogComponent.vue';

const {
  isLoading,
  searchTeamId,
  searchTeamName,
  teams,
  checkIdCreateAndEdit,
  ruleFormTeam,
  ruleForm,
  centerDialogVisible,
  objectPage,
  rules,
  resetForm,
  getAllTeamsAndSearch,
  addTeam,
  updateTeam,
  deleteTeam,
  getTeamById
} = useTeamFacade();

const handleDialog = (id: string) => {
  centerDialogVisible.value = true
  checkIdCreateAndEdit.value = id
}

const loadteams = async () => {
  objectPage.pageNo = 1;
  objectPage.pageSize = 10
  const result = await getAllTeamsAndSearch(searchTeamName.value)
  teams.value = result.data

  setTimeout(() => {
    isLoading.value = false;
  }, 1000);
  objectPage.totalLenght = result.totalLength
  if (result.message) alert(result.message)
}

const handleSizeChange = async (val: number) => {
  objectPage.pageNo = 1;
  objectPage.pageSize = val;
  await callParam();

}

const handleCurrentChange = async (val: number) => {
  objectPage.pageNo = val
  await callParam();
}

const deleteRow = async (formEl: FormInstance | undefined) => {
  centerDialogVisible.value = false;

  await deleteTeam(checkIdCreateAndEdit.value)
  await resetForm(formEl);
  await loadteams()
};

const hanldeSearch = async () => {
  if (searchTeamName.value == "" && searchTeamId.value == "") {
    await loadteams();
  } else {
    objectPage.pageNo = 1;
    objectPage.pageSize = 10;
    const result = await getAllTeamsAndSearch(searchTeamName.value, searchTeamId.value)

    teams.value = result.data;
    objectPage.pageSize = result.totalSize;
    objectPage.totalLenght = result.totalLength;

  }
};

const closeDialog = () => {
  centerDialogVisible.value = false;
}
const findByIdTeam = async (id: string) => {
  const result = await getTeamById(id)
  checkIdCreateAndEdit.value = id;
  ruleForm.teamId = id;
  ruleForm.teamName = result.teamName;
  ruleForm.description = result.description;
};

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  if (checkIdCreateAndEdit.value == '') {
    await formEl.validate(async (valid) => {
      if (valid) {
        await addTeam(ruleForm)
        await loadteams().then(() => {
          ElNotification({
            title: 'Success',
            message: 'Create successful.',
            type: 'success',
          })
        })
        resetForm(formEl)
      }
    });
  } else {
    await formEl.validate(async (valid) => {
      if (valid) {
        await updateTeam(ruleForm)
        await loadteams()
        ElNotification({
          title: 'Success',
          message: 'Update successful.',
          type: 'success',
        })
        resetForm(formEl)
      }
    });

  }
};

const callParam = async () => {
  const result = await getAllTeamsAndSearch(searchTeamName.value === '' && searchTeamId.value === '' ? '' : searchTeamName.value, searchTeamId.value)
  teams.value = result.data;
};

onMounted(() => {
  (async () => {
    await loadteams();
  })()
});

onUnmounted(() => {
  console.log("onUnmounted")
})

const showDescription = (e) => {
  if (e.target.className === "text-hidden") {
    e.target.className = "";
  } else {
    e.target.className = "text-hidden";
  }
};
</script>

<style scoped>
.text-hidden {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.pagination {
  margin: 10px 0;
}

.demo-pagination-block .demonstration {
  margin-bottom: 16px;
}

.searchInput {
  width: 150px;
  margin: 10px 5px;
}

.searchInput {
  margin-left: 10px;
}

/* .buttonSearch {
  background-color: #337ecc;
  color: #FFFFFF;
} */

.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.el-form--inline .el-form-item {
  display: inline-flex;
  margin-right: 32px;
  vertical-align: text-top;
}

.el-text-link {
  cursor: pointer;
  color: #409EFF;
  text-decoration: underline;
}

.active-link {
  color: #337ecc;
  text-decoration: underline;
}
</style>
