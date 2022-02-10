<template>
  <div class="container">
    <div class="header">
      <p class="title">课题评分</p>
    </div>

    <div class="toolbox">
      <el-select class="toolbox-left" size="medium" v-model="graduateInfoId" placeholder="选择年份">
        <el-option v-for="item in graduateYears" :key="item.id" :label="item.title" :value="item.id"> </el-option>
      </el-select>
    </div>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="tableData" stripe>
      <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
      <el-table-column prop="title" label="名称" width="200"></el-table-column>
      <el-table-column prop="teacher_name" label="指导老师" width="200"></el-table-column>
      <el-table-column prop="student_name" label="学生" width="200"></el-table-column>
      <el-table-column prop="score" label="得分" width="100"></el-table-column>
      
    </el-table>


  </div>
</template>

<script>
import topic from '@/model/topic'
import user from '@/lin/model/user'
import cardApi from '@/model/card'
import scorecardApi from '@/model/scorecard'
import graduateInfo from '@/model/graduateinfo'
import student from '@/model/student'
import teacher from '@/model/teacher'

export default {
  
  data() {
    return {
      graduateInfoId: 0,
      studentId: 0,
      graduateYears: [],
      loginUsername: '',
      loginUserType: '',
      tableData: [],
      loading: false,
      
    }
  },
  async created() {
    const res = await graduateInfo.getGraduateInfoAll()
      console.log("graduate info list: " + JSON.stringify(res))
  
    this.graduateYears = res

    this.graduateInfoId = res[0].id

    await this.checkLoginUser()
    await this.initData()
    this._getTableData()
  },
  methods: {
    async _getTableData() {
      
      if (this.loginUserType == 'teacher') {
        let params = {graduate_info_id: this.graduateInfoId, teacher_id: this.teacherId}
        let scorecards = await scorecardApi.getScroecardByParams(params)
        this.tableData = scorecards
      } else if (this.loginUserType == 'student') {
        let params = {graduate_info_id: this.graduateInfoId, student_id: this.studentId}
        let scorecards = await scorecardApi.getScroecardByParams(params)
        this.tableData = scorecards
      }
      
    },
    
    async initData() {
      
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
      re = new RegExp('答辩组账号')
      if (re.test(result.username)) {
        this.loginUserType = 'judgeTeam'
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
