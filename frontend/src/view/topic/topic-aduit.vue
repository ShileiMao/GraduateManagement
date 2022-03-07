<template>
  <div class="container">
    <div class="header">
      <p class="title">课题审核</p>
    </div>

    <div class="toolbox">
      <el-select class="toolbox-left" size="medium" v-model="graduateInfoId" placeholder="选择年份">
        <el-option v-for="item in graduateYears" :key="item.id" :label="item.title" :value="item.id"> </el-option>
      </el-select>
    </div>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="tableData" stripe>
      <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
      <el-table-column prop="name" label="名称" width="200"></el-table-column>
      <el-table-column prop="publish_year" label="发布年份" width="100"></el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">
          <el-tag type="success">
            {{ status[scope.row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button
            @click="handleSubmit(scope.row, initButtonText(scope.row)[0].status)"
            v-permission="{ permission: ['审核课题'], type: 'disabled' }"
            type="primary"
            plain
            size="mini"
            >{{ initButtonText(scope.row)[0].title }}</el-button
          >
          <el-button
            @click="handleSubmit(scope.row, initButtonText(scope.row)[1].status)"
            v-permission="{ permission: ['审核课题'], type: 'disabled' }"
            type="primary"
            plain
            size="mini"
            >{{ initButtonText(scope.row)[1].title }}</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import topic from '@/model/topic'
import user from '@/lin/model/user'
import teacher from '@/model/teacher'
import graduateInfo from '@/model/graduateinfo'
import student from '@/model/student'

export default {
  data() {
    return {
      loginUsername: '',
      loginUserType: '',
      graduateYears: [],
      graduateInfoId: 0,
      teacherId: 0,
      studentId: 0,
      status: {
        0: '开始',
        1: '申请课题',
        2: '申请通过',
        3: '申请未通过',
        4: '课题被选',
        5: '课题未被选',
        6: '一审通过',
        7: '一审未通过',
        8: '二审通过',
        9: '二审未通过',
        10: '三审通过',
        11: '三审未通过',
        100: '结束',
      },
      texts: {
        0: [{title: '申请通过', status: 2}, {title: '申请未通过', status: 3}],
        1: [{title: '一审通过', status: 6}, {title: '一审未通过', status: 7}],
        2: [{title: '二审通过', status: 8}, {title: '二审未通过', status: 9}],
        3: [{title: '三审通过', status: 10} ,{title: '三审未通过', status: 11} ],
      },
      tableData: [],
      loading: false,
    }
  },
  async created() {
    const res = await graduateInfo.getGraduateInfoAll()
  
    this.graduateYears = res

    if(res.length == 0) {
      this.$message.info('未开启毕设!')  
      return
    }

    this.graduateInfoId = res[0].id
    
    await this.checkLoginUser()
    this._getTableData()
  },
  methods: {
    async _getTableData() {
      if (this.loginUserType == 'teacher') {
        const res = await topic.getAssignByTeacherId(this.graduateInfoId, this.teacherId)
        this.tableData = res
      } else if (this.loginUserType == 'student') {
        const res = await topic.getAssignByStudent(this.studentId)
        this.tableData = [res]
      }
    },
    async handleSubmit(data, status) {
      if (status >= 100) return
      await topic.updateStatus({ id: data.id, status: status })
      this._getTableData()
    },
    // 切换分页
    async handleCurrentChange(val) {
      this.currentPage = val
      this.loading = true
      await this._getTableData()
      this.loading = false
    },

    initButtonText(data) {
      console.log("课题审核， data: " + JSON.stringify(data))
      if (data.status < 2) {
        return this.texts[0]
      } if (data.status < 6) {
        return this.texts[1]
      } if (data.status < 8) {
        return this.texts[2]
      } if (data.status < 10) {
        return this.texts[3]
      }
      return ['无操作', '无操作']
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
        this.loginUserType = 'student'

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
