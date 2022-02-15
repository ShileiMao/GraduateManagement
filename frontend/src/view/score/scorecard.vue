<template>
  <div class="container">
    <div class="header">
      <p class="title">评分表列表</p>
    </div>
    <div class="toolbox">
      <div class="toolbox-left">
        <el-select
          @change="handleChange"
          size="medium"
          clearable
          v-model="query.teacherId"
          placeholder="请选择指导老师"
        >
          <el-option v-for="item in teachers" :key="item.id" :label="item.name" :value="item.id"> </el-option>
        </el-select>

       <el-select class="toolbox-left" size="medium" v-model="query.graduateInfoId" placeholder="选择年份">
        <el-option v-for="item in graduateYears" :key="item.id" :label="item.title" :value="item.id"> </el-option>
      </el-select>
        
      </div>
    </div>

    <!-- 表格 -->
    <el-table v-loading="loading" :data="tableData" stripe>
      <el-table-column fixed prop="id" label="id" width="50"></el-table-column>
      <el-table-column fixed prop="title" label="评分表标题" width="120"></el-table-column>
      <el-table-column prop="level" label="等级"></el-table-column>
      <el-table-column prop="score" label="得分"></el-table-column>
      <el-table-column prop="comment" label="答辩委员会意见"></el-table-column>
      <el-table-column prop="judge_advise" label="答辩组评语"></el-table-column>
      <el-table-column prop="judge_score" label="答辩组打分"></el-table-column>
      <el-table-column prop="guide_score" label="指导老师打分"></el-table-column>
      <el-table-column prop="guide_advise" label="指导老师评语"></el-table-column>
      <el-table-column label="操作" width="450">
        <template slot-scope="scope">
          <el-button
            v-if="!scope.row.comment"
            v-permission="['答辩委员会留言']"
            @click="handleAdvice(scope.row, true)"
            type="primary"
            plain
            size="mini"
            >答辩委员会意见</el-button
          >

          <el-button
            v-if="!scope.row.comment"
            v-permission="['答辩委员会留言']"
            @click="handleAdvice(scope.row, false)"
            type="primary"
            plain
            size="mini"
            >不留意见</el-button
          >

          <el-button v-if="scope.row.supplement" @click="handleOldDownload(scope.row)" type="primary" plain size="mini"
            >下载补答辩前评分表</el-button
          >
          <el-button v-if="scope.row.score" @click="handleDownload(scope.row)" type="primary" plain size="mini"
            >下载</el-button
          >
          <el-button @click="handleDelete(scope.row)" type="danger" size="mini" plain>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        @current-change="handleCurrentChange"
        :background="true"
        :page-size="pageCount"
        :current-page="currentPage"
        v-if="refreshPagination"
        layout="prev, pager, next, jumper"
        :total="totalNums"
      ></el-pagination>
    </div>

    <el-dialog :visible.sync="showAdviseCard" title="留言">
      <el-input v-model="comment" @keyup.enter.native="submitComment" placeholder="请输入留言,回车提交"></el-input>
    </el-dialog>
  </div>
</template>

<script>
import teacherApi from '@/model/teacher'
import classApi from '@/model/class'
import user from '@/lin/model/user'
import scorecardApi from '@/model/scorecard'
import graduateInfo from '@/model/graduateinfo'
import student from '@/model/student'
import teacher from '@/model/teacher'

export default {
  data() {
    return {
      graduateInfoId: 0,
      graduateYears: [],
      loginUsername: '',
      loginUserType: '',
      tableData: [],
      loading: false,
      // 分页相关
      refreshPagination: true, // 页数增加的时候，因为缓存的缘故，需要刷新Pagination组件
      currentPage: 1, // 默认获取第一页的数据
      pageCount: 2, // 每页10条数据
      totalNums: 180, // 分组内总数
      teachers: [],
      classes: [],

      showAdviseCard: false,
      comment: null,
      scorecardId: null,
      query: {
        teacherId: null,
        classId: null,
        year: null,
      },
    }
  },
  async created() {
    const res = await graduateInfo.getGraduateInfoAll()
      console.log("graduate info list: " + JSON.stringify(res))
  
    this.graduateYears = res

    this.graduateInfoId = res[0].id
    this.query.graduateInfoId = res[0].id


    this.initData()
    this.loading = false
    await this.checkLoginUser()
    if (this.loginUserType === 'student' || this.loginUserType === 'teacher') {
      await this._getTableData((this.currentPage - 1) * this.pageCount, this.pageCount)
    }
  },
  methods: {
    async _getTableData() {
      const currentPage = this.currentPage - 1
      const res = await scorecardApi.getScorecardByPage(currentPage, this.pageCount, this.query)
      this.tableData = res.items
      this.totalNums = res.total
    },

    handleAdvice(data, flag) {
      this.scorecardId = data.id
      if (flag) this.showAdviseCard = true
      else {
        this.submitComment()
      }
    },

    handleDownload(data) {
      window.open(`${process.env.VUE_APP_BASE_URL}/v1/scorecard/download/${data.id}`, '_parent')
    },

    handleOldDownload(data) {
      window.open(`${process.env.VUE_APP_BASE_URL}/v1/scorecard/download/${data.id}/sup`, '_parent')
    },

    async handleDelete(data) {
      this.$confirm('此操作将永久删除该评分表, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        const res = await await scorecardApi.deleteScorecard(data.id)
        if (res.code < window.MAX_SUCCESS_CODE) {
          if (this.totalNums % this.pageCount === 1 && this.currentPage !== 1) {
            // 判断删除的是不是每一页的最后一条数据
            this.currentPage--
          }
          await this._getTableData()
          this.$message({
            type: 'success',
            // message: `${res.message}`,
            message: '删除成功',
          })
        } else {
          this.$message({
            type: 'error',
            message: `${res.message}`,
          })
        }
      })
    },

    // 切换分页
    async handleCurrentChange(val) {
      this.currentPage = val
      this.loading = true
      await this._getTableData()
      this.loading = false
    },

    async initData() {
      if (!window.sessionStorage.getItem('teachers')) {
        const res = await teacherApi.getAllTeacher()
        this.teachers = res
        window.sessionStorage.setItem('teachers', JSON.stringify(res))
      } else {
        this.teachers = JSON.parse(window.sessionStorage.getItem('teachers'))
      }

      if (!window.sessionStorage.getItem('classes')) {
        const res = await classApi.getAllClass()
        this.classes = res
        window.sessionStorage.setItem('classes', JSON.stringify(res))
      } else {
        this.classes = JSON.parse(window.sessionStorage.getItem('classes'))
      }
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
    // 下拉框选择
    async handleChange() {
      this.currentPage = 1
      this.loading = true
      await this._getTableData()
      this.loading = false
    },

    async submitComment() {
      const res = await scorecardApi.committeeComment(this.scorecardId, this.comment, this.comment != null)

      if (res != null && res.code < window.MAX_SUCCESS_CODE) {
        this.$message.success('评论成功')
        this._getTableData()
        this.showAdviseCard = false
        this.comment = null
      }
    },
  },
}
</script>
<style lang="scss" scoped>
.container {
  padding: 0 30px;

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

  .toolbox {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    border-bottom: 1px solid #dae1ed;
    padding-bottom: 10px;

    .toolbox-left {
      float: left;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .toolbox-right {
      float: right;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }

  .pagination {
    display: flex;
    justify-content: flex-end;
    margin: 20px;
  }
}
</style>
