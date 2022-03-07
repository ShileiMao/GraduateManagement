<template>
  <div class="container" v-if="!showEdit">
    <div class="header">
      <p class="title">选课管理</p>

    </div>

    <div class="toolbox">
      <el-select class="toolbox-left" size="medium" v-model="graduateInfoId" placeholder="选择年份">
        <el-option v-for="item in graduateYears" :key="item.id" :label="item.title" :value="item.id"> </el-option>
      </el-select>

      <el-button class="toolbox-right" size="medium" type="primary" :disabled="showingSelect" @click="deleteAssign">
        删除选课
      </el-button>
    </div>
    
  <!-- 当前选课 -->
  <div v-if="currentAssign != null">
    <div class="row">
      <label class="TitleLabel">课题：</label>
      <p class="ValueLabel">{{this.currentAssign.name}}</p>
    </div>
    <div class="row">
      <label class="TitleLabel">描述</label>
      <p class="ValueLabel">{{this.currentAssign.description}}</p>
    </div>
    <div class="row">
      <label class="TitleLabel">指导老师:</label>
      <p class="ValueLabel">{{this.currentAssign.teacher_name}}</p>
    </div>
    <div class="row">
      <label class="TitleLabel">课题：</label>
      <p class="ValueLabel">{{this.currentAssign.name}}</p>
    </div>
    <div class="row">
      <label class="TitleLabel">学院：</label>
      <p class="ValueLabel">{{this.currentAssign.college_name}}</p>
    </div>
  
  </div>
  <!-- 表格 -->
  <div v-else>
    <el-table v-loading="loading" :data="tableData" stripe>
      <el-table-column prop="name" label="名称" width="200"></el-table-column>
      <el-table-column prop="description" label="描述信息" width="300"></el-table-column>
      <el-table-column prop="topic_type_name" label="课题类别" width="100"></el-table-column>
      <el-table-column prop="teacher_name" label="负责老师" width="100"></el-table-column>

      <el-table-column label="操作" width="400">
        <template slot-scope="scope">
          <el-button @click="handleSelect(scope.row)" type="primary" plain size="mini">
            选 择
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  </div>
  <task-edit v-else @editClose="editClose" :teacherId="teacherId" :graduateInfoId="graduateInfoId" :isCreate="isCreate"> </task-edit>
</template>

<script>
import topic from '@/model/topic'
import graduateInfo from '@/model/graduateinfo'
import user from '@/lin/model/user'
import taskEdit from './task-edit'
import student from '@/model/student'

export default {
  components: {
    taskEdit
  },
  data() {
    return {
      graduateInfoId: 0,
      graduateYears: [],

      studentId: 0,

      currentAssign: null,
      showingSelect: false,

      loginUsername: '',
      loginUserType: '',
      tableData: [],
      loading: false,
      showEdit: false,
      isCreate: true,
    }
  },
  async created() {
    await this.checkLoginUser()
    const result = await this.initData()
    if(!result) {
      return
    }
    await this._getTableData()
    await this.getCurrentAssign()
  },
  methods: {
    async _getTableData() {
      let _topics = null
      
      if (this.loginUserType === 'student') {
        _topics = await topic.getTasksByTeacherId(this.graduateInfoId, null)
        this.tableData = _topics  
      }
    },

    async getCurrentAssign() {
      if(this.loginUserType === 'student' && this.studentId != 0) {
        const assign = await topic.getAssignByStudent(this.studentId)

      this.currentAssign = null
      this.showingSelect = true
      if(assign != null && assign != undefined && assign != "") {
        this.currentAssign = assign
        this.showingSelect = false
      } 
      
        console.log("current assign: " + JSON.stringify(this.currentAssign))
        return
      }
      this.currentAssign = null
    },
    
    async handleSelect(data) {
      if(this.loginUserType != 'student') {
        return
      }
      console.log("-- handle select: " + JSON.stringify(data))
      const confirmed = confirm("确定选课？")
      if (confirmed) {
        await topic.selectTopic(data.id, this.studentId)
        await this._getTableData()
        await this.getCurrentAssign()
      }
    },

    async deleteAssign(assignId) {
      const confirmed = confirm("取消选题？")
      if (confirmed) {
        await topic.deleteAssignById(assignId)
        this.currentAssign = null
        await this._getTableData()
        await this.getCurrentAssign()
      }
    },


    async initData() {
      const res = await graduateInfo.getGraduateInfoAll()
      
      this.graduateYears = res

      if(res.length == 0) {
        this.$message.info('未开启毕设!')
        return false
      }

      this.graduateInfoId = res[0].id
      return true
    },

    async checkLoginUser() {
      const result = await user.getInformation()  
    
      let re = new RegExp('\\d{10}')
      if (re.test(result.username)) {
        // Student
        const studentInfo = await student.getStudentByLoginName(result.username)
        this.studentId = studentInfo.id
        this.loginUserType = 'student'
        return
      }
      re = new RegExp('\\d{4}')
      if (re.test(result.username)) {
        // teacher
        this.loginUserType = 'teacher'
        this.$message.info('该界面老师用户不给予展示!')  
        return
      }
      this.$message.info('该界面root用户不给予展示!')
    },

    async deleteAssign() {
      console.log("add topic tapped!")
      const confirmed = confirm("确定取消课题？")
      if (confirmed) {
        await topic.deleteAssignById(this.currentAssign.id)
        await this._getTableData()
        await this.getCurrentAssign()
      }
    },

    editClose() {
      this.showEdit = false
      this._getTableData()
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
  .row {
    width: 80%;
    min-height: 50px;
    display: flex;
    flex-direction: row;
    line-height: 50px;
  }
  .TitleLabel {
    width: 150px;
  }
  .ValueLabel {
    flex-grow: 1;
  }
}
</style>
