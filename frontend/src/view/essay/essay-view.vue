<template>
  <div v-if="!showingUploadEssay && !modifyingEssay" class="container">
    <el-row :gutter="20" class="wrap">
      <el-col :lg="16" :md="20" :sm="24" :xs="24">
      <el-divider></el-divider>

        <div class="title">
          <span>论文列表</span>
        </div>
      </el-col>

      <div v-if="isStudent" class="toolbox">
        <el-button class="toolbox-right" size="medium" type="primary" @click="showUploadEssay" :disabled="hasAssign == false">
          上传论文
        </el-button>
      </div>

      <div v-if="isStudent">
        <div v-if="!hasAssign">
          您还没选课哦，请先选课
        </div>
        <div v-else>
          <div class="row">
            <label class="TitleLabel">论文课题：</label>
            <label class="ValueLabel">{{currentAssign.name}}</label>
          </div>

          <div class="row">
            <label class="TitleLabel">描述：</label>
            <label class="ValueLabel">{{currentAssign.description}}</label>
          </div>
        </div>
      </div>

      <!-- 表格 老师视图 -->
      <el-table v-loading="loading" :data="tableData" stripe style="width:100%">
        <el-table-column prop="student_name" label="学生" min-width="80"></el-table-column>
        <el-table-column prop="student_sid" label="学号" min-width="80"></el-table-column>
        <el-table-column prop="teacher_name" label="指导老师" min-width="80"></el-table-column>
        <el-table-column prop="topic_name" label="课题" min-width="200"></el-table-column>
        <el-table-column prop="essay_title" label="论文" min-width="200"></el-table-column>

        <el-table-column label="操作" min-width="120">
          <template slot-scope="scope">
            <el-button @click="handleSelect(scope.row)" type="primary" plain size="mini">
              查 看 
            </el-button>

            <el-button v-if="!isStudent" @click="handleEdit(scope.row)" type="primary" plain size="mini">
              修 改
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
  </div>
  <div v-else-if="modifyingEssay">
    <essay-edit @editClose="editClose" :essayId="essayId" :isUpload="isUpload" :isCreate="false" :topicAssignId="topicAssignId"></essay-edit>
  </div>
  <div v-else>
    <essay-edit @editClose="editClose" :essayId="essayId" :isUpload="isUpload" :isCreate="true" :topicAssignId="topicAssignId"></essay-edit>
  </div>
</template>

<script>
import topic from '@/model/topic'
import user from '@/lin/model/user'
import graduateInfo from '@/model/graduateinfo'
import student from '@/model/student'
import teacher from '@/model/teacher'
import essay from '@/model/essay'
import EssayEdit from './essay-edit.vue'

export default {
  components: {
    EssayEdit
  },
  data() {
    return {
      graduateInfoId: 0,
      studentId: 0,
      teacherId: 0,
      isStudent: false,

      showingUploadEssay: false,
      modifyingEssay: false,
      essayId: 0,
      isUpload: 1,
      topicAssignId: 0,
      currentAssign: null,
      hasAssign: false,

      loginUsername: '',
      loginUserType: '',
      tableData: [],
      loading: false
    }
  },
  async created() {
    const res = await graduateInfo.getGraduateInfoAll()
    console.log("graduate info list: " + JSON.stringify(res))

    this.graduateInfoId = res[0].id

    await this.checkLoginUser()
    this._getTableData()
  },
  methods: {
    editClose(e) {
        this.showingUploadEssay = false
        this.modifyingEssay = false
        this._getTableData()
    },
    showUploadEssay() {
        this.showingUploadEssay = true
        this.modifyingEssay = false
    },
    async _getTableData() {

      if(this.loginUserType === 'student') {
        let essayList = await essay.getEssayList(this.graduateInfoId, null, this.studentId)
        console.log("essayList: " + JSON.stringify(essayList))
        this.tableData = essayList
        return
      } else if(this.loginUserType === 'teacher') {
        let essayList = await essay.getEssayList(this.graduateInfoId, this.teacherId, null)
        this.tableData = essayList
        return
      }
      
  
      this.tableData = []
    },

    handleSelect(data) {
      window.location = data.download_url
    },
    handleEdit(data) {
      this.essayId = data.id
      this.modifyingEssay = true
      console.log("show edit")
    },
    async checkLoginUser() {
      const result = await user.getInformation()
      this.loginUsername = result.username
      let re = new RegExp('\\d{10}')
      if (re.test(result.username)) {
        // Student
        this.loginUserType = 'student'
        const studentInfo = await student.getStudentByLoginName(result.username)
        this.studentId = studentInfo.id
        this.isStudent = true;
      

      const assign = await topic.getAssignByStudent(this.studentId)

      this.currentAssign = null
      this.hasAssign = false
      if(assign != null && assign != undefined && assign != "") {
        this.currentAssign = assign
        this.hasAssign = true
        this.topicAssignId = assign.id
      } 
      
        console.log("current assign: " + JSON.stringify(this.currentAssign))

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

.title {
    height: 59px;
    line-height: 59px;
    color: $parent-title-color;
    font-size: 16px;
    font-weight: 500;
    // text-indent: 40px;

    .back {
      float: right;
      margin-right: 40px;
      cursor: pointer;
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
