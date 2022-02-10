<template>
  <div class="container" v-if="!showEdit && !showAssign">
    <div class="header">
      <p class="title">课题管理</p>

    </div>

    <div class="toolbox">
      <el-select class="toolbox-left" size="medium" v-model="graduateInfoId" placeholder="选择年份">
        <el-option v-for="item in graduateYears" :key="item.id" :label="item.title" :value="item.id"> </el-option>
      </el-select>

      <el-button class="toolbox-right" size="medium" type="primary" @click="showAddTopic">
        添加课题
      </el-button>
    </div>
    
    <!-- 表格 -->
    <el-table v-loading="loading" :data="tableData" stripe>
      <el-table-column prop="name" label="名称" width="200"></el-table-column>
      <el-table-column prop="description" label="描述信息" width="300"></el-table-column>
      <el-table-column prop="topic_type_name" label="课题类别" width="100"></el-table-column>
      <el-table-column prop="teacher_name" label="负责老师" width="100"></el-table-column>

      <el-table-column label="操作" width="400">
        <template slot-scope="scope">
          <el-button @click="handleDelete(scope.row)" type="primary" plain size="mini">
            删 除
          </el-button>

          <el-button @click="handleShowAssign(scope.row)" type="primary" plain size="mini">
            查看选课
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <task-edit v-else-if="showEdit" @editClose="editClose" :teacherId="teacherId" :graduateInfoId="graduateInfoId" :isCreate="isCreate"> </task-edit>
  <task-assign-list v-else @editClose="editClose" :teacherId="teacherId" :graduateInfoId="graduateInfoId" :showingTask="showingTask"></task-assign-list>
</template>

<script>
import topic from '@/model/topic'
import graduateInfo from '@/model/graduateinfo'
import user from '@/lin/model/user'
import teacher from '@/model/teacher'
import cardApi from '@/model/card'
import scorecardApi from '@/model/scorecard'
import taskEdit from './task-edit'
import TaskAssignList from './task-assign-list.vue'

export default {
  components: {
    taskEdit,
    TaskAssignList
  },
  data() {
    return {
      teacherId: 0,
      graduateInfoId: 0,
      graduateYears: [],

      loginUsername: '',
      loginUserType: '',
      tableData: [],
      loading: false,
      showEdit: false,
      showAssign: false,
      showingTask: 0,
      isCreate: true,
    }
  },
  async created() {
    await this.checkLoginUser()
    await this.initData()
    this._getTableData()
  },
  methods: {
    async _getTableData() {
      let _topics = null
      
      if(this.loginUserType != 'student') {
        _topics = await topic.getTasksByTeacherId(this.graduateInfoId, null)
      }
    
      console.log("table data: " + JSON.stringify(_topics))
      this.tableData = _topics
    },
    
    async handleDelete(data) {
      const confirmed = confirm("确定删除？")
      if (confirmed) {
        await topic.deleteTopicById(data.id)
        this._getTableData()
      }
      console.log("-- handle delete: " + JSON.stringify(data))
    },

    async initData() {
      const res = await graduateInfo.getGraduateInfoAll()
      console.log("graduate info list: " + JSON.stringify(res))
  
      this.graduateYears = res

      this.graduateInfoId = res[0].id
    },

    async checkLoginUser() {
      const result = await user.getInformation()  
    
      let re = new RegExp('\\d{10}')
      if (re.test(result.username)) {
        // Student
        this.loginUserType = 'student'
        this.$message.info('该界面学生用户不给予展示!')
        return
      }
      re = new RegExp('\\d{4}')
      if (re.test(result.username)) {
        // teacher
        this.loginUserType = 'teacher'
        const teacherInfo = await teacher.getTeacherByLognName(result.username)
        console.log("teacher info: " + JSON.stringify(teacherInfo))

        this.teacherId = teacherInfo.id
        
        return
      }
      this.$message.info('该界面root用户不给予展示!')
    },

    handleShowAssign(data) {
      this.showingTask = data.id
      this.showAssign = true
    },

    showAddTopic() {
      console.log("add topic tapped!")
      this.showEdit = true
    },


    editClose() {
      this.showEdit = false
      this.showAssign = false
      this._getTableData();
    }
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0 30px;

  .cardTable {
    width: 100%;
    ol {
      list-style-type: decimal !important;

      li {
        list-style-type: decimal !important;
        font-size: 16px;

        ol {
          list-style-type: upper-alpha !important;
          li {
            font-size: 14px;
            list-style-type: upper-alpha !important;
            list-style-position: inside;
            text-indent: 2em;
          }
          li::marker {
            color: red;
          }
        }
      }
      li::marker {
        color: darkred;
      }
    }
  }
  .add-dialog {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

  .header {
    display: flex;
    justify-content: flex-start;
    align-items: center;

    .title {
      height: 59px;
      line-height: 59px;
      color: $parent-title-color;
      font-size: 16px;
      font-weight: 500;
    }
  }

  .pagination {
    display: flex;
    justify-content: flex-end;
    margin: 20px;
  }
}
</style>
